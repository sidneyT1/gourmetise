<?php

namespace App\Tests\Entity;

use App\Entity\User;
use PHPUnit\Framework\TestCase;

class UserTest extends TestCase
{
    public function testMail()
    {
        $user = new User();
        $user->setMail("test@gmail.com");
        $this->assertEquals("test@gmail.com", $user->getMail());
    }

    public function testPassword()
    {
        $user = new User();
        $user->setPassword("AxZ&&12Se");
        $this->assertEquals("AxZ&&12Se", $user->getPassword());
    }

    public function testRoles()
    {
        $user = new User();
        $user->setRole("ROLE_PARTICIPANT");
        $this->assertEquals(["ROLE_PARTICIPANT"], $user->getRoles());
    }
}
