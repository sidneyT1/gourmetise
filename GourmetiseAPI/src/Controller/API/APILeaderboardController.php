<?php

namespace App\Controller\API;

use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\DBAL\Connection;
use App\Repository\ContestParamsRepository;

class APILeaderboardController extends AbstractController
{
    private Connection $connection;
    private ContestParamsRepository $contestParamsRepository;

    // Injection du repository pour récupérer les paramètres du concours
    public function __construct(Connection $connection, ContestParamsRepository $contestParamsRepository)
    {
        $this->connection = $connection;
        $this->contestParamsRepository = $contestParamsRepository;
    }

    #[Route('/api/leaderboard', methods: ["GET"])]
    public function getTopBakeries(): JsonResponse
    {
        try {
            $contestParams = $this->contestParamsRepository->find(1); 
            if (!$contestParams) {
                return new JsonResponse(['error' => 'Contest parameters not found.'], 404);
            }

            $endEvaluationDate = $contestParams->getEndEvaluation(); 

            $currentDate = new \DateTime();
            if ($currentDate < $endEvaluationDate) {
                return new JsonResponse(['error' => 'La période d\'évaluation n\'est pas encore terminée.'], 400);
            }

            $sql = "
                SELECT b.siren, b.name AS bakery_name, AVG(e.score) AS avg_score
                FROM evaluation e
                INNER JOIN bakery b ON e.siren = b.siren
                GROUP BY b.siren, b.name
                ORDER BY avg_score DESC
                LIMIT 3
            ";

            $stmt = $this->connection->executeQuery($sql);
            $topBakeries = $stmt->fetchAllAssociative();

            return new JsonResponse($topBakeries, 200);
        } catch (\Exception $e) {
            return new JsonResponse(['error' => $e->getMessage()], 500);
        }
    }
}
