package ims;

	
public class InvoiceReport {
	
	public static void main(String[] args) {
		DataConverter.readPersonFile();
		DataConverter.readCustomerFile();
		DataConverter.readProductFile();
		DataConverter.readInvoiceFile();
	}// end main()
}// end InvoiceReport class

