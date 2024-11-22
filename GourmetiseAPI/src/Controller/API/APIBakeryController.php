<?php

namespace App\Controller\API;

use App\Entity\Bakery;
use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\Response;
use DateTime;

class APIBakeryController extends AbstractController
{
    #[Route('/api/bakery', methods: ["POST"])]
    public function createBakery(
        Request $request,
        EntityManagerInterface $entityManager,
        SerializerInterface $serializer
    ): JsonResponse {
        $data = $request->getContent();

        try {
            
            
            $bakery = $serializer->deserialize($data, Bakery::class, 'json');

            
            $decodedData = json_decode($data, true);
            $userData = $decodedData['user'] ?? null;

            if (!$userData || !isset($userData['mail'])) {
                return new JsonResponse(['error' => 'User mail is required'], Response::HTTP_BAD_REQUEST);
            }

            
            $user = $entityManager->getRepository(User::class)->findOneBy(['mail' => $userData['mail']]);
            if (!$user) {
                return new JsonResponse(['error' => 'User not found'], Response::HTTP_BAD_REQUEST);
            }

            
            $bakery->setUser($user);

         
            if (!$bakery->getConditionsDate()) {
                $bakery->setConditionsDate(new DateTime());
            }

                            
            $entityManager->persist($bakery);
            $entityManager->flush();

            return $this->json('Création Bakery réussie', Response::HTTP_CREATED);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], Response::HTTP_BAD_REQUEST);
        }
    }
}
