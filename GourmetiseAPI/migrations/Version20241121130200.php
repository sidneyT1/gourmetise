<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20241121130200 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE bakery (siren VARCHAR(20) NOT NULL, user_id INT NOT NULL, name VARCHAR(50) NOT NULL, street VARCHAR(100) NOT NULL, postcode VARCHAR(5) NOT NULL, city VARCHAR(20) NOT NULL, phonenumber VARCHAR(10) NOT NULL, contactname VARCHAR(30) NOT NULL, description LONGTEXT DEFAULT NULL, conditions_checkbox TINYINT(1) NOT NULL, conditions_date DATETIME NOT NULL, UNIQUE INDEX UNIQ_C647FA2AA76ED395 (user_id), PRIMARY KEY(siren)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, mail VARCHAR(30) NOT NULL, password VARCHAR(255) NOT NULL, created_at DATETIME NOT NULL, updated_at DATETIME DEFAULT NULL, role VARCHAR(30) NOT NULL, UNIQUE INDEX UNIQ_8D93D6495126AC48 (mail), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE bakery ADD CONSTRAINT fk_bakery_user FOREIGN KEY (user_id) REFERENCES user (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE bakery DROP FOREIGN KEY fk_bakery_user');
        $this->addSql('DROP TABLE bakery');
        $this->addSql('DROP TABLE user');
    }
}
