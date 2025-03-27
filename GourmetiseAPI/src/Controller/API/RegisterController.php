<?php

namespace App\Controller\API;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Lexik\Bundle\JWTAuthenticationBundle\Services\JWTTokenManagerInterface;

class RegisterController extends AbstractController
{
    #[Route('/api/register', name: 'api_register', methods: ['POST'])]
    public function register(
        Request $request, 
        EntityManagerInterface $entityManager, 
        UserPasswordHasherInterface $passwordHasher,
        JWTTokenManagerInterface $jwtManager
    ): JsonResponse {
        $data = json_decode($request->getContent(), true);
        
     
        if (!isset($data['mail']) || !isset($data['password'])) {
            return new JsonResponse(["message" => "Email et mot de passe requis."], 400);
        }

        $user = new User();
        $user->setMail($data['mail']);
        $user->setPassword($passwordHasher->hashPassword($user, $data['password']));
        $user->setRole("Participant"); 



        $user->setCreatedAt(new \DateTime());

        $entityManager->persist($user);
        $entityManager->flush();

        
        $token = $jwtManager->create($user);

        return new JsonResponse(["message" => "Utilisateur créé avec succès.", "token" => $token], 201);
    }
}
