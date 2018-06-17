-- ---------------------------------------------
-- MySQL
-- @ Author Dat Nguyen and Reid Stagemeyer
-- Host: localhost    Database: datduyn
-- ------------------------------------------------------
-- Server version	5.1.57






-- Uncomment these piece to test them locally
-- use rstagemeyer;
-- use datduyn;
-- use ims;

--  drop the old version before creating new DB
DROP TABLE IF EXISTS `InvoiceProducts`;
DROP TABLE IF EXISTS `Invoices`;
DROP TABLE IF EXISTS `Refreshments`;
DROP TABLE IF EXISTS `ParkingPasses`;
DROP TABLE IF EXISTS `SeasonPasses`;
DROP TABLE IF EXISTS `MovieTickets`;
DROP TABLE IF EXISTS `Products`;
DROP TABLE IF EXISTS `Customers`;
DROP TABLE IF EXISTS `Emails`;
DROP TABLE IF EXISTS `Persons`;
DROP TABLE IF EXISTS `Addresses`;
DROP TABLE IF EXISTS `StateCountries`;
DROP TABLE IF EXISTS `Countries`;

--
-- Tables structure for Products
--

CREATE TABLE Products(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
productCode VARCHAR(255) NOT NULL,
productType VARCHAR(2) NOT NULL
);
-- Dumping data For table `Product`
INSERT INTO Products(productCode, productType) VALUES
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

-- create product related object table.
-- Refreshment DB table
CREATE TABLE Refreshments(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL,
cost float NOT NULL default '0.0',
haveTicket boolean NOT NULL default 0,# TODO:
productID INT,
FOREIGN KEY(productID) REFERENCES Products(id)
);
-- Dumping data For table `Refreshment`
INSERT INTO Refreshments(name, cost, haveTicket, productID) VALUES
('Labatt Beer-20oz',4.99,0,2),
('Caramel Popcorn',5.50,0,8),
('Double Cheeseburger',9.00,0,12);

-- Parkingpass DB table.
CREATE TABLE ParkingPasses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fee float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);
-- Dumping data For table `ParkingPass`
INSERT INTO ParkingPasses(fee, productID) VALUES
(25.00,4),
(55.00,7),
(20.00,10);

#Seasonpass DB table.
CREATE TABLE SeasonPasses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL DEFAULT 'na',
startDate DATE NOT NULL,
endDate DATE NOT NULL,
cost float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);
-- Dumping data For table `ParkingPass`
INSERT INTO SeasonPasses(name, startDate, endDate, cost, productID) VALUES
('Winter Special','2016-12-13','2017-01-07',120.00,1),
('BlackFriday Deal','2016-11-24','2016-11-29',58.00,9),
('FiveWeeks-Unlimited','2016-10-07','2016-11-11',185.00,11);

-- create Countries, States databases table
CREATE TABLE Countries(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL DEFAULT 'na'
);

-- dumping data for Countries
INSERT INTO Countries(name)
VALUES ('USA'),('Canada'),('Mexico');


-- create States database table
CREATE TABLE StateCountries(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(5) NOT NULL DEFAULT 'na',
fullName VARCHAR(255) NOT NULL DEFAULT 'na',
countryID INT NOT NULL,
FOREIGN KEY(countryID) REFERENCES Countries(id)
);
-- Dumping data for States
INSERT INTO StateCountries(name,fullName,countryID) VALUES
('AL', 'Alabama',1),('AK', 'Alaska',1),('AZ', 'Arizona',1),('AR', 'Arkansas',1),
('CA', 'California',1),('CO', 'Colorado',1),('CT', 'Connecticut',1),('DE', 'Delaware',1),
('DC', 'District of Columbia',1),('FL', 'Florida',1),('GA', 'Georgia',1),('HI', 'Hawaii',1),
('ID', 'Idaho',1),('IL', 'Illinois',1),('IA', 'Iowa',1),('KS', 'Kansas',1),('KY', 'Kentucky',1),
('LA', 'Louisiana',1),('ME', 'Maine',1),('MD', 'Maryland',1),('MA', 'Massachusetts',1),
('MI', 'Michigan',1),('MN', 'Minnesota',1),('MS', 'Mississippi',1),('MO', 'Missouri',1),
('MT', 'Montana',1),('NE', 'Nebraska',1),('NV', 'Nevada',1),('NH', 'New Hampshire',1),
('NJ', 'New Jersey',1),('NM', 'New Mexico',1),('NY', 'New York',1),('NC', 'North Carolina',1),
('ND', 'North Dakota',1),('OH', 'Ohio',1),('OK', 'Oklahoma',1),('OR', 'Oregon',1),
('PA', 'Pennsylvania',1),('PR', 'Puerto Rico',1),('RI', 'Rhode Island',1),('SC', 'South Carolina',1),
('SD', 'South Dakota',1),('TN', 'Tennessee',1),('TX', 'Texas',1),('UT', 'Utah',1),
('VT', 'Vermont',1),('VA', 'Virginia',1),('WA', 'Washington',1),('WV', 'West Virginia',1),
('WI', 'Wisconsin',1),('WY', 'Wyoming',1),('ON','Ontra',2),('FD','Farade',3),('BCS','BaoCCongSang',3);


