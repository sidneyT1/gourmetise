<?php

namespace App\Controller\API;

use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\DBAL\Connection;
use App\Repository\ContestParamsRepository;
use App\Repository\BakeryRepository;
use Symfony\Component\HttpFoundation\Response;

class APIBakeryScoreController extends AbstractController
{
    private Connection $connection;
    private ContestParamsRepository $contestParamsRepository;
    private BakeryRepository $bakeryRepository;

    public function __construct(
        Connection $connection,
        ContestParamsRepository $contestParamsRepository,
        BakeryRepository $bakeryRepository
    ) {
        $this->connection = $connection;
        $this->contestParamsRepository = $contestParamsRepository;
        $this->bakeryRepository = $bakeryRepository;
    }

    #[Route('/api/bakery/score', methods: ["GET"])]
    public function getBakeryScore(): JsonResponse
{
    $user = $this->getUser();

    if (!$user) {
        return new JsonResponse(['error' => 'Utilisateur non authentifié.'], Response::HTTP_UNAUTHORIZED);
    }

    $bakery = $this->bakeryRepository->findOneBy(['user' => $user]);

    if (!$bakery) {
        return new JsonResponse(['error' => 'Aucune boulangerie associée à cet utilisateur.'], Response::HTTP_NOT_FOUND);
    }

    $contestParams = $this->contestParamsRepository->find(1);
    if (!$contestParams) {
        return new JsonResponse(['error' => 'Paramètres du concours introuvables.'], Response::HTTP_NOT_FOUND);
    }

    $endEvaluationDate = $contestParams->getEndEvaluation();
    $currentDate = new \DateTime();
    if ($currentDate < $endEvaluationDate) {
        return new JsonResponse(['error' => 'La période d\'évaluation n\'est pas encore terminée.'], Response::HTTP_BAD_REQUEST);
    }

    $sql = "
        SELECT b.siren, b.name AS bakery_name, AVG(e.score) AS avg_score
        FROM evaluation e
        INNER JOIN bakery b ON e.siren = b.siren
        GROUP BY b.siren, b.name
        ORDER BY avg_score DESC
    ";

    $stmt = $this->connection->executeQuery($sql);
    $results = $stmt->fetchAllAssociative();

    $personalScore = null;
    $rank = null;
    foreach ($results as $index => $row) {
        if ($row['siren'] === $bakery->getSiren()) {
            $personalScore = $row;
            $rank = $index + 1; 
            break;
        }
    }

    if (!$personalScore) {
        return new JsonResponse(['error' => 'Score de la boulangerie non trouvé.'], Response::HTTP_NOT_FOUND);
    }

    $personalScore['rank'] = $rank;

    return new JsonResponse($personalScore, Response::HTTP_OK);
}

}
