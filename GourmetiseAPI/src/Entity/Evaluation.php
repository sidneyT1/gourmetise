<?php

namespace App\Entity;

use App\Repository\EvaluationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: EvaluationRepository::class)]
class Evaluation
{
    #[ORM\Id]
    #[ORM\Column(length: 100)]
    #[Groups(['appmobile'])]
    private ?string $ticketNum = null;

    #[ORM\Column(type: 'decimal', precision: 5, scale: 2)]
    #[Groups(['appmobile'])]
    private ?float $score = null;

    #[ORM\Column(type: 'datetime')]
    #[Groups(['appmobile'])]
    private ?\DateTimeInterface $evaluationDate = null;

    #[ORM\ManyToOne(targetEntity: Bakery::class)]
    #[ORM\JoinColumn(name: "siren", referencedColumnName: "siren", nullable: false)]
    #[Groups(['appmobile'])]
    private ?Bakery $bakery = null;

    public function getScore(): ?float
    {
        return $this->score;
    }

    public function setScore(float $score): static
    {
        $this->score = $score;
        return $this;
    }

    public function getTicketNum(): ?string
    {
        return $this->ticketNum;
    }

    public function setTicketNum(string $ticketNum): static
    {
        $this->ticketNum = $ticketNum;
        return $this;
    }

    public function getEvaluationDate(): ?\DateTimeInterface
    {
        return $this->evaluationDate;
    }

    public function setEvaluationDate(\DateTimeInterface $evaluationDate): static
    {
        $this->evaluationDate = $evaluationDate;
        return $this;
    }

    public function getBakery(): ?Bakery
    {
        return $this->bakery;
    }

    public function setBakery(?Bakery $bakery): static
    {
        $this->bakery = $bakery;
        return $this;
    }
}
