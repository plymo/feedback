DELIMITER $$

DROP DATABASE IF EXISTS feedback $$
CREATE DATABASE feedback $$

USE feedback $$

DROP USER 'feedback'@'localhost'$$
CREATE USER 'feedback'@'localhost' IDENTIFIED BY 'feedback' $$


CREATE TABLE IF NOT EXISTS feedback (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  message VARCHAR(255) NOT NULL,
  agreedWithSpam TINYINT(1) DEFAULT 1,
  created DATETIME DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
$$

GRANT ALL ON feedback.* TO 'feedback'@'localhost' $$
GRANT ALL PRIVILEGES ON *.* TO 'feedback'@'%' IDENTIFIED BY 'feedback' WITH GRANT OPTION $$
FLUSH PRIVILEGES $$

DELIMITER ;
