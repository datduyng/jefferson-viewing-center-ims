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



CREATE TABLE Products(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
productCode VARCHAR(255) UNIQUE NOT NULL,
productType VARCHAR(2) NOT NULL
);


CREATE TABLE Refreshments(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL,
cost float NOT NULL default '0.0',
haveTicket boolean NOT NULL default 0,
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);

CREATE TABLE ParkingPasses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fee float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);


CREATE TABLE SeasonPasses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL DEFAULT 'na',
startDate DATE NOT NULL,
endDate DATE NOT NULL,
cost float NOT NULL DEFAULT '0.0',
productID INT NOT NULL,
FOREIGN KEY(productID) REFERENCES Products(id)
);

-- create Countries, States databases table
CREATE TABLE Countries(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL DEFAULT 'na'
);

-- dumping data for Countries
INSERT INTO Countries(name)
VALUES ('USA'),('Canada'),('Mexico');

CREATE TABLE StateCountries(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(5) NOT NULL DEFAULT 'na',
fullName VARCHAR(255) NOT NULL DEFAULT 'na',
countryID INT NOT NULL,
FOREIGN KEY(countryID) REFERENCES Countries(id)
);
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

CREATE TABLE Addresses(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
street varchar(255) NOT NULL DEFAULT 'na',
city varchar(255) NOT NULL DEFAULT 'na',
stateCountryID INT NOT NULL,
zipcode varchar(255) NOT NULL DEFAULT 'na',
FOREIGN KEY(stateCountryID) REFERENCES StateCountries(id)
);


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

CREATE TABLE Persons(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personCode varchar(255) UNIQUE NOT NULL,
lastName varchar(255) NOT NULL,
firstName varchar(255) NOT NULL,
addressID INT NOT NULL,
foreign key(addressID) references Addresses(id)
);


create table Emails(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
personID INT NOT NULL,
email 	VARCHAR(255) NOT NULL,
FOREIGN KEY(personID) REFERENCES Persons(id)
);

CREATE TABLE Customers(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customerCode VARCHAR(255) UNIQUE NOT NULL,
customerName VARCHAR(255) NOT NULL,
customerType VARCHAR(255) NOT NULL DEFAULT 'na',
primaryContactID INT NOT NULL,
addressID INT NOT NULL,
FOREIGN KEY(primaryContactID) REFERENCES Persons(id),
FOREIGN KEY(AddressID) REFERENCES Addresses(id)
);



CREATE TABLE Invoices(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
invoiceCode VARCHAR(255) UNIQUE NOT NULL,
customerID INT NOT NULL DEFAULT 0,
salesPersonID INT NOT NULL DEFAULT 0,
invoiceDate DATE NOT NULL,
FOREIGN KEY(CustomerID) REFERENCES Customers(id),
FOREIGN KEY(salesPersonID) REFERENCES Persons(id)
);

CREATE TABLE InvoiceProducts(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
invoiceID INT NOT NULL ,
productID INT NOT NULL,
unit INT NOT NULL,
note VARCHAR(255),
FOREIGN KEY(invoiceID) REFERENCES Invoices(id),
FOREIGN KEY(productID) REFERENCES Products(id),
CONSTRAINT uniqueIP UNIQUE(invoiceID, productID)
);



