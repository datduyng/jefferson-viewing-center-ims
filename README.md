# csce156-assignment

- if you have trouble connect git with Eclipse
	- check this out. https://stackoverflow.com/questions/18813847/cannot-open-git-upload-pack-error-in-eclipse-when-cloning-or-pushing-git-repos?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

## Assignment 02
####  Goal
   -Design data parsers for reading and importing flat data files. (review Lab 4/Lab 5 if necessary)
   -Design classes for modeling the OOP based system and handling various data objects.
   -Export various data objects to standard XML/JSON (or both) format.

#### JSON version
   - A JSON Validator: http://jsonformatter.curiousconcept.com/
   - gson(google json)
      -http://code.google.com/p/google-gson/
      - https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
   - objectMapper (JSON)
      - https://www.tutorialspoint.com/jackson/jackson_objectmapper.htm
      - https://www.mkyong.com/java/jackson-tree-model-example/

	 - using Array list with JSON example
		 - https://java2blog.com/gson-example-read-and-write-json/
	 - pretty print JSON file example with gson library/use List example
		 - https://medium.com/@ssaurel/parse-and-write-json-data-in-java-with-gson-dd8d1285b28


### XML version
   - convert java object to xml
      - http://x-stream.github.io/tutorial.html


## Assignment 03
   - phase 02;
   - started.
   - Goal:
      - output 2 report one the `standard output`
         - summary report
	 - invoice report of each invoice.

## Assignment 04
- IMS model project overview:

![imsobjmodel](https://user-images.githubusercontent.com/35666615/41205687-8c29951e-6cbd-11e8-9ca8-b58d7b25827b.png)

### TODO
- Ask if they are prorating the parking pass.


## Assignment 04
- IMS Model overview :


![Alt text](/../datNguyen/assignment/model/imsObjModel.png?raw=true "test")

- Goal:
	- Design database tables for storing the data of the system. Define appropriate column names,
column sizes, primary and foreign keys to model various types of relationships.
	-	Design a SQL script for creation of the database tables and inserting sample data into the tables.
(revisit Lab 8 if required)
	- Design a SQL script containing queries based

- Turn in:
	- You will hand in an SQL script (name it `invoiceDB.sql`) that contains all of the necessary SQL queries
to create your database
	- in a separate file (name it `invoiceQueries.sql`), queries to perform the following
operations.

- PreparedStatement with Date Obj:
	- https://stackoverflow.com/questions/18614836/using-setdate-in-preparedstatement
