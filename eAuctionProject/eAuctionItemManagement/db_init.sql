CREATE DATABASE IF NOT EXISTS eAuction;

USE eAuction;


/************** ITEM MANAGEMENT ***************/
-- Category
DROP TABLE IF EXISTS `eAuction`.`category`;
CREATE TABLE `eAuction`.`category` (
	id INT AUTO_INCREMENT NOT NULL
	, name VARCHAR(100) NOT NULL
	, PRIMARY KEY (id)
);

INSERT INTO `eAuction`.`category`
(name)
VALUES
('Automotive & Powersports')
, ('Baby Products (Excluding Apparel)')
, ('Beauty & Personal Care')
, ('Books')
, ('Business Products (B2B)')
, ('Camera & Photo')
, ('Cell Phones')
, ('Clothing & Accessories')
, ('Collectible Coins')
, ('Electronics (Accessories)')
, ('Electronics (Consumer)')
, ('Fine Art')
, ('Grocery & Gourmet Food')
, ('Handmade')
, ('Health & Personal Care')
, ('Historical & Advertising Collectibles')
, ('Home & Garden')
, ('Industrial & Scientific')
, ('Jewelry')
, ('Luggage & Travel Accessories')
, ('Music')
, ('Musical Instruments')
, ('Office Products')
, ('Outdoors')
, ('Personal Computers')
, ('Professional Services')
, ('Shoes, Handbags & Sunglasses')
, ('Software & Computer Games')
, ('Sports')
, ('Sports Collectibles')
, ('Tools & Home Improvement')
, ('Toys & Games')
, ('Video, DVD & Blu-Ray')
, ('Video Games & Video Game Consoles')
, ('Watches')
;


-- Item
DROP TABLE IF EXISTS `eAuction`.`item`;
CREATE TABLE `eAuction`.`item` (
	id INT AUTO_INCREMENT NOT NULL
	, name VARCHAR(100) NOT NULL
	, description VARCHAR(255) NULL
	, imageUrl VARCHAR(255) NULL
	, categoryName VARCHAR(100) NULL
	, PRIMARY KEY (id)
);

INSERT INTO `eAuction`.`item`
(name, description, imageUrl, categoryName)
VALUES
('iPhone X', 'iPhone X is a smartphone designed, developed, and marketed by Apple Inc. It was announced on September 12, 2017.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/IPhone_X_vector.svg/330px-IPhone_X_vector.svg.png', 'Cell Phones')
, ('Sony Cyber-shot DSC-RX100 IV', 'The Sony Cyber-shot DSC-RX100 IV is a digital premium compact camera announced by Sony on June 10, 2015.', 'https://sonyglobal.scene7.com/is/image/gwtprod/59416b80c62a6a184e16b04ec3a60143?fmt=pjpeg&wid=165&bgcolor=FFFFFF&bgc=FFFFFF', 'Camera & Photo')
, ('Docker Containers: Build and Deploy with Kubernetes, Flannel, Cockpit, and Atomic', 'Docker Containers Includes Content Update Program Build and Deploy with Kubernetes Flannel Cockpit and Atomic', 'https://images-na.ssl-images-amazon.com/images/I/51dOSTXyEdL._SX404_BO1,204,203,200_.jpg', 'Books')
, ('FIFA 18 Standard Edition - PS4', 'FIFA 18 introduces Real Player Motion Technology, an all-new animation system which unlocks a new level of responsiveness, and player personality.', 'https://images-na.ssl-images-amazon.com/images/I/51hLY34ZmyL._SX215_.jpg', 'Video Games & Video Game Consoles')
, ('Philips Norelco OneBlade hybrid electric trimmer and shaver, QP2520/70', 'Rechargeable OneBlade can trim, edge, and shave any length of hair.', 'https://images-na.ssl-images-amazon.com/images/I/71TTWTnZhpL._SY679_.jpg', 'Beauty & Personal Care')
;

/************** END OF ITEM MANAGEMENT ***************/
