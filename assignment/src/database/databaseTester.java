package database;

import ims.Address;

public class databaseTester {
	public static void main(String args[]) {
		//InvoiceData.addParkingPass("data2",12.3);
		//InvoiceData.addRefreshment("dataR1", "new year1", 45.12);
		//InvoiceData.removeAllInvoices();
		//InvoiceData.addInvoice("INV010", "C006", "944c", "2012-02-03");
		//InvoiceData.addMovieTicketToInvoice("INV001", "782g", 14);
		//InvoiceData.addMovieTicketToInvoice("INV001", "xer4", 100);
		//InvoiceData.addMovieTicketToInvoice("INV001", "yp23", 1000);
		//InvoiceData.addParkingPassToInvoice("INV001", "90fb", 12000, "abcs");
		//InvoiceData.executeSqlScript("assignment04/deleteImsTable.sql");
		//InvoiceData.deleteDatabaseTable();
	
		InvoiceData.deleteDatabaseTable();
		InvoiceData.executeSqlScript("assignment04/createDatabaseTable.sql");
		CreateDatabaseTable.createTable();
		ProcessDatabase.toPersonObject();
		System.out.println("=============================================================================================================");
		ProcessDatabase.toCustomerObjectFromDB();
		
	}
	}
	
