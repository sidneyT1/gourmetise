<?php

namespace App\Repository;

use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Lexik\Bundle\JWTAuthenticationBundle\Services\JWTTokenManagerInterface;


class UserRepository extends ServiceEntityRepository
{
    private UserPasswordHasherInterface $passwordHasher;
    private JWTTokenManagerInterface $JWTManager;

    public function __construct(
        ManagerRegistry $registry,
        UserPasswordHasherInterface $passwordHasher,
        JWTTokenManagerInterface $JWTManager
    ) {
        parent::__construct($registry, User::class);
        $this->passwordHasher = $passwordHasher;
        $this->JWTManager = $JWTManager;
    }

    public function registerUser(User $user): string
    {
        // Hasher le mot de passe
        $hashedPassword = $this->passwordHasher->hashPassword($user, $user->getPassword());
        $user->setPassword($hashedPassword);

        // Persister l'utilisateur en base
        $entityManager = $this->getEntityManager();
        $entityManager->persist($user);
        $entityManager->flush();

        // Générer un JWT pour l'utilisateur
        return $this->JWTManager->create($user);
    }
}


