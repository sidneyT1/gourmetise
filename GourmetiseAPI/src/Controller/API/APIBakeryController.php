<?php

namespace App\Controller\API;
use App\Entity\Bakery; 
use App\Entity\User; 
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

use App\Repository\BakeryRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use DateTime;

class APIUserController extends AbstractController
{

    #[Route('/api/bakery', methods :["POST"])]
    public function createUser(
    Request $request,
    EntityManagerInterface $entityManager,
    SerializerInterface $serializer
    ) : JsonResponse
    {
 
    $data = $request->getContent();
    

    try {
    
    $bakery = $serializer->deserialize($data, User::class, 'json');


    $bakery->setCreatedAt(new DateTime());
  
   
    $entityManager->persist($bakery);
    $entityManager->flush();
   
    return $this->json( 'Création Bakery réussie', Response::HTTP_CREATED);
    }
     catch (\Exception $e) {
    return new JsonResponse( ['error' => $e->getMessage()],
     Response::HTTP_BAD_REQUEST);
    }
    }





}