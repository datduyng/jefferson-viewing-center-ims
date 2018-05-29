package ims;

	
public class InvoiceReport {
	
	public static void main(String[] args) {
		
		// read and process data from file.
		DataConverter.readPersonFile();
		DataConverter.readCustomerFile();
		DataConverter.readProductFile();
		DataConverter.readInvoiceFile();
		
		// TODO: print 2 report. 
	}// end main()
}// end InvoiceReport class