-- create address DB table
CREATE TABLE Addresses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
street varchar(255) NOT NULL DEFAULT 'na',
city varchar(255) NOT NULL DEFAULT 'na',
stateCountryID INT NOT NULL,
zipcode varchar(255) NOT NULL DEFAULT 'na',
FOREIGN KEY(stateCountryID) REFERENCES StateCountries(id)
);
-- Dumping data For table `Address`
INSERT INTO Addresses(street,city,stateCountryID,zipcode) VALUES
('1060 West Addison Street','Chicago',14,'600613'),
('123 N 1st Street','Omaha',27,'68116'),
('8753 West 3rd Ave.','Dallas',44,'75001'),
('123 Friendly Street','Ottawa',52,'KA 0G9'),
('1 Wall Street	','New York',32,'10005-0012'),
('321 Bronx Street','New York',32,'10004'),
('301 Front St W','Toronto',52,'M5V 2T6'),
('1 Blue Jays Way','Toronto',52,'M5V 1J1'),
('Campos El290','Mexico City', 53,' '),
('Avery Hall','Lincoln',27,'68503'),
('126-01 Roosevelt Ave','Flushing',32,'11368'),
('1 MetLife Stadium Dr', 'East Rutherford', 30,'07073'),
('1 E 161st St', 'Bronx', 32,'10451'),
('700 E Grand Ave', 'Chicago', 14, '60611'),
('333 W 35th St', 'Chicago', 14,'60616'),
('800 West 7th Street', 'Albuquerque', 31, '87105'),
('123 Cabo San Lucas', 'Los Cabos', 54,'11111'),
('259 Concorde Suites', 'Lincoln', 27,'68588-0115'),
('1223 Oldfather Hall', 'Lincoln', 27,'68503'),
('123 Venture Way', 'Culver City', 5,'90230' ),
('9800 Savage Rd', 'Fort Meade', 20,'20755'),
('456 West 7th St.', 'Omaha', 27,'68500'),
('3555 South 140th Plaza', 'Omaha', 27,'68144'),
('755 Willard Drive','Ashwaubenon',50,'54304'),
('6301 University Ave','Cedar Falls',15,'50613');


-- Movie Ticket DB table
CREATE TABLE MovieTickets(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
dateTime datetime NOT NULL,
movieName VARCHAR(255) NOT NULL,
addressID INT NOT NULL,
screenNumber VARCHAR(255) NOT NULL,
pricePerUnit FLOAT NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id),
FOREIGN KEY(addressID) REFERENCES Addresses(id)
);
-- Dumping data For table `MovieTicket`
INSERT INTO MovieTickets(dateTime, movieName, addressID, screenNumber, pricePerUnit, productID) VALUES
('2016-10-21 13:10','A Monster Calls',23,'2A',21.50,3),
('2016-09-13 15:30','The Wild Life',24,'12F',11.00,5),
('2016-11-10 10:00','Shut In',25,'32D',25.00,6);

--
-- Tables structure for Person
--

-- create PersonDB
CREATE TABLE Persons(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personCode varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
firstName varchar(255) NOT NULL,
addressID INT NOT NULL,
foreign key(addressID) references Addresses(id)
);

INSERT INTO Persons(personCode,lastName,firstName,addressID) VALUES
('944c','Castro','Starlin',1),
('306a','Sampson','Brock',2),
('55bb','O''Brien','Miles',3),
('2342','O''Brien','Miles',4),
('aef1','Gekko','Gordon',5),
('321f','Fox','Bud',6),
('ma12','Sveum','Dale',7),
('321nd','Hartnell','William',8),
('nf32a','Troughton','Patrick',9),
('321na','Pertwee','Jon',10),
('231','Baker','Tom',11),
('6doc','Hurndall','Richard',12),
('321dr','Baker','C.',13),
('1svndr','McCoy','Sylvester',14),
('1231st','McGann','Paul',15),
('nwdoc1','Eccleston','Chris',16),
('2ndbestd','Tennant','David',17),
('wrddoc','Smith','Matt',18),
('bbchar','Ehrmantraut','Kaylee',19),
('doc05','Davison','Peter',20);

