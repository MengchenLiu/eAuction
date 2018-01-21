CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;

/************** CUSTOMER SERVICE ***************/
-- Message
DROP TABLE IF EXISTS `eAuction`.`message`;
CREATE TABLE `eAuction`.`message` (
	id INT AUTO_INCREMENT NOT NULL
	, userId INT NOT NULL
	, question VARCHAR(8000) NOT NULL
	, adminId INT NOT NULL
	, answer VARCHAR(8000) NOT NULL
	, lastModifiedTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, PRIMARY KEY (id)
);

/************** END OF CUSTOMER SERVICE ***************/