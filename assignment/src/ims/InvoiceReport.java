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
		
		// variable 
		double overallSubTotal = 0.0;
		double overallFee = 0.0;
		double overallTax = 0.0;
		double overallDiscount = 0.0;
		double overallTotal = 0.0;
		
		// TODO: print 2 report. 
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb2.append("=========================\n");
		sb2.append("Excutive Report\n");
		sb2.append("=========================\n");
		sb2.append(String.format("%-10s%-60s%-20s%15s%15s%15s%15s%15s\n", "Invoice","Customer","SalePerson","Subtotal","Fees","Taxes","discounts","Total"));
		for( Invoice invoice: DataConverter.getInvoices()) {
			
			sb.append(String.format("Invoice %s\n", invoice.getInvoiceCode()));
			sb.append("==================================\n");
			sb.append(String.format("SalePerson: %s\n",invoice.getSalesPerson().getName() ));
			sb.append(String.format("Customer Info: \n%s\n",invoice.getCustomer().toInfo()));
			sb.append(String.format("-----------------------------\n"));
			sb.append(String.format("Code\tItem\t\t\t\t\t\t\t\t\t\t\t\t   SubTotal\t     Tax\tTotal\n"));
			
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
				
				if((key instanceof MovieTicket)){// movie tiecket have a special formated string
					sb.append(String.format("%s\t%s\n",key.getProductCode(),itemInString));
				}else {
					sb.append(String.format("%s\t%-96s$%10s  $%10s  $%10s\n",key.getProductCode(),itemInString, subTotalInStr,taxInStr,totalInStr));
				}
				
			}	// end p forloop
			sb.append(String.format("\t\t\t\t\t\t\t\t\t\t\t\t\t==============================\n"));
			String totalSubTotalInStr = String.format("%3.2f", totalSubTotal);
			String totalTaxInStr = String.format("%3.2f", totalTax);
			String totalTotalInStr = String.format("%3.2f", totalTotal);
			sb.append(String.format("%-104s$%10s  $%10s  $%10s\n","SUBTOTAL",totalSubTotalInStr, totalTaxInStr,totalTotalInStr));
			
			String studentDiscountInStr = "0.0";
			String totalFeeInStr = "0.0";
			String finalTotalInStr = "";
			double studentDiscount = 0.0;
			if(invoice.getCustomer() instanceof Student) {
				
				studentDiscount = (totalSubTotal * Student.discountRate) + totalTax;
				finalTotal = totalTotal - studentDiscount + Student.additionalFee;
				studentDiscountInStr = String.format("%3.2f", (studentDiscount)*-1.0);
				totalFeeInStr = String.format("%s", Student.additionalFeeToString());
				sb.append(String.format("%-127s   $%10s  \n","DISCOUNT ( 8%% STUDENT & NO TAX)", studentDiscountInStr));
				sb.append(String.format("%-127s   $%10s  \n","ADDITIONAL FEE (Student)", Student.additionalFeeToString()));
				
				//calcualte overall fee
				overallFee += Student.additionalFee;
			}else {
				finalTotal = totalTotal ;
			}
			finalTotalInStr = String.format("%3.2f", finalTotal);
			sb.append(String.format("%-127s   $%10s  \n","TOTAL: ", Student.additionalFeeToString()));
			
			sb.append(String.format("\n\t\t\t\t\tThank you\n\n"));
			sb2.append(String.format("%-10s%-30s-%-30s%-20s $%13s $%13s $%13s $%13s $%13s\n", invoice.getInvoiceCode(),
					invoice.getCustomer().getCustomerName(),invoice.getCustomer().getCustomerType(),invoice.getSalesPerson().getName()
					,totalSubTotalInStr,totalFeeInStr,totalTaxInStr,studentDiscountInStr,finalTotalInStr));
			
			
			//calculate overall total
			overallSubTotal += totalSubTotal;
			overallTax += totalTax;
			overallDiscount += studentDiscount;
			overallTotal += finalTotal;
		}// end invoice for loop
		sb2.append("===========================================================================================================================================================\n");
		sb2.append(String.format("%-90s  $%13s $%13s $%13s $%13s $%13s\n","TOTALS", floatFormatedToString(overallSubTotal),
				floatFormatedToString(overallFee),floatFormatedToString(overallTax),
				floatFormatedToString(overallDiscount*-1.0),floatFormatedToString(overallTotal)));
		System.out.println(sb2);
		System.out.println(sb);
	}// end main()
	
	public static String floatFormatedToString(double value) {
		return String.format("%3.2f", value);
	}
}// end InvoiceReport class



