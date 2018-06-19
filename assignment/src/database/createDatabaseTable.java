package database;

public class createDatabaseTable {
	public static void createTable() {
		
		// delete pre-exist data in database table 
		InvoiceData.deleteDatabaseTable();
		
		// then start adding.
		/*Add Product into product DB Table*/
		
		//public static void addRefreshment(String productCode, String name, double cost) {
		InvoiceData.addRefreshment("ff23", "Labatt Beer-20oz", 4.99);
		InvoiceData.addRefreshment("32f4", "Camramel Popcorn ", 5.5);
		InvoiceData.addRefreshment("yp23", "Double cheese Burger", 9.0);
		
		InvoiceData.addSeasonPass("b29e", "Winter Special", "2016-12-13", "2017-01-07", 12.0);
		InvoiceData.addSeasonPass("3y92","Black Friday Deal", "2012-11-24", "2016-11-29", 58.00);
		InvoiceData.addSeasonPass("xer4", "FiveWeek-Unlimited", "2016-10-07", "2012-11-29", 185.0);
		
		//InvoiceData.addMovieTicket(productCode, dateTime, movieName, street, city, state, zip, country, screenNo, pricePerUnit);
		InvoiceData.addMovieTicket("fp12", "2016-10-21 13:10", "A Monster Calls", "3555 South 140th Plaza", "Omaha", "NE", "68144", "USA", "2A",21.5);
		InvoiceData.addMovieTicket("1239", "2016-09-13 15:30", "The Wild Life", "755 Willard Drive", "Ashwaubenon", "WI", "54304", "USA", "12F",11.0 );
		InvoiceData.addMovieTicket("782g", "2016-11-10 10:00", "Shut In", "6301 University Ave", "Cedar Falls", "IA", "50613", "USA", "32D",25.0 );
		
		//InvoiceData.addParkingPass(productCode, parkingFee);
		InvoiceData.addParkingPass("90fa", 25.0);
		InvoiceData.addParkingPass("3289", 55.0);
		InvoiceData.addParkingPass("90fb", 20.0);
		
		/* Added Person*/
		//InvoiceData.addPerson(personCode, firstName, lastName, street, city, state, zip, country);
		InvoiceData.addPerson("944c","Castro","Starlin","1060 West Addison Street","Chicago","IL","600613","USA");
		InvoiceData.addPerson("306a","Sampson","Brock","123 N 1st Street","Omaha","NE","68116","USA");
		InvoiceData.addPerson("55bb","O'Brien","Miles","8753 West 3rd Ave.","Dallas","TX","75001","USA");
		InvoiceData.addPerson("2342","O'Brien","Miles","123 Friendly Street","Ottawa","ON","KA 0G9","Canada");
		InvoiceData.addPerson("aef1","Gekko","Gordon", "1 Wall Street","New York","NY","10005-0012","USA");
		InvoiceData.addPerson("321f","Fox","Bud", "321 Bronx Street","New York","NY","10004","USA");
		InvoiceData.addPerson("ma12","Sveum","Dale","301 Front St W","Toronto","ON","M5V 2T6","Canada");
		InvoiceData.addPerson("321nd","Hartnell","William", "1 Blue Jays Way","Toronto","ON","M5V 1J1","Canada");
		InvoiceData.addPerson("nf32a","Troughton","Patrick","Campos El290","Mexico City", "FD"," ","Mexico");
		InvoiceData.addPerson("321na","Pertwee","Jon", "Avery Hall","Lincoln","NE","68503","USA");
		InvoiceData.addPerson("231","Baker","Tom","126-01 Roosevelt Ave","Flushing","NY","11368","USA");
		InvoiceData.addPerson("6doc","Hurndall","Richard","1 MetLife Stadium Dr", "East Rutherford", "NJ","07073","USA");
		InvoiceData.addPerson("321dr","Baker","C.", "1 E 161st St", "Bronx", "NY","10451","USA");
		InvoiceData.addPerson("1svndr","McCoy","Sylvester", "700 E Grand Ave", "Chicago", "IL", "60611","USA");
		InvoiceData.addPerson("1231st","McGann","Paul","333 W 35th St", "Chicago", "IL","60616","USA");
		InvoiceData.addPerson("nwdoc1","Eccleston","Chris","800 West 7th Street", "Albuquerque", "NM", "87105","USA");
		InvoiceData.addPerson("2ndbestd","Tennant","David","123 Cabo San Lucas", "Los Cabos", "BCS","11111","USA");
		InvoiceData.addPerson("wrddoc","Smith","Matt","259 Concorde Suites", "Lincoln", "NE","68588-0115","USA");
		InvoiceData.addPerson("bbchar","Ehrmantraut","Kaylee", "1223 Oldfather Hall", "Lincoln", "NE","68503","USA");
		InvoiceData.addPerson("doc05","Davison","Peter", "123 Venture Way", "Culver City", "CA","90230","USA");
		
		/** Add email*/
		InvoiceData.addEmail("944c", "scastro@cubs.com");
		InvoiceData.addEmail("944c", "starlin_castro13@gmail.com");
		InvoiceData.addEmail("306a", "brock_f_sampson@gmail.com");
		InvoiceData.addEmail("306a", "bsampson@venture.com");
		InvoiceData.addEmail("55bb", "obrien@ds9.com");
		InvoiceData.addEmail("55bb", "obrien@enterprise.gov");
		InvoiceData.addEmail("321f", "bfox@gmail.com");
		InvoiceData.addEmail("321f", "csheen@crazy.net");
		InvoiceData.addEmail("ma12", "sveum@cubs.com");
		InvoiceData.addEmail("321nd", "whartnell@doctors.com");
		InvoiceData.addEmail("321nd", "dr@who.com");
		InvoiceData.addEmail("nf32a", "ptroug@cse.unl.edu");
		InvoiceData.addEmail("nf32a", "ptrou32@unl.edu");
		InvoiceData.addEmail("321na", "jpet@whofan.com");
		InvoiceData.addEmail("231", "famousdoc@who.com");
		InvoiceData.addEmail("231", "tbaker@cse.unl.edu");
		InvoiceData.addEmail("231", "mostfamous@whoian.com");
		InvoiceData.addEmail("231", "thedoctor@bbc.com");
		InvoiceData.addEmail("6doc", "rhurndall@cse.unl.edu");
		InvoiceData.addEmail("6doc", "richard@unl.edu");
		InvoiceData.addEmail("321dr", "dr@baker.com");
		InvoiceData.addEmail("1svndr", "slyguy@hotmail..com");
		InvoiceData.addEmail("1svndr", "mccoy@whofan.com");
		InvoiceData.addEmail("1231st", "pmcgann@mlb.com");
		InvoiceData.addEmail("1231st", "foo@bar.com");
		InvoiceData.addEmail("1231st", "pmc@unl.edu");
		InvoiceData.addEmail("nwdoc1", "newguy@whoian.com");
		InvoiceData.addEmail("2ndbestd", "actor@shakespeare.com");
		InvoiceData.addEmail("2ndbestd", "tdavid@unl.edu");
		InvoiceData.addEmail("wrddoc", "msmith@who.com");
		InvoiceData.addEmail("wrddoc", "thedoc@cse.unl.edu");
		
		
		//add Customers to database
		//C001
		InvoiceData.addCustomer("C001", "G", "231", "Clark Consultants", "259 Concorde Suites", "Lincoln", "NE", "68588-0115", "USA");
		//C002
		InvoiceData.addCustomer("C002", "S", "944c", "International Fellows", "1223 Oldfather Hall", "Lincoln", "NE", "68503", "USA");
		//C003
		InvoiceData.addCustomer("C003", "S", "306a", "Valueless Club", "123 Venture Way", "Culver City", "CA", "90230", "USA");
		//C004
		InvoiceData.addCustomer("C004", "G", "321f", "Stony Brook", "9800 Savage Rd", "Fort Meade", "MD", "20755", "USA");
		//C005
		InvoiceData.addCustomer("C005", "G", "bbchar", "Hewlett Industries", "1060 West Addison", "Chicago", "IL", "60601", "USA");
		//C006
		InvoiceData.addCustomer("C006", "S", "321dr", "Gregory Smith", "456 West 7th St.", "Omaha", "NE", "68500", "USA");
		
		//add Invoices to database
		//INV001
		InvoiceData.addInvoice("INV001", "C001", "nwdoc1", "2016-09-03");
		//INV002
		InvoiceData.addInvoice("INV002", "C002", "2ndbestd", "2016-11-10");
		//INV003
		InvoiceData.addInvoice("INV003", "C005", "wrddoc", "2016-11-26");
		//INV004
		InvoiceData.addInvoice("INV004", "C003", "6doc", "2016-10-16");
		
		//add InvoiceProducts to database
		//INV001
		InvoiceData.addInvoiceProduct("INV001", "fp12", 2);
		InvoiceData.addInvoiceProduct("INV001", "3289", 1, "fp12");
		InvoiceData.addInvoiceProduct("INV001", "ff23", 4);
		//INV002
		InvoiceData.addInvoiceProduct("INV002", "b29e", 23);
		InvoiceData.addInvoiceProduct("INV002", "1239", 31);
		InvoiceData.addInvoiceProduct("INV002", "90fa", 30, "b29e");
		InvoiceData.addInvoiceProduct("INV002", "32f4", 17);
		//INV003
		InvoiceData.addInvoiceProduct("INV003", "782g", 7);
		InvoiceData.addInvoiceProduct("INV003", "3y92", 6);
		InvoiceData.addInvoiceProduct("INV003", "3289", 2, "782g");
		InvoiceData.addInvoiceProduct("INV003", "yp23", 4);
		//INV004
		InvoiceData.addInvoiceProduct("INV004", "90fb", 3);
		InvoiceData.addInvoiceProduct("INV004", "32f4", 3);
		
		
	}
}
