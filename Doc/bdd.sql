CREATE DATABASE Logs_DB;
USE Logs_DB;

CREATE TABLE Logs_gourmetise
(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(50),
    action VARCHAR(50),
    USER VARCHAR(50),
    Date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    id_log INT
    
);



DELIMITER $$

CREATE TRIGGER after_insert_bakery
AFTER INSERT ON bakery
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('bakery', 'INSERT', NEW.user_id, NEW.siren);
END$$

CREATE TRIGGER after_insert_user
AFTER INSERT ON user
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('user', 'INSERT', NEW.mail, NEW.id);
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_update_bakery
AFTER UPDATE ON bakery
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('bakery', 'UPDATE', NEW.user_id, NEW.siren);
END$$

CREATE TRIGGER after_update_user
AFTER UPDATE ON user
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('user', 'UPDATE', NEW.mail, NEW.id);
END$$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_delete_bakery
AFTER DELETE ON bakery
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('bakery', 'DELETE', OLD.user_id, OLD.siren);
END$$

CREATE TRIGGER after_delete_user
AFTER DELETE ON user
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('user', 'DELETE', OLD.mail, OLD.id);
END$$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_insert_contest_params
AFTER INSERT ON contest_params
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('contest_params', 'INSERT', NULL, NEW.id);
END$$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_update_contest_params
AFTER UPDATE ON contest_params
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('contest_params', 'UPDATE', NULL, NEW.id);
END$$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_delete_contest_params
AFTER DELETE ON contest_params
FOR EACH ROW
BEGIN
    INSERT INTO Logs_DB.Logs_gourmetise (table_name, action, user, id_log)
    VALUES ('contest_params', 'DELETE', NULL, OLD.id);
END$$

DELIMITER ;