-- create join table named Emails
create table Emails(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personID INT NOT NULL,
email 	VARCHAR(255) NOT NULL,
FOREIGN KEY(personID) REFERENCES Persons(id)
);

INSERT INTO Emails(personID,email) VALUES
(1,'scastro@cubs.com'),
(1, 'starlin_castro13@gmail.com'),
(2,'brock_f_sampson@gmail.com'),
(2, 'bsampson@venture.com'),
(3,'obrien@ds9.com'),
(3, 'obrien@enterprise.gov'),
(6,'bfox@gmail.com'),
(6, 'csheen@crazy.net'),
(7,'sveum@cubs.com'),
(8, 'whartnell@doctors.com'),
(8,'dr@who.com'),
(9, 'ptroug@cse.unl.edu'),
(9,'ptrou32@unl.edu'),
(10, 'jpet@whofan.com'),
(11,'famousdoc@who.com'),
(11, 'tbaker@cse.unl.edu'),
(11,'mostfamous@whoian.com'),
(11, 'thedoctor@bbc.com'),
(12,'rhurndall@cse.unl.edu'),
(12, 'richard@unl.edu'),
(13,'dr@baker.com'),
(14, 'slyguy@hotmail..com'),
(14, 'mccoy@whofan.com'),
(15, 'pmcgann@mlb.com'),
(15, 'foo@bar.com'),
(15, 'pmc@unl.edu'),
(16, 'newguy@whoian.com'),
(17, 'actor@shakespeare.com'),
(17, 'tdavid@unl.edu'),
(18, 'msmith@who.com'),
(18, 'thedoc@cse.unl.edu');
--
-- Tables structure for Customer
--
CREATE TABLE Customers(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customerCode VARCHAR(255) NOT NULL,
customerName VARCHAR(255) NOT NULL,
customerType VARCHAR(255) NOT NULL DEFAULT 'na',
primaryContactID INT NOT NULL,
addressID INT NOT NULL,
FOREIGN KEY(primaryContactID) REFERENCES Persons(id),
FOREIGN KEY(AddressID) REFERENCES Addresses(id)
);


INSERT INTO Customers(customerCode,customerName,customerType,primaryContactID,addressID) VALUES
('C001','Clark Consultants','G',11, 19),
('C002','CAS International Fellows','S',1, 20),
('C003','Valueless Club','S',2, 21),
('C004','Stony Brook','G', 6, 22),
('C005','Hewlett Industries','G', 19, 1),
('C006','Gregory Smith','S', 13, 23);

--
-- Table structure for Invoices
--
-- create table to store Invoice attribute
CREATE TABLE Invoices(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
invoiceCode VARCHAR(255) NOT NULL,
customerID INT NOT NULL,
salesPersonID INT NOT NULL,
invoiceDate DATE NOT NULL,
FOREIGN KEY(CustomerID) REFERENCES Customers(id),
FOREIGN KEY(salesPersonID) REFERENCES Persons(id)
);

INSERT INTO Invoices(invoiceCode,customerID,salesPersonID,invoiceDate) VALUES
('INV001',1,16,'2016-09-03'),
('INV002',2,17,'2016-11-10'),
('INV003',5,18,'2016-11-26'),
('INV004',3,12,'2016-10-16');


#create table to store product associate to an invoice.
CREATE TABLE InvoiceProducts(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
invoiceID INT NOT NULL ,
productID INT NOT NULL,
unit INT NOT NULL,
FOREIGN KEY(invoiceID) REFERENCES Invoices(id),
FOREIGN KEY(productID) REFERENCES Products(id),
CONSTRAINT uniqueIP UNIQUE(invoiceID, productID)
);

-- Added CONSTRAIN unique key word to prevent duplicate product in the
-- same Invocie
INSERT INTO InvoiceProducts(invoiceID, productID, unit) VALUES
(1,3,2),
(1,7,1),
(1,2,4),
(2,1,23),
(2,5,31),
(2,4,30),
(2,8,17),
(3,6,7),
(3,9,6),
(3,7,2),
(3,12,4),
(4,10,3),
(4,8,3);

--
-- General select Statesments to see if tables are created and stored properly
--
-- select * from Addresses;
-- select * from Persons;
-- select * from Customers;
-- select * from Emails;
-- select * from Products;
-- select * from MovieTickets;
-- select * from SeasonPasses;
-- select * from ParkingPasses;
-- select * from Refreshments;
-- select * from Invoices;
-- select * from InvoiceProducts;
