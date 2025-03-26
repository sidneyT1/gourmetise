<?php

namespace App\Controller\API;

use App\Entity\Evaluation;
use App\Entity\Bakery;
use App\Repository\EvaluationRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Doctrine\ORM\EntityManagerInterface;

class APIEvaluationController extends AbstractController
{
    private EntityManagerInterface $entityManager;

   
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/api/export/evaluations', name: 'export_evaluations', methods: ['POST'])]
    public function exportEvaluations(Request $request): JsonResponse
    {
    
        $data = json_decode($request->getContent(), true);

       
        if (empty($data['evaluations']) || !is_array($data['evaluations'])) {
            return new JsonResponse(['message' => 'Invalid data'], JsonResponse::HTTP_BAD_REQUEST);
        }

        foreach ($data['evaluations'] as $evaluationData) {
            $evaluation = new Evaluation();
            $evaluation->setTicketNum($evaluationData['ticketNum']);
            $evaluation->setScore($evaluationData['score']);
            $evaluation->setEvaluationDate(new \DateTime($evaluationData['evaluationDate']));
        
            $bakery = $this->entityManager->getRepository(Bakery::class)->find($evaluationData['siren']);
            if ($bakery) {
                $evaluation->setBakery($bakery);
                $this->entityManager->persist($evaluation);
            } else {
                error_log("Bakery non trouvée pour le SIREN : " . $evaluationData['siren']);
            }
        }
        

        
        $this->entityManager->flush();

        return new JsonResponse(['message' => 'Les évaluations ont été importées avec succes'], JsonResponse::HTTP_OK);
    }
}
