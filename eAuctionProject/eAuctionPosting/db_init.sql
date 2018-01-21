CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;


/************** POSTING ***************/
-- Posting Type
DROP TABLE IF EXISTS `eAuction`.`posting_type`;
CREATE TABLE `eAuction`.`posting_type` (
	id INT AUTO_INCREMENT NOT NULL
	, name VARCHAR(100) NOT NULL
	, PRIMARY KEY (id)
);

INSERT INTO `eAuction`.`posting_type` (name) VALUES ('Buy It Now'), ('Auction');
 
-- Posting Buy It Now
DROP TABLE IF EXISTS `eAuction`.`posting_buy_it_now`;
CREATE TABLE `eAuction`.`posting_buy_it_now`(
	id INT AUTO_INCREMENT NOT NULL
	, itemId INT NOT NULL
	, itemName VARCHAR(100) NOT NULL
	, quantity INT NOT NULL
	, sellerId INT NOT NULL
	, price DECIMAL(10,2) NOT NULL
	, description VARCHAR(8000)
	, imageUrl VARCHAR(255)
	, createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, startTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, expirationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, lastModifiedTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, isNotified TINYINT UNSIGNED DEFAULT 0
	, isDeleted TINYINT UNSIGNED DEFAULT 0
	, PRIMARY KEY (id)
);

-- Posting Auction
DROP TABLE IF EXISTS `eAuction`.`posting_auction`;
CREATE TABLE `eAuction`.`posting_auction`(
	id INT AUTO_INCREMENT NOT NULL
	, itemId INT NOT NULL
	, itemName VARCHAR(100) NOT NULL
	, quantity INT NOT NULL
	, sellerId INT NOT NULL
	, startPrice DECIMAL(10,2) NOT NULL
	, curPrice DECIMAL(10,2) NOT NULL
	, curBidder INT NOT NULL
	, description VARCHAR(8000)
	, imageUrl VARCHAR(255) NULL
	, createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, startTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, expirationTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, lastModifiedTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, isNotified TINYINT UNSIGNED DEFAULT 0
	, isAddedToCart TINYINT UNSIGNED DEFAULT 0
	, isDeleted TINYINT UNSIGNED DEFAULT 0
	, PRIMARY KEY (id)
);

-- Auction Bid History
DROP TABLE IF EXISTS `eAuction`.`auction_bid_history`;
CREATE TABLE `eAuction`.`auction_bid_history` (
	historyId INT AUTO_INCREMENT NOT NULL
	, postingAuctionId INT NOT NULL
	, bidderId INT NOT NULL
	, bidPrice DECIMAL(10,2) NOT NULL
	, bidTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, PRIMARY KEY (historyId)
);

/************** END OF POSTING ***************/
