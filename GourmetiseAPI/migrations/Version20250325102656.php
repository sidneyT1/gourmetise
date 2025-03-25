<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250325102656 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP INDEX `primary` ON evaluation');
        $this->addSql('ALTER TABLE evaluation DROP name, CHANGE siren siren VARCHAR(20) NOT NULL');
        $this->addSql('ALTER TABLE evaluation ADD CONSTRAINT FK_1323A575DB8BBA08 FOREIGN KEY (siren) REFERENCES bakery (siren)');
        $this->addSql('CREATE INDEX IDX_1323A575DB8BBA08 ON evaluation (siren)');
        $this->addSql('ALTER TABLE evaluation ADD PRIMARY KEY (ticket_num)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE evaluation DROP FOREIGN KEY FK_1323A575DB8BBA08');
        $this->addSql('DROP INDEX IDX_1323A575DB8BBA08 ON evaluation');
        $this->addSql('DROP INDEX `PRIMARY` ON evaluation');
        $this->addSql('ALTER TABLE evaluation ADD name VARCHAR(50) NOT NULL, CHANGE siren siren VARCHAR(14) NOT NULL');
        $this->addSql('ALTER TABLE evaluation ADD PRIMARY KEY (siren)');
    }
}
