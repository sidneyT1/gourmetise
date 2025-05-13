<?php

namespace App\Tests\Entity;

use App\Entity\Bakery;
use App\Entity\User;
use PHPUnit\Framework\TestCase;

class BakeryTest extends TestCase
{
    public function testBakeryInformations()
    {
        $bakery = new Bakery();
        $bakery->setName("La Boulangerie Test")
               ->setSiren("12345678900000")
               ->setStreet("12 rue montparnasse")
               ->setPostcode("75001")
               ->setCity("Paris")
               ->setPhonenumber("0602030405")
               ->setContactname("Alice Trelles")
               ->setDescription("Meilleure baguette")
               ->setConditionsCheckbox(true);

        $this->assertEquals("La Boulangerie Test", $bakery->getName());
        $this->assertEquals("12345678900000", $bakery->getSiren());
        $this->assertEquals(true, $bakery->isConditionsCheckbox());
    }

    public function testBakeryUserAssociation()
    {
        $user = new User();
        $user->setMail("bakery@gmail.com");

        $bakery = new Bakery();
        $bakery->setUser($user);

        $this->assertSame($user, $bakery->getUser());
    }
}
