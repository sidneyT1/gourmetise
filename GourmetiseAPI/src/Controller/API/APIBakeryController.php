<?php

namespace App\Controller\API;

use App\Entity\Bakery;
use App\Entity\User;
use App\Entity\ContestParams;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\Response;
use DateTime;
use Doctrine\DBAL\Exception\UniqueConstraintViolationException;  



class APIBakeryController extends AbstractController
{
    #[Route('/api/bakery', methods: ["GET"])]
    public function getBakeries(EntityManagerInterface $entityManager, SerializerInterface $serializer): JsonResponse {
        try {
            $bakeries = $entityManager->getRepository(Bakery::class)->findAll();

            
            $data = $serializer->serialize($bakeries, 'json', ['groups' => ['appmobile']]);

            return new JsonResponse($data, Response::HTTP_OK, [], true);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

        #[Route('/api/bakery', methods: ["POST"])]
        public function createBakery(
            Request $request,
            EntityManagerInterface $entityManager,
            SerializerInterface $serializer
        ): JsonResponse {
            $data = $request->getContent();

            try {
                $bakery = $serializer->deserialize($data, Bakery::class, 'json');

                if (!$bakery->getSiren()) return new JsonResponse(['error' => 'Le champ siren est obligatoire.'], 400);
                if (!$bakery->getName()) return new JsonResponse(['error' => 'Le champ name est obligatoire.'], 400);
                if (!$bakery->getStreet()) return new JsonResponse(['error' => 'Le champ street est obligatoire.'], 400);
                if (!$bakery->getPostcode()) return new JsonResponse(['error' => 'Le champ postcode est obligatoire.'], 400);
                if (!$bakery->getCity()) return new JsonResponse(['error' => 'Le champ city est obligatoire.'], 400);
                if (!$bakery->getPhonenumber()) return new JsonResponse(['error' => 'Le champ phonenumber est obligatoire.'], 400);
                if (!$bakery->getContactname()) return new JsonResponse(['error' => 'Le champ contactname est obligatoire.'], 400);
                if ($bakery->isConditionsCheckbox() === null) return new JsonResponse(['error' => 'Le champ conditions_checkbox est obligatoire.'], 400);

                $existingSiren = $entityManager->getRepository(Bakery::class)->findOneBy(['siren' => $bakery->getSiren()]);
                if ($existingSiren) return new JsonResponse(['error' => 'Le SIREN existe déjà.'], 400);

                $connectedUser = $this->getUser();
                if (!$connectedUser instanceof User) {
                    return new JsonResponse(['error' => 'Utilisateur non connecté.'], 401);
                }

                if (!in_array('Participant', $connectedUser->getRoles())) {
                    return new JsonResponse(['error' => 'L\'utilisateur n\'est pas un participant'], 403);
                }

                $existingUserBakery = $entityManager->getRepository(Bakery::class)->findOneBy(['user' => $connectedUser]);
                if ($existingUserBakery) {
                    return new JsonResponse(['error' => 'L\'utilisateur est déjà inscrit.'], 400);
                }

                $contestParams = $entityManager->getRepository(ContestParams::class)->find(1);
                $now = new \DateTime();

                if ($now < $contestParams->getStartRegistration() || $now > $contestParams->getEndRegistration()) {
                    return new JsonResponse(['error' => 'Vous êtes hors période d\'inscription'], 403);
                }

                $bakery->setUser($connectedUser);
                if (!$bakery->getConditionsDate()) {
                    $bakery->setConditionsDate(new \DateTime());
                }

                $entityManager->persist($bakery);
                $entityManager->flush();

                return new JsonResponse(['message' => 'Création Bakery réussie'], 201);
            } catch (\Doctrine\DBAL\Exception\UniqueConstraintViolationException $e) {
                return new JsonResponse(['error' => 'L\'utilisateur est déjà enregistré.'], 400);
            } catch (\Exception $e) {
                return new JsonResponse(['error' => 'Une erreur est survenue: ' . $e->getMessage()], 400);
            }
        }

}
