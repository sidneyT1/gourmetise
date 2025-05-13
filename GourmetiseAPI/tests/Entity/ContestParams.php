<?php

namespace App\Tests\Entity;

use App\Entity\ContestParams;
use PHPUnit\Framework\TestCase;

class ContestParamsTest extends TestCase
{
    public function testTitleAndDescription()
    {
        $contest = new ContestParams();
        $contest->setTitle("Concours 2025");
        $contest->setDescription("Édition annuelle du concours");

        $this->assertEquals("Concours 2025", $contest->getTitle());
        $this->assertEquals("Édition annuelle du concours", $contest->getDescription());
    }

    public function testDates()
    {
        $now = new \DateTime();
        $contest = new ContestParams();
        $contest->setStartRegistration($now);
        $contest->setEndRegistration($now);

        $this->assertSame($now, $contest->getStartRegistration());
        $this->assertSame($now, $contest->getEndRegistration());
    }
}
