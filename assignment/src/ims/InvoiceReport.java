package ims;

import java.util.HashMap;
import java.util.Map.Entry;

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
			sb.append(String.format("SalePerson: %s\n",invoice.toString() ));
			sb.append(String.format("-----------------------------\n"));
			sb.append(String.format("Code\tItem\t\t\t\t\t\tSubTotal\tTax\tTotal\n"));
			
			
			HashMap<Product,Double> subTotalList = new HashMap<Product,Double>();
			subTotalList = invoice.getSubTotalList();
			//System.out.println(invoice.getSubTotalList());
			for(Entry<Product, Integer> p : invoice.getProductList().entrySet()) {
				// get key and valu
				Product key =  p.getKey();
				int value = p.getValue();
				
				

				double subTotal = (double)subTotalList.get(key);
				
				
				String itemInString = "";
				if(key.getProductType().equals("M")) {
					MovieTicket m = ((MovieTicket)key);
					itemInString = String.format("MovieTicket '%s' \n@%s \n%s\n", m.getMovieName()
							,m.getTheaterAddress().toString(),m.getDateTime());
				}
				sb.append(String.format("%s\t%s\t\t\t\t\t\t%.2f\t%.2f\t%.2f\n",
					key.getProductCode(),itemInString,subTotal,0.0,0.0));
			}	// end p forloop
			System.out.println(sb);
			//sb.setLength(0);
		}// end invoice for loop
		System.out.println("==========Main()===============================\n");

	}// end main()
}// end InvoiceReport class

