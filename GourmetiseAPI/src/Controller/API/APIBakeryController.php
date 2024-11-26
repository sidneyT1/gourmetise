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

            if (!$bakery->getSiren()) {
                return new JsonResponse(['error' => 'Le champ siren est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getName()) {
                return new JsonResponse(['error' => 'Le champ name est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getStreet()) {
                return new JsonResponse(['error' => 'Le champ street est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getPostcode()) {
                return new JsonResponse(['error' => 'Le champ postcode est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getCity()) {
                return new JsonResponse(['error' => 'Le champ city est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getPhonenumber()) {
                return new JsonResponse(['error' => 'Le champ phonenumber est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if (!$bakery->getContactname()) {
                return new JsonResponse(['error' => 'Le champ contactname est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }
            if ($bakery->isConditionsCheckbox() === null) {
                return new JsonResponse(['error' => 'Le champ conditions_checkbox est obligatoire.'], Response::HTTP_BAD_REQUEST);
            }

            $existingSiren = $entityManager->getRepository(Bakery::class)->findOneBy(['siren' => $bakery->getSiren()]);
            if ($existingSiren) {
                return new JsonResponse(['error' => 'Le SIREN existe déjà.'], Response::HTTP_BAD_REQUEST);
            }

            $user = $bakery->getUser();
            if (!$user || !$user->getMail()) {
                return new JsonResponse(['error' => 'Veuillez fournir un email utilisateur valide.'], Response::HTTP_BAD_REQUEST);
            }

            $existingUser = $entityManager->getRepository(User::class)->findOneBy(['mail' => $user->getMail()]);
            if (!$existingUser) {
                return new JsonResponse(['error' => 'Utilisateur introuvable.'], Response::HTTP_BAD_REQUEST);
            }

            if ($existingUser->getRole() !== 'Participant') {
                return new JsonResponse(['error' => 'L\'utilisateur n\'est pas un participant'], Response::HTTP_BAD_REQUEST);
            }

            $contestParams = $entityManager->getRepository(ContestParams::class)->find(1);
            $now = new DateTime();
            //  if ($now < $contestParams->getStartRegistration() || $now > $contestParams->getEndRegistration()) {
            //    return new JsonResponse(['error' => 'La période d\'inscription est terminée.'], Response::HTTP_FORBIDDEN);
            //}

            $bakery->setUser($existingUser);

            if (!$bakery->getConditionsDate()) {
                $bakery->setConditionsDate(new DateTime());
            }

            $entityManager->persist($bakery);
            $entityManager->flush();

            return new JsonResponse(['message' => 'Création Bakery réussie'], Response::HTTP_CREATED);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], Response::HTTP_BAD_REQUEST);
        }
    }
}
