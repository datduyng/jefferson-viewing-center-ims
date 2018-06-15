-- 1.A query to retrieve the major fields for every person.
SELECT * FROM Emails JOIN Persons ON Emails.personID=Persons.id;

-- 2.A query to add an email to a specific person.
INSERT INTO Emails(personID,email) VALUE(1,'944-castroc2@gmail.com');

---------------------------------------
-- 3.A query to change the address of a theater in a movie ticket record.
UPDATE `Addresses` a FROM `Addresses` JOIN `MovieTickets` m ON (m.addressID=a.id)
SET `street`='1230 King street',`city`='Philadelphia',`stateCountryID`=15,`zipcode='68573'
WHERE m.productID=3;
---------------------------___Error



-- 4.A query (or series of queries) to remove a given movie ticket record.
DELETE FROM MovieTickets WHERE productID=3;
DELETE FROM InvoiceProducts WHERE productID=3;


-- 5.A query SELECT p.id, productCode, productType, count(invoiceID) NumInvoices FROM InvoiceProducts ip
JOIN Products p ON ip.productID = p.id WHERE productCode = 'fp12';
SELECT p.id, productCode, productType, count(invoiceID) NumInvoices FROM InvoiceProducts ip 
JOIN Products p ON ip.productID = p.id WHERE productCode = 'fp12';
to get all the products in a particular invoice.
SELECT productCode,productType,invoiceCode FROM Invoices i JOIN InvoiceProducts ip
ON ip.invoiceID=i.id JOIN Products p ON p.id=ip.productID WHERE ip.invoiceID=2;

-- 6.A query to get all the invoices of a particular customer.
SELECT customerCode, customerName, invoiceCode FROM Customers c
JOIN Invoices i ON i.customerID=c.id WHERE c.id=2;

-- 7.A query that “adds” a particular product to a particular invoice.

INSERT INTO InvoiceProducts(invoiceID, productID, unit) VALUES(4,12,110);

-- 8.A query to find the total of all per-unit costs of all movie-tickets.
SELECT SUM(pricePerUnit) total FROM MovieTickets;

-- 9.A query to find the total number of movie-tickets sold on a particular date.
SELECT invoiceDate,SUM(unit) FROM Invoices i JOIN InvoiceProducts ip on i.id=ip.invoiceID
JOIN Products p ON p.id=ip.productID WHERE i.invoiceDate='2016-11-10' AND p.productType='M';

--10.A query to find the total number of invoices for every salesperson.
SELECT COUNT(i.id) countOfInvoice,personCode
FROM Invoices i JOIN Persons p ON i.salesPersonID=p.id
GROUP BY p.id;


-- 11.A query to find the total number of invoices for a particular movie ticket.
SELECT p.id, productCode, productType, count(invoiceID) NumInvoices FROM InvoiceProducts ip
JOIN Products p ON ip.productID = p.id WHERE productCode = 'fp12';

-- 12.A query to find the total revenue generated (excluding fees and taxes) on a particular date from
-- all movie-tickets (hint: you can take an aggregate of a mathematical expression).
-- Note: This query does not work on Mysql server
-- This query work on MariaDb server
SELECT invoiceDate, productType, sum(pricePerUnit * unit) TotalRevenue
FROM InvoiceProducts ip JOIN Invoices i ON ip.invoiceID = i.id
JOIN Products p ON ip.productID = p.id
JOIN MovieTickets mt ON p.id = mt.productID
WHERE i.invoiceDate = '2016-11-26';

-- 13. A query to find the total quantities sold (excluding fees and taxes) of each category of services
-- (parking-passes and refreshments) in all the existing invoices.
SELECT productType, SUM(unit) TotalQuantitiesSold FROM InvoiceProducts ip JOIN Products p ON ip.productID = p.id
WHERE p.productType = 'P' OR p.productType = 'R' GROUP BY productType;


-- 14. A query to detect invalid data in invoices as follows. In a single invoice, a particular ticket should
-- only appear once (since any number of units can be consolidated to a single record). Write a
-- query to find any invoice that includes multiple instances of the same ticket.
--
-- Note: This query cannot be run on mySql server. this can only run on mariaDB server
-- Yield error if atempt
--  Error: this is incompatible with sql_mode=only_full_group_by
SELECT invoiceCode, productID, productCode, productType, count(productID) NumberOfInstances FROM InvoiceProducts ip
JOIN Products p ON p.id = ip.productID
JOIN Invoices i ON i.id = ip.invoiceID
WHERE productType = 'M' OR productType = 'S' GROUP BY productID;

-- 15. Write a query to detect a possible conflict of interest as follows. No distinction is made in this
-- system between a person who is the primary contact of a customer and a person who is also a
-- sales person. Write a query to find any and all invoices where the salesperson is the same as the
-- primary contact of the invoice’s customer.
SELECT i.id, i.salesPersonID FROM Invoices i JOIN Customers c ON i.customerID = c.id
WHERE i.salesPersonID = c.primaryContactID;
