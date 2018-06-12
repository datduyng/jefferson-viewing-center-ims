use rstagemeyer;

#1. A query to retrieve the major fields for every person.

#2. A query to add an email to a specific person.

#3. A query to change the address of a theater in a movie ticket record.

#4. A query (or series of queries) to remove a given movie ticket record.
DELETE FROM MovieTicket WHERE productID = 3;
DELETE FROM Product WHERE id = 3;

#5. A query to get all the products in a particular invoice.

#6. A query to get all the invoices of a particular customer.

#7. A query that “adds” a particular product to a particular invoice.

#8. A query to find the total of all per-unit costs of all movie-tickets.

#9. A query to find the total number of movie-tickets sold on a particular date.
SELECT invoiceDate, sum(unit) TotalNumTickets from InvoiceProducts join Invoices on InvoiceProducts.invoiceID = Invoices.id 
join Products on InvoiceProducts.productId = Products.id where Invoices.invoiceDate = '2016-09-03' and Products.productType = 'M';
#10. A query to find the total number of invoices for every salesperson.
select Persons.id, Persons.personCode, Persons.firstName, Persons.lastName, count(Invoices.id) 
from Persons join Invoices on Persons.id = Invoices.salesPersonID group by Persons.id;
#11. A query to find the total number of invoices for a particular movie ticket.
SELECT p.id, productCode, productType, count(invoiceID) NumInvoices FROM InvoiceProducts ip 
JOIN Products p ON ip.productID = p.id WHERE productCode = 'fp12'; 
#12. A query to find the total revenue generated (excluding fees and taxes) on a particular date from
#all movie-tickets (hint: you can take an aggregate of a mathematical expression).
SELECT invoiceDate, productType, sum(pricePerUnit * unit) TotalRevenue 
FROM InvoiceProducts ip JOIN Invoices i ON ip.invoiceID = i.id 
JOIN Products p ON ip.productID = p.id 
JOIN MovieTickets mt ON p.id = mt.productID 
WHERE i.invoiceDate = '2016-11-26'; 
#13. A query to find the total quantities sold (excluding fees and taxes) of each category of services
#(parking-passes and refreshments) in all the existing invoices.
SELECT productType, SUM(unit) TotalQuantitiesSold FROM InvoiceProducts ip JOIN Products p ON ip.productID = p.id
WHERE p.productType = 'P' OR p.productType = 'R' GROUP BY productType;
#14. A query to detect invalid data in invoices as follows. In a single invoice, a particular ticket should
#only appear once (since any number of units can be consolidated to a single record). Write a
#query to find any invoice that includes multiple instances of the same ticket.
SELECT invoiceCode, productID, productCode, productType, count(productID) NumberOfInstances FROM InvoiceProducts ip 
JOIN Products p ON p.id = ip.productID 
JOIN Invoices i ON i.id = ip.invoiceID 
WHERE productType = 'M' OR productType = 'S' GROUP BY productID;
#15. Write a query to detect a possible conflict of interest as follows. No distinction is made in this
#system between a person who is the primary contact of a customer and a person who is also a
#sales person. Write a query to find any and all invoices where the salesperson is the same as the
#primary contact of the invoice’s customer. 
SELECT i.id, i.salesPersonID FROM Invoices i JOIN Customers c ON i.customerID = c.id 
WHERE i.salesPersonID = c.salesPersonID;