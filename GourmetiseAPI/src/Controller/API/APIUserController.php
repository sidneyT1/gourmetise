<?php

namespace App\Controller\API;
use App\Entity\User; // Ensure this is included
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

    #[Route('/api/users', methods :["POST"])]
    public function createUser(
    Request $request,
    EntityManagerInterface $entityManager,
    SerializerInterface $serializer
    ) : JsonResponse
    {
    // récupérer le contenu JSON de la requête
    $data = $request->getContent();
    

    try {
    // désérialiser le JSON en une instance de l'entité Concurrent
    $user = $serializer->deserialize($data, User::class, 'json');


    $user->setCreatedAt(new DateTime());
  
    // enregistrer le nouveau Concurrent dans la base de données
    $entityManager->persist($user);
    $entityManager->flush();
    // envoyer réponse de succès de la création
    return $this->json( 'Création User réussie', Response::HTTP_CREATED);
    }
     catch (\Exception $e) {
    return new JsonResponse( ['error' => $e->getMessage()],
     Response::HTTP_BAD_REQUEST);
    }
    }





}