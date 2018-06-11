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
DROP TABLE IF EXISTS `Product`;
DROP TABLE IF EXISTS `Refreshment`;
DROP TABLE IF EXISTS `ParkingPass`;
DROP TABLE IF EXISTS `SeasonPass`;
DROP TABLE IF EXISTS `MovieTicket`;
DROP TABLE IF EXISTS `Customer`;
DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Address`;
DROP TABLE IF EXISTS `EmailList`;
DROP TABLE IF EXISTS `Invoice`;





--
-- Tables structure for Product
--

CREATE TABLE Product(
id INT,
productCode VARCHAR(255) NOT NULL,
productType VARCHAR(255) NOT NULL,
PRIMARY KEY(id)
);
-- Dumping data For table `Product`
INSERT INTO Product VALUES (),(),();

# create product related object table.
#Refreshment DB table
create table Refreshment(
id INT,
name varchar(255) NOT NULL,
cost float NOT NULL default '0.0',
haveTicket boolean NOT NULL default 0,# TODO: 
productID INT,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);


-- Dumping data For table `Refreshment`
INSERT INTO Refreshment VALUES (),(),();

# Parkingpass DB table.
create table ParkingPass(
id INT,
fee float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);

#Seasonpass DB table.
create table SeasonPass(
id INT,
name varchar(255) NOT NULL DEFAULT 'na',
startDate DATE NOT NULL,
endDate DATE NOT NULL,
cost float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Product(id),
PRIMARY KEY(id)
);

# create address DB table
create table Address(
id int AUTO_INCREMENT,
street varchar(255) NOT NULL DEFAULT 'na',
city varchar(255) NOT NULL DEFAULT 'na',
state varchar(255) NOT NULL DEFAULT 'na',
zipcode varchar(255) NOT NULL DEFAULT 'na',
country varchar(255) NOT NULL DEFAULT 'na',
primary key(id)
);

#Movie Ticket DB table
create table MovieTicket(
id INT,
movieName VARCHAR(255) NOT NULL,
screenNumber INT NOT NULL DEFAULT '0',
pricePerUnit FLOAT NOT NULL DEFAULT '0.0',
dateTime datetime NOT NULL,
addressID INT NOT NULL,
productID INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(productID) REFERENCES Product(id),
FOREIGN KEY(addressID) REFERENCES Address(id)
);

--
-- Tables structure for Person
--

# create PersonDB 
create table Person(
id INT,
personCode varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
firstName varchar(255) NOT NULL,
addressID INT NOT NULL,
primary key(id),
foreign key(addressID) references Address(id)
);



# create join table name EmailList 
create table EmailList(
id INT AUTO_INCREMENT,
personID INT NOT NULL,
email 	VARCHAR(255) NOT NULL,
PRIMARY KEY(id,email),
FOREIGN KEY(personID) REFERENCES Person(id)
);

--
-- Tables structure for Customer
--
CREATE TABLE Customer(
id INT,
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
id INT,
InvoiceCode VARCHAR(255) NOT NULL,
InvoiceDate DATE NOT NULL,
PRIMARY KEY(id)
);

#create table to store product associate to an invoice.
CREATE TABLE InvoiceProduct(
id INT,
invoiceID INT NOT NULL,
productID INT NOT NULL,
unit INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(invoiceID) REFERENCES Invoice(id),
FOREIGN KEY(productID) REFERENCES Product(id)
);