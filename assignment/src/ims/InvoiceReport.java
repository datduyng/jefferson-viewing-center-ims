package ims;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
			
			
			Map<Product,Double> subTotalList = new LinkedHashMap<Product,Double>();
			subTotalList = invoice.getSubTotalList();
			for(Entry<Product, Integer> p : invoice.getProductList().entrySet()) {
				// get key and valu
				Product key =  p.getKey();
				int value = p.getValue();	
				

				double subTotal = (double)subTotalList.get(key);
				double tax = 0.0;
				if(key instanceof Ticket) {
					tax = subTotal * Ticket.saleTaxRate;
				}else {
					tax = subTotal * Service.saleTaxRate;
				}
				double total = subTotal + tax;
				
				
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
			System.out.println(sb);
		}// end invoice for loop
		System.out.println("==========Main()===============================\n");

	}// end main()
}// end InvoiceReport class

