CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;

/************** ACCOUNT ***************/
DROP TABLE IF EXISTS `eAuction`.`user`;
CREATE TABLE `eAuction`.`user`(
	`id` INT NOT NULL auto_increment
	, `userName` VARCHAR(100) NOT NULL
	, `firstName` VARCHAR(100) NOT NULL
	, `lastName` VARCHAR(100) NOT NULL
	, `password` VARCHAR(100) NOT NULL
	, `email` VARCHAR(100) NOT NULL
	, `role` VARCHAR(10) NOT NULL
	, PRIMARY KEY (`id`)
	, index(`userName`)
) ENGINE= innodb;

DROP TABLE IF EXISTS `eAuction`.`payment`;
CREATE TABLE `eAuction`.`payment` (
	`id` INT NOT NULL auto_increment
	, `cardNumber` VARCHAR(20) NOT NULL
	, `holderName` VARCHAR(40) NOT NULL
	, `cvs` INT NOT NULL
	, `expiryMonth` INT NOT NULL
	, `expiryYear` INT NOT NULL
	, `userName` VARCHAR(100) NOT NULL
	, PRIMARY KEY (`id`)
	, index(`userName`)
) ENGINE=innodb;

DROP TABLE IF EXISTS `eAuction`.`address`;
CREATE TABLE `eAuction`.`address` (
	`id` INT NOT NULL auto_increment
	, `country` VARCHAR(40) NOT NULL
	, `state` VARCHAR(40) NOT NULL
	, `city` varchar(40) NOT NULL
	, `addressStreet1` varchar(100) NOT NULL
	, `addressStreet2` varchar(100) NOT NULL
	, `userName` VARCHAR(100) NOT NULL
	, `zip` int not null
	, PRIMARY KEY (`id`)
	, index(`userName`)
) ENGINE=innodb;

DROP TABLE IF EXISTS `eAuction`.`shoppingcart`;
CREATE TABLE `eAuction`.`shoppingcart` (
	`id` INT NOT NULL auto_increment
	, `userName` VARCHAR(100) NOT NULL
	, `postingid` int not null
	, `quantity` int not null
	, `price` DOUBLE NOT NULL
	, `imageUrl` varchar(255) NOT NULL
	, `itemName` VARCHAR(100) NOT NULL
	, `typeId` int not null
	, PRIMARY KEY (`id`)
	, index(`userName`)
) ENGINE=innodb;


/************** END OF ACCOUNT ***************/
