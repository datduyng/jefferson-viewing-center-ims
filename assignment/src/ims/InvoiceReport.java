/**
 * @Authors Dat Nguyen and Reid Stagemeyer 
 * @Version 3.0
 * 
 * Reads data from flat files and creates appropriate products, 
 * people, customers, and invoices.  Generates a formatted invoice
 * report for all the invoices in the system.
 * 
 */
package ims;

import java.util.Iterator;
import java.util.Map.Entry;
import com.jvc.ext.ProcessDatabase;

import customer.Student;
import product.MovieTicket;
import product.ParkingPass;
import product.Product;
import product.Refreshment;
import product.SeasonPass;
import product.Service;
import product.Ticket;

public class InvoiceReport {
	
	public static void main(String[] args) {
		
		/* uncomment if you want to read data from flat file*/
		// read and process data from file.
//		DataConverter.readPersonFile();
//		DataConverter.readCustomerFile();
//		DataConverter.readProductFile();
//		DataConverter.readInvoiceFile();
	
		/*Uncomment these if you want to test the program locally.*/
		//InvoiceData.deleteDatabaseTable();
		//InvoiceData.executeSqlScript("assignment04/createDatabaseTable.sql");
		//CreateDatabaseTable.createTable();
		
		/*convert data from DB to Object*/
		System.out.println("============================================================================================++");
		ProcessDatabase.toPersonObject();
		System.out.println("============================================================================================++");

		ProcessDatabase.toCustomerObjectFromDB();
		System.out.println("============================================================================================++");

		//add product.
		ProcessDatabase.toMovieTicketObjectFromDB();
		ProcessDatabase.toSeasonPassObjectFromDB();
		ProcessDatabase.toParkingPassObjectFromDB();
		ProcessDatabase.toRefreshmentObjectFromDB();
		System.out.println("============================================================================================++");
		
		// uncomment to add invoice from DB 
		ProcessDatabase.toInvoiceObjectFromDB();
		
		System.out.println("============================================================================================++");
		
		// linked invoice list. 
		MyList<Invoice> invoiceList = new MyList<Invoice>();
		// adding invoice
		// invoice variables for invoice report
		for( Invoice invoice: DataConverter.getInvoices()) {
			invoiceList.addInvoiceNodeInOrder(invoice);
		}
		
		double overallSubTotal = 0.0;
		double overallFee = 0.0;
		double overallTax = 0.0;
		double overallDiscount = 0.0;
		double overallTotal = 0.0;
		
		//heading for invoice report
		StringBuilder subInvoiceSB = new StringBuilder();
		StringBuilder allInvoiceSB = new StringBuilder();
		allInvoiceSB.append("=========================\n");
		allInvoiceSB.append("Executive Report\n");
		allInvoiceSB.append("=========================\n");
		allInvoiceSB.append(String.format("%-10s%-60s%-20s%15s%15s%15s%15s%15s\n", "Invoice","Customer","SalesPerson","Subtotal","Fees","Taxes","Discounts","Total"));
		
		//print summaries for each individual invoice and grand totals for all invoices 
		//iterator 
		Iterator<Invoice> invoiceIterator = invoiceList.iterator();
		
		while(invoiceIterator.hasNext()) {
			Invoice invoice = invoiceIterator.next();
			
			subInvoiceSB.append(String.format("Invoice %s\n", invoice.getInvoiceCode()));
			subInvoiceSB.append("==================================\n");
			subInvoiceSB.append(String.format("SalePerson: %s\n",invoice.getSalesPerson().getName() ));
			subInvoiceSB.append(String.format("Customer Info: \n%s\n",invoice.getCustomer().toInfo()));
			subInvoiceSB.append(String.format("-----------------------------\n"));
			subInvoiceSB.append(String.format("Code\tItem\t\t\t\t\t\t\t\t\t\t\t\t   SubTotal\t     Tax\tTotal\n"));
			
			//cal culate total 
			//double totalSubTotal = 0.0, totalTax = 0.0, totalTotal = 0.0, finalTotal = 0.0;
			for(Entry<Product, Integer> p : invoice.getProductList().entrySet()) {
				
				// get key and value
				Product key =  p.getKey();
				int value = p.getValue();	
				
				//get subtotal for product based on the invoice date and quantity of said product bought 
				double subTotal = key.calculateSubTotal(value, invoice.getInvoiceDate(), invoice.getProductList());
				double tax = 0.0;
				if(key instanceof Ticket) {
					tax = subTotal * Ticket.saleTaxRate;
				}else {
					tax = subTotal * Service.saleTaxRate;
				}
				double total = subTotal + tax;
				
				//downcast and print out specific formatted details for each specific product
				String itemInString = "";
				if(key instanceof MovieTicket) {
					MovieTicket m = ((MovieTicket)key);
					itemInString = m.toInvoiceFormat(subTotal, tax, total);
				}else if(key instanceof SeasonPass) {
					SeasonPass s = ((SeasonPass)key);
					itemInString = s.toInvoiceFormat();
				}else if(key instanceof ParkingPass) {
					ParkingPass pPass = ((ParkingPass)key);
					itemInString = pPass.toInvoiceFormat();
				}else if(key instanceof Refreshment) {
					Refreshment r = ((Refreshment)key);
					itemInString = r.toInvoiceFormat();
				}

				String subTotalInStr = String.format("%3.2f", subTotal);
				String taxInStr = String.format("%3.2f", tax);
				String totalInStr = String.format("%3.2f", total);
				
				//movie ticket has a special formatted string
				if((key instanceof MovieTicket)){
					subInvoiceSB.append(String.format("%s\t%s\n",key.getProductCode(),itemInString));
				}else {
					subInvoiceSB.append(String.format("%s\t%-96s$%10s  $%10s  $%10s\n",key.getProductCode(),itemInString, subTotalInStr,taxInStr,totalInStr));
				}
				
			}	// end p forloop
			
			
			//print formatted strings for the totals of the invoice
			subInvoiceSB.append(String.format("\t\t\t\t\t\t\t\t\t\t\t\t\t==========================================\n"));
			String totalSubTotalInStr = String.format("%3.2f", invoice.getTotalSubTotal());
			String totalTaxInStr = String.format("%3.2f", invoice.getTotalTax());
			String totalTotalInStr = String.format("%3.2f", invoice.getTotalTotal());
			subInvoiceSB.append(String.format("%-104s$%10s  $%10s  $%10s\n","SUBTOTAL",totalSubTotalInStr, totalTaxInStr,totalTotalInStr));
			
			
			//apply applicable discounts and fees and calculate final totals
			String studentDiscountInStr = "0.0";
			String totalFeeInStr = "0.0";
			String finalTotalInStr = "";
//			double discount = 0.0;
			if(invoice.getCustomer() instanceof Student) {
				
//				discount = (totalSubTotal * Student.discountRate) + totalTax;
//				finalTotal = totalTotal - discount + Student.additionalFee;
				studentDiscountInStr = String.format("%3.2f", (invoice.getTotalDiscount())*-1.0);
				totalFeeInStr = String.format("%s", Student.additionalFeeToString());
				subInvoiceSB.append(String.format("%-127s   $%10s  \n","DISCOUNT ( 8% STUDENT & NO TAX)", studentDiscountInStr));
				subInvoiceSB.append(String.format("%-127s   $%10s  \n","ADDITIONAL FEE (Student)", Student.additionalFeeToString()));
				
				//calcualte overall fee
				overallFee += Student.additionalFee;
			}
			
			finalTotalInStr = String.format("%3.2f", invoice.getFinalTotal());
			subInvoiceSB.append(String.format("%-127s   $%10s  \n","TOTAL: ", finalTotalInStr));
			
			subInvoiceSB.append(String.format("\n\t\t\t\t\tThank you\n\n"));
			allInvoiceSB.append(String.format("%-10s%-30s-%-30s%-20s $%13s $%13s $%13s $%13s $%13s\n", invoice.getInvoiceCode(),
					invoice.getCustomer().getCustomerName(),invoice.getCustomer().getCustomerType(),invoice.getSalesPerson().getName()
					,totalSubTotalInStr,totalFeeInStr,totalTaxInStr,studentDiscountInStr,finalTotalInStr));
			
			
			//calculate overall total
			overallSubTotal += invoice.getTotalSubTotal();
			overallTax += invoice.getTotalTax();
			overallDiscount += invoice.getTotalDiscount();
			overallTotal += invoice.getFinalTotal();
		}// end invoice for loop
		
		allInvoiceSB.append("===========================================================================================================================================================\n");
		allInvoiceSB.append(String.format("%-90s  $%13s $%13s $%13s $%13s $%13s\n","TOTALS", floatFormatedToString(overallSubTotal),
				floatFormatedToString(overallFee),floatFormatedToString(overallTax),
				floatFormatedToString(overallDiscount*-1.0),floatFormatedToString(overallTotal)));
		System.out.println(allInvoiceSB);
		System.out.println(subInvoiceSB);
	}// end main()
	
	/**
	 * 
	 * @param value double to be formatted
	 * @return formatted String
	 */
	public static String floatFormatedToString(double value) {
		return String.format("%3.2f", value);
	}
}// end InvoiceReport class



