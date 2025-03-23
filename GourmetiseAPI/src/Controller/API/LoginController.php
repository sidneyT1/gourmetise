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

class LoginController extends AbstractController
{
    #[Route('/api/login', name: 'api_login', methods: ['POST'])]
    public function login(
        Request $request,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher,
        JWTTokenManagerInterface $jwtManager
    ): JsonResponse {
        $data = json_decode($request->getContent(), true);

        if (!isset($data['mail']) || !isset($data['password'])) {
            return new JsonResponse(["message" => "Email et mot de passe requis."], 400);
        }

        $user = $entityManager->getRepository(User::class)->findOneBy(['mail' => $data['mail']]);

        if (!$user || !$passwordHasher->isPasswordValid($user, $data['password'])) {
            return new JsonResponse(["message" => "Identifiants incorrects."], 401);
        }

        $token = $jwtManager->create($user);

        return new JsonResponse([
            "message" => "Authentification réussie.",
            "token" => $token,
            "user" => [
                "id" => $user->getId(),
                "mail" => $user->getMail(),
                "role" => $user->getRole(),
            ]
        ], 200);
    }
}
