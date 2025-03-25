<?php

namespace App\Repository;

use App\Entity\Evaluation;
use App\Entity\Bakery;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class EvaluationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Evaluation::class);
    }

    public function calculateAndCreateEvaluation(): void
    {
        $conn = $this->getEntityManager()->getConnection();
        
    
        $sql = "
            SELECT
                b.siren,
                b.name,
                n.ticketNum,
                n.evaluationDate,
                SUM(n.value) AS average_score
            FROM bakery b
            JOIN note n ON b.siren = n.bakery_siren
            GROUP BY b.siren, b.name, n.ticketNum, n.evaluationDate
        ";

        $stmt = $conn->prepare($sql);
        $result = $stmt->executeQuery()->fetchAllAssociative();

        foreach ($result as $row) {
          
            $bakery = $this->getEntityManager()->getRepository(Bakery::class)->findOneBy(['siren' => $row['siren']]);

            if ($bakery) {
              
                $evaluation = new Evaluation();
                $evaluation->setBakery($bakery);  
                $evaluation->setTicketNum($row['ticketNum']);
                $evaluation->setEvaluationDate(new \DateTime($row['evaluationDate']));
                $evaluation->setScore(round($row['average_score'], 2));

                $this->_em->persist($evaluation);
            }
        }

     
        $this->_em->flush();
    }
}
