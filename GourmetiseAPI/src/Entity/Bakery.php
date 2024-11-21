<?php

namespace App\Entity;

use App\Repository\BakeryRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BakeryRepository::class)]
class Bakery
{
    #[ORM\Id]
    

    #[ORM\Column(length: 20)]
    private ?string $siren = null;

    #[ORM\Column(length: 50)]
    private ?string $name = null;

    #[ORM\Column(length: 100)]
    private ?string $street = null;

    #[ORM\Column(length: 5)]
    private ?string $postcode = null;

    #[ORM\Column(length: 20)]
    private ?string $city = null;

    #[ORM\Column(length: 10)]
    private ?string $phonenumber = null;

    #[ORM\Column(length: 30)]
    private ?string $contactname = null;

    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $description = null;

    #[ORM\Column]
    private ?bool $conditions_checkbox = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $conditions_date = null;

    #[ORM\OneToOne(targetEntity:User::class)]
    #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id', nullable: false, unique: true)]
    private ?User $user = null;

 

    public function getSiren(): ?string
    {
        return $this->siren;
    }

    public function setSiren(string $siren): static
    {
        $this->siren = $siren;

        return $this;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): static
    {
        $this->name = $name;

        return $this;
    }

    public function getStreet(): ?string
    {
        return $this->street;
    }

    public function setStreet(string $street): static
    {
        $this->street = $street;

        return $this;
    }

    public function getPostcode(): ?string
    {
        return $this->postcode;
    }

    public function setPostcode(string $postcode): static
    {
        $this->postcode = $postcode;

        return $this;
    }

    public function getCity(): ?string
    {
        return $this->city;
    }

    public function setCity(string $city): static
    {
        $this->city = $city;

        return $this;
    }

    public function getPhonenumber(): ?string
    {
        return $this->phonenumber;
    }

    public function setPhonenumber(string $phonenumber): static
    {
        $this->phonenumber = $phonenumber;

        return $this;
    }

    public function getContactname(): ?string
    {
        return $this->contactname;
    }

    public function setContactname(string $contactname): static
    {
        $this->contactname = $contactname;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function isConditionsCheckbox(): ?bool
    {
        return $this->conditions_checkbox;
    }

    public function setConditionsCheckbox(bool $conditions_checkbox): static
    {
        $this->conditions_checkbox = $conditions_checkbox;

        return $this;
    }

    public function getConditionsDate(): ?\DateTimeInterface
    {
        return $this->conditions_date;
    }

    public function setConditionsDate(\DateTimeInterface $conditions_date): static
    {
        $this->conditions_date = $conditions_date;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(User $user): static
    {
        $this->user = $user;

        return $this;
    }
}
