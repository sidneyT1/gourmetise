<?php

namespace App\Controller\API;
use App\Entity\User; // Ensure this is included
use App\Repository\UserRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use App\Entity\ContestParams;
use App\Repository\ContestParamsRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use DateTime;

class APIUserController extends AbstractController
{

    #[Route('/api/users', methods: ['POST'])]
    public function createUser(
        Request $request,
        UserRepository $userRepository,
        SerializerInterface $serializer
    ): JsonResponse {
        $data = $request->getContent();
    
        try {
            // Désérialiser le JSON en une instance de User
            $user = $serializer->deserialize($data, User::class, 'json');
            $user->setCreatedAt(new \DateTime());
    
            // Créer l'utilisateur et obtenir le token JWT
            $token = $userRepository->registerUser($user);
    
            return new JsonResponse(['token' => $token], Response::HTTP_CREATED);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], Response::HTTP_BAD_REQUEST);
        }
    }
    



}
