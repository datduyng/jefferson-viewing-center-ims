package ims;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		
		// read and process data from file.
		DataConverter.readPersonFile();
		DataConverter.readCustomerFile();
		DataConverter.readProductFile();
		DataConverter.readInvoiceFile();
		
		// TODO: print 2 report. 
		System.out.println(DataConverter.getInvoices());
		StringBuilder sb = null;
		for( Invoice invoice: DataConverter.getInvoices()) {
			
			sb = new StringBuilder();
			System.out.printf("Invoice %s\n",invoice.getInvoiceCode());
			System.out.println("==================================\n");
			sb.append(String.format("SalePerson: %s\n",invoice.getSalesPerson().getName() ));
			sb.append(String.format("Customer Info: \n%s\n",invoice.getCustomer().toInfo()));
			sb.append(String.format("-----------------------------\n"));
			sb.append(String.format("Code\tItem\t\t\t\t\t\tSubTotal\tTax\tTotal\n"));
			
			double totalSubTotal = 0.0, totalTax = 0.0, totalTotal = 0.0, finalTotal = 0.0;
			for(Entry<Product, Integer> p : invoice.getProductList().entrySet()) {
				
				// get key and valu
				Product key =  p.getKey();
				int value = p.getValue();	
				

				double subTotal = key.calculateSubTotal(value, invoice.getInvoiceDate(), invoice.getProductList());
				double tax = 0.0;
				if(key instanceof Ticket) {
					tax = subTotal * Ticket.saleTaxRate;
				}else {
					tax = subTotal * Service.saleTaxRate;
				}
				double total = subTotal + tax;
				
				totalSubTotal += subTotal;
				totalTax += tax;
				totalTotal += total;
				
				String itemInString = "";
				if(key.getProductType().equals("M")) {
					MovieTicket m = ((MovieTicket)key);
					itemInString = String.format("MovieTicket '%s' \n@%s \n%s\n", m.getMovieName()
							,m.getTheaterAddress().toString(),m.getDateTime());
				}else if(key.getProductType().equals("S")) {
					SeasonPass s = ((SeasonPass)key);
					itemInString = String.format("SeasonPass");
				}else if(key.getProductType().equals("P")) {
					ParkingPass pPass = ((ParkingPass)key);
					itemInString = String.format("ParkingPass");
				}else if(key.getProductType().equals("R")) {
					Refreshment r = ((Refreshment)key);
					itemInString = String.format("Refreshment");
				}
				sb.append(String.format("%s\t%s\t\t\t\t\t\t%.2f\t%.2f\t%.2f\n",
					key.getProductCode(),itemInString,subTotal,tax,total));
			}	// end p forloop
			sb.append(String.format("SUB-TOTALS:\t\t\t\t\t\t\t%.2f\t%.2f\t%.2f\n", totalSubTotal, totalTax,totalTotal));
			
			if(invoice.getCustomer() instanceof Student) {
				
				double studentDiscount = (totalSubTotal * Student.discountRate) + totalTax;
				finalTotal = totalTotal - studentDiscount + Student.additionalFee;
				sb.append(String.format("DISCOUNT ( 8%% STUDENT & NO TAX)\t\t\t\t\t\t\t%.2f\n", (studentDiscount)*-1.0));
				sb.append(String.format("ADDITIONAL FEE (Student)\t\t\t\t\t\t\t%.2f\n", Student.additionalFee));
			}else {
				finalTotal = totalTotal ;
			}		
			sb.append(String.format("TOTAL:\t\t\t\t\t\t\t\t\t\t%.2f\n",finalTotal));
			System.out.println(sb);
		}// end invoice for loop
		System.out.println("==========Main()===============================\n");

	}// end main()
}// end InvoiceReport class

