##use rstagemeyer;

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
('z1e4','S'),
('fa45','M'),
('90','P'),
('ff23','R'),
('b29e','S'),
('122e','S'),
('f12','R');

#Refreshment DB table
create table Refreshments(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL,
cost float NOT NULL default '0.0',
haveTicket boolean NOT NULL default 0,# TODO: 
productID INT,
FOREIGN KEY(productID) REFERENCES Products(id)
);

-- Dumping data For table `Refreshment`
INSERT INTO Refreshments(name, cost, haveTicket, productID) VALUES 
('Zipline Beer-1 oz',3.01,0,4),
('good beer-1 oz',0.0,0,7);

# Parkingpass DB table.
create table ParkingPasses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fee float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);

-- Dumping data For table `ParkingPass`
INSERT INTO ParkingPasses(fee, productID) VALUES 
(10.00,3);

#Seasonpass DB table.
create table SeasonPasses(
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
('Easter Pass','2018-03-13','2018-04-01',50.00,1),
('Winter Special','2016-12-13','2017-01-07',120.00,5),
('summer special','2016-12-13','2017-01-07',-5.00,6);


# create address DB table
CREATE TABLE Addresses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
street varchar(255) NOT NULL DEFAULT 'na',
city varchar(255) NOT NULL DEFAULT 'na',
state varchar(255) NOT NULL DEFAULT 'na',
zipcode varchar(255) NOT NULL DEFAULT 'na',
country varchar(255) NOT NULL DEFAULT 'na'
);

-- Dumping data For table `Address`
INSERT INTO Addresses(street,city,state,zipcode,country) VALUES 
('1065 West Madison Ave','Toronto','OT','60613','Canada'),
('666 N 99th Street','Oklahoma City','OK','68116','USA'),
('8754 Turnpike Lane.','Dallas','WY','75031','USA'),
('123 Bottoms St','Lincoln','NE','68588-0115','USA'),
('1223 Discreet Location','Somewhere','CA','62401','USA'),
('The Alley Cove','New York City','NY','90210','USA'),
('2234 Dudley St','Lincoln','NE','68503','USA');

#Movie Ticket DB table
create table MovieTickets(
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
('2018-5-22 12:10','Space Jam',7,'2A',12.50,2);

--
-- Tables structure for Person
--

# create PersonDB 
create table Persons(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personCode varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
firstName varchar(255) NOT NULL,
addressID INT NOT NULL,
foreign key(addressID) references Addresses(id)
);

INSERT INTO Persons(personCode,lastName,firstName,addressID) VALUES 
('01a01','Madison','Billy',1),
('zzz3','Lebowski','Big',2),
('wtf72','Miles','Les',3),
('zzz4','Lebowski','Big',2),
('zz23','as','Big',2);


# create join table named Emails
create table Emails(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personID INT NOT NULL,
email 	VARCHAR(255) NOT NULL,
FOREIGN KEY(personID) REFERENCES Persons(id)
);

INSERT INTO Emails(personID,email) VALUES 
(2,'big_lebo@aol.com'),
(2,'bski@ventura.com'),
(3,'lester@d.coom'),
(4,'big_lebo@aol.com'),
(4,'bski@ventura.com'),
(5,'bug@gmail.com');

--
-- Tables structure for Customer
--
CREATE TABLE Customers(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customerCode VARCHAR(255) NOT NULL,
customerName VARCHAR(255) NOT NULL,
customerType VARCHAR(255) NOT NULL DEFAULT 'na', 
salesPersonID INT NOT NULL,
addressID INT NOT NULL,
FOREIGN KEY(salesPersonID) REFERENCES Persons(id),
FOREIGN KEY(AddressID) REFERENCES Addresses(id)
);

INSERT INTO Customers(customerCode,customerName,customerType,salesPersonID,addressID) VALUES 
('C001','Bill''s Volume Sales','G',1,4), 
('C002','The Knights Templar','S',3,5), 
('C003','Dirty Mike and the Boyz','S',5,6);

--
-- Table structure for Invoices 
--
# create table to store Invoice attribute
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
('In1',3,1,'2018-03-10'),
('In2',2,3,'2018-05-22'),
('In3',1,4,'2016-12-24'),
('In6',3,5,'2018-05-25'),
('In7 ',3,1,'2018-03-05');

#create table to store product associate to an invoice.
CREATE TABLE InvoiceProducts(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
invoiceID INT NOT NULL,
productID INT NOT NULL,
unit INT NOT NULL,
FOREIGN KEY(invoiceID) REFERENCES Invoices(id),
FOREIGN KEY(productID) REFERENCES Products(id)
);

INSERT INTO InvoiceProducts(invoiceID, productID, unit) VALUES
(1,1,4),
(1,3,2),
(1,7,100),
(2,2,3),
(2,3,5),
(2,4,2),
(3,5,2),
(4,6,1),
(4,4,1),
(5,3,1),
(5,1,1); 

# General select statements to see if tables are created and stored properly

##select * from Addresses;
##select * from Persons;
##select * from Customers;
##select * from Emails;

##select * from Products;
##select * from MovieTickets;
##select * from SeasonPasses;
##select * from ParkingPasses;
##select * from Refreshments;

##select * from Invoices;
##select * from InvoiceProducts; 
