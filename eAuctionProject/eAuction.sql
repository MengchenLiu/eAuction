create table `eAuction`.`user`(
	`id` int NOT null auto_increment,
    `userName` varchar(100) NOT null ,
    `firstName` varchar(100) Not null,
    `lastName` varchar(100) Not null,
    `password` varchar(100) not null,
    `email` varchar(100) not null,
    `role` varchar(10) not null,
    primary key (`id`),
    index(`userName`))ENGINE= innodb; 

create table `eAuction`.`payment` (
	`id` int not null auto_increment,
    `cardNumber` varchar(20) not null,
    `holderName` varchar(40) not null,
    `cvs` int not null,
    `expiryMonth` int not null,
    `expiryYear` int not null,
    `userName` varchar(100) not null,
    primary key (`id`),
    index(`userName`)) ENGINE=innodb;

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

Update user set role = "normal" where userName= "haoze";



