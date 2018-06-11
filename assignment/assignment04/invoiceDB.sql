-- ---------------------------------------------
-- MySQL
--
-- Host: localhost    Database: datduyn
-- ------------------------------------------------------
-- Server version	5.1.57


#use ims;

# drop the old version before creating new DB
#drop database invoiceDB;

# create DB name invoiceDB;
#create database invoiceDB;

# Drop all databases if exist already

DROP TABLE IF EXISTS `InvoiceProduct`;
DROP TABLE IF EXISTS `Refreshment`;
DROP TABLE IF EXISTS `ParkingPass`;
DROP TABLE IF EXISTS `SeasonPass`;
DROP TABLE IF EXISTS `MovieTicket`;
DROP TABLE IF EXISTS `Product`;
DROP TABLE IF EXISTS `Customer`;
DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Address`;
DROP TABLE IF EXISTS `EmailList`;
DROP TABLE IF EXISTS `Invoice`;




--
-- Tables structure for Product
--

CREATE TABLE Product(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
productCode VARCHAR(255) NOT NULL,
productType VARCHAR(2) NOT NULL
);
-- Dumping data For table `Product`
INSERT INTO Product(productCode, productType) VALUES 
('b29e','S'),
('ff23','R'),
('fp12','M'),
('90fa','P'),
('1239','M'),
('782g','M'),
('3289','P'),
('32f4','R'),
('3y92','S'),
('90fb','P'),
('xer4','S'),
('yp23','R');

# create product related object table.
#Refreshment DB table
create table Refreshment(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL,
cost float NOT NULL default '0.0',
haveTicket boolean NOT NULL default 0,# TODO: 
productID INT,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);
-- Dumping data For table `Refreshment`
INSERT INTO Refreshment VALUES 
('Labatt Beer-20oz',4.99,0,2),
('Caramel Popcorn',5.50,0,8),
('Double Cheeseburger',9.00,0,12);

# Parkingpass DB table.
create table ParkingPass(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fee float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);
-- Dumping data For table `ParkingPass`
INSERT INTO ParkingPass VALUES 
(25.00,4),
(55.00,7),
(20.00,10);

#Seasonpass DB table.
create table SeasonPass(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL DEFAULT 'na',
startDate DATE NOT NULL,
endDate DATE NOT NULL,
cost float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);
-- Dumping data For table `ParkingPass`
INSERT INTO SeasonPass VALUES 
('Winter Special','2016-12-13','2017-01-07',120.00,1),
('BlackFriday Deal','2016-11-24','2016-11-29',58.00,9),
('FiveWeeks-Unlimited','2016-10-07','2016-11-11',185.00,11);

# create address DB table
create table Address(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
street varchar(255) NOT NULL DEFAULT 'na',
city varchar(255) NOT NULL DEFAULT 'na',
state varchar(255) NOT NULL DEFAULT 'na',
zipcode varchar(255) NOT NULL DEFAULT 'na',
country varchar(255) NOT NULL DEFAULT 'na',
primary key(id)
);
-- Dumping data For table `Address`
INSERT INTO Address(street,city,state,zipcode,country) VALUES 
('1060 West Addison Street','Chicago','IL','600613','USA'),
('123 N 1st Street','Omaha','NE','68116','USA'),
('8753 West 3rd Ave.','Dallas','TX','75001','USA'),
('Miles;123 Friendly Street','Ottawa','ON','K1A 0G9','Canada'),
('1 Wall Street	','New York','NY','10005-0012','USA'),
('321 Bronx Street','New York','NY','10004','USA'),
('1060 West Addison Street','Chicago','IL','60613','USA'),
('301 Front St W','Toronto','ON','M5V 2T6','Canada'),
('1 Blue Jays Way','Toronto','ON','M5V 1J1','Canada'),
('Campos El290','Mexico City', 'FD','', 'Mexico'),
('Avery Hall','Lincoln','NE','68503','USA'),
('126-01 Roosevelt Ave','Flushing','NY','11368','USA'),
('1 MetLife Stadium Dr', 'East Rutherford', 'NJ','07073','USA'),
('1 E 161st St', 'Bronx', 'NY','10451','USA'),
('700 E Grand Ave', 'Chicago', 'IL', '60611','USA'),
('333 W 35th St', 'Chicago', 'IL','60616','USA'),
('800 West 7th Street', 'Albuquerque', 'NM', '87105','USA'),
('123 Cabo San Lucas', 'Los Cabos', 'BCS','11111', 'Mexico');

#Movie Ticket DB table
create table MovieTicket(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
dateTime datetime NOT NULL,
movieName VARCHAR(255) NOT NULL,
addressID INT NOT NULL,
screenNumber VARCHAR(255) NOT NULL,
pricePerUnit FLOAT NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(productID) REFERENCES Product(id),
FOREIGN KEY(addressID) REFERENCES Address(id)
);
-- Dumping data For table `MovieTicket`
INSERT INTO MovieTicket VALUES 
('2016-10-21 13:10','A Monster Calls',,'2A',21.50,3),
('2016-09-13 15:30','The Wild Life',,'12F',11.00,5),
('2016-11-10 10:00','Shut In',,'32D',25.00,6);
--
-- Tables structure for Person
--

# create PersonDB 
create table Person(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personCode varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
firstName varchar(255) NOT NULL,
addressID INT NOT NULL,
primary key(id),
foreign key(addressID) references Address(id)
);



# create join table name EmailList 
create table EmailList(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personID INT NOT NULL,
email 	VARCHAR(255) NOT NULL,
PRIMARY KEY(id,email),
FOREIGN KEY(personID) REFERENCES Person(id)
);
INSERT INTO EmailList(PersonID,email) VALUES 
(),
(),
();


--
-- Tables structure for Customer
--
CREATE TABLE Customer(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customerCode VARCHAR(255) NOT NULL,
customerName VARCHAR(255) NOT NULL,
customerType VARCHAR(255) NOT NULL DEFAULT 'na', 
personID INT NOT NULL,
addressID INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(personID) REFERENCES Person(id),
FOREIGN KEY(AddressID) REFERENCES Address(id)
);
##TODO: create invoiceDB table.

--
-- Table structure for Invoices 
--
# create table to store Invoice attribute
CREATE TABLE Invoice(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
InvoiceCode VARCHAR(255) NOT NULL,
InvoiceDate DATE NOT NULL,
PRIMARY KEY(id)
);

#create table to store product associate to an invoice.
CREATE TABLE InvoiceProduct(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENTs,
invoiceID INT NOT NULL,
productID INT NOT NULL,
unit INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(invoiceID) REFERENCES Invoice(id),
FOREIGN KEY(productID) REFERENCES Product(id)
);