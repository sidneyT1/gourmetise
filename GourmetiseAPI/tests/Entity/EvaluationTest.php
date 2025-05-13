<?php

namespace App\Tests\Entity;

use App\Entity\Evaluation;
use App\Entity\Bakery;
use PHPUnit\Framework\TestCase;

class EvaluationTest extends TestCase
{
    public function testEvaluationEntity(): void
    {
        $evaluation = new Evaluation();
        $ticketNum = 'ABC123456';
        $score = 17.5;
        $date = new \DateTimeImmutable('2025-02-13 16:00:00');

        $bakery = $this->createMock(Bakery::class); 

        $evaluation->setTicketNum($ticketNum);
        $evaluation->setScore($score);
        $evaluation->setEvaluationDate($date);
        $evaluation->setBakery($bakery);

        $this->assertSame($ticketNum, $evaluation->getTicketNum());
        $this->assertSame($score, $evaluation->getScore());
        $this->assertSame($date, $evaluation->getEvaluationDate());
        $this->assertSame($bakery, $evaluation->getBakery());
    }
}
