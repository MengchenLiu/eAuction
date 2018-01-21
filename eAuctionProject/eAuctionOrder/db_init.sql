CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;


/************** ORDER ***************/
-- Order Status
DROP TABLE IF EXISTS `eAuction`.`order_status`;
CREATE TABLE `eAuction`.`order_status` (
	id INT AUTO_INCREMENT NOT NULL
	, name VARCHAR(100) NOT NULL
	, PRIMARY KEY (id)
);
INSERT INTO `eAuction`.`order_status` (name) VALUES ('Pending'), ('Paid'), ('Rejected'), ('Shipped');

-- Order
DROP TABLE IF EXISTS `eAuction`.`order`;
CREATE TABLE `eAuction`.`order` (
	id INT AUTO_INCREMENT NOT NULL
	, buyerId INT NOT NULL
	, amountDue DECIMAL(10,2) NOT NULL
	, paymentId INT NOT NULL
	, shippingId INT NOT NULL
	, orderStatusId INT NOT NULL
	, orderTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, PRIMARY KEY (id)
);

-- OrderPosting
DROP TABLE IF EXISTS `eAuction`.`order_posting`;
CREATE TABLE `eAuction`.`order_posting` (
	orderId INT NOT NULL
	, postingId INT NOT NULL
	, postingTypeId INT NOT NULL
	, PRIMARY KEY (orderId, postingId, postingTypeId)
);

/************** END OF ORDER ***************/