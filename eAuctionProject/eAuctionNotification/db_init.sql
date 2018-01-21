CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;

/************** NOTIFICATION ***************/
-- Watchlist
DROP TABLE IF EXISTS `eAuction`.`watchlist`;
CREATE TABLE `eAuction`.`watchlist` (
	id INT NOT NULL auto_increment
	, email VARCHAR(100) NOT NULL
	, itemId INT NOT NULL
	, itemName VARCHAR(100) NOT NULL
	, price DOUBLE NOT NULL
	, userName VARCHAR(100) NOT NULL
	, PRIMARY KEY (id)
);

-- UserEmail
DROP TABLE IF EXISTS `eAuction`.`useremail`;
CREATE TABLE `eAuction`.`useremail` (
	id INT NOT NULL auto_increment
	, email VARCHAR(100) NOT NULL
	, userId INT NOT NULL
	, PRIMARY KEY (id)
);
/************** END OF NOTIFICATION ***************/
