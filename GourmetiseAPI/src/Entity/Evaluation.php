<?php

namespace App\Entity;

use App\Repository\EvaluationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=EvaluationRepository::class)
 */
class Evaluation
{
    /**
     * @ORM\Id
     * @ORM\Column(type="string", length=14)
     * @Groups(["appmobile"])
     */
    private string $bakerySiren;

    /**
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity=Criteria::class)
     * @ORM\JoinColumn(nullable=false)
     * @Groups(["appmobile"])
     */
    private Criteria $criteria;

    /**
     * @ORM\Column(type="integer")
     * @Groups(["appmobile"])
     */
    private int $value;

    /**
     * @ORM\ManyToOne(targetEntity=Bakery::class)
     * @ORM\JoinColumn(nullable=false)
     */
    private Bakery $bakery;

    /**
     * @ORM\Column(type="datetime")
     * @Groups(["appmobile"])
     */
    private \DateTimeInterface $evaluationDate;

    public function __construct(string $bakerySiren, Criteria $criteria, int $value, Bakery $bakery, \DateTimeInterface $evaluationDate)
    {
        $this->bakerySiren = $bakerySiren;
        $this->criteria = $criteria;
        $this->value = $value;
        $this->bakery = $bakery;
        $this->evaluationDate = $evaluationDate;
    }

    public function getBakerySiren(): string
    {
        return $this->bakerySiren;
    }

    public function setBakerySiren(string $bakerySiren): self
    {
        $this->bakerySiren = $bakerySiren;
        return $this;
    }

    public function getCriteria(): Criteria
    {
        return $this->criteria;
    }

    public function setCriteria(Criteria $criteria): self
    {
        $this->criteria = $criteria;
        return $this;
    }

    public function getValue(): int
    {
        return $this->value;
    }

    public function setValue(int $value): self
    {
        $this->value = $value;
        return $this;
    }

    public function getBakery(): Bakery
    {
        return $this->bakery;
    }

    public function setBakery(Bakery $bakery): self
    {
        $this->bakery = $bakery;
        return $this;
    }

    public function getEvaluationDate(): \DateTimeInterface
    {
        return $this->evaluationDate;
    }

    public function setEvaluationDate(\DateTimeInterface $evaluationDate): self
    {
        $this->evaluationDate = $evaluationDate;
        return $this;
    }
}
