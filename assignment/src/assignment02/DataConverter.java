package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import assignment02.Person;

/**
 * The DataConverter class parses and stores data in newly created objects 
 * from the old Invoice System located on flat files.  Then the objects are 
 * serialized into both XML and JSON with the use of appropriate external 
 * libraries. 
 * 
 * @authors Reid Stagemeyer and Dat Nguyen 
 * @version 1.0
 * @since 2018-05-20
 * 
 */

public class DataConverter {
	
	/* HashSets created to store created Person, Customer, and Product objects 
	 * respectively.  Sets chosen to prevent duplicate entries.
	 */
	private static Set<Person> persons = new HashSet<Person>();
	private static Set<Customer> customers = new HashSet<Customer>();
	private static Set<Product> products = new HashSet<Product>();
	
	public static void main(String[] args) {
	
		Scanner s; // scanner declared
		
		// parse Persons Data File, create appropriate objects
		s = DataConverter.openFile("data/Persons.dat"); // set scanner to opened file
		int numberOfPeople = 0; // number of entries in Persons file
		// scan in and set number of entries
		if(s.hasNext()) {
			numberOfPeople = Integer.parseInt(s.nextLine());
		}
		// go through each entry, scan line, create person, and add to set
		for(int i = 0; i < numberOfPeople; i++) {
			String nextLine = s.nextLine();
			Person p = new Person(nextLine);
			persons.add(p);
		}
		
		// parse Customer Data File, create appropriate objects
		s = DataConverter.openFile("data/Customers.dat"); // set scanner to opened file
		int numberOfCustomers = 0; // number of entries in Customers file
		// scan in and set number of entries
		if(s.hasNext()) {
			numberOfCustomers = Integer.parseInt(s.nextLine());
		}
		// go through each entry, scan line, create customer, and add to set
		for(int i = 0; i < numberOfCustomers; i++) {
			String nextLine = s.nextLine();
			Customer c = new Customer(nextLine);
			customers.add(c);
		}
		
		// parse Product Data File, create appropriate objects
		s = DataConverter.openFile("data/Products.dat"); // set scanner to opened file
		int numberOfProducts = 0; // number of entries in Products file
		// scan in and set number of entries
		if(s.hasNext()) {
			numberOfProducts = Integer.parseInt(s.nextLine());
		}
		/* Go through each entry, scan and split line, determine type of entry,
		 * and create appropriate object.  Add created object to products set.
		 */
		for(int i = 0; i < numberOfProducts; i++) {
			String nextLine = s.nextLine();
			String[] nextLineTokens = nextLine.split(";");
			Product p = null;
			if(nextLineTokens[1].equalsIgnoreCase("M")) {
				p = new MovieTicket(nextLineTokens);
			} else if (nextLineTokens[1].equalsIgnoreCase("S")) {
				p = new SeasonPass(nextLineTokens);
			} else if (nextLineTokens[1].equalsIgnoreCase("P")) {
				p = new ParkingPass(nextLineTokens);
			} else if (nextLineTokens[1].equalsIgnoreCase("R")) {
				p = new Refreshment(nextLineTokens);
			} 
			products.add(p);
		}
		
		s.close(); // close scanner to avoid resource leak 
		
		/* Print out persons set to make sure data is stored properly in 
		 * created objects.
		 */
		for(Person person : persons) {
			System.out.println(person.toString());
			
		}
		
		System.out.println(); // print blank line for readability 
		
		/* Print out customers set to make sure data is stored properly in 
		 * created objects.
		 */
		for(Customer customer : customers) {
			System.out.println(customer.toString());
			
		}
		
		System.out.println(); // print blank line for readability 
		
		/* Print out products set to make sure data is stored properly in 
		 * created objects.
		 */
		for(Product product : products) {
			System.out.println(product.toString());
			
		}
		
		System.out.println(); // print blank line for readability 
		
		//TODO: output objects to JSON

		
		//TODO: output objects to XML
	
	} // end of main
	
	/**
	 * Sets scanner to new file of given file name.
	 * @param fileName name of file to be opened
	 * @return Scanner object set to new file
	 */
	private static Scanner openFile(String fileName) {
		Scanner s;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e2) {
			throw new RuntimeException("Error: File Not Found!");
		}
		return s;
	}
	
	/**
	 * Searches the set of Person objects and returns the Person object with 
	 * the same personCode as given.
	 *   
	 * @param personCode The code of the person being looked for.
	 * @param persons Set<Person> objects to be searched through
	 * @return The Person object matching the given code.
	 */
	public static Person findPerson(String personCode, Set<Person> persons) {
		Person p = null;
		for(Person person : persons) {
			if(person.getPersonCode().equals(personCode)) {
				p = person;
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @return Set of Person objects
	 */
	public static Set<Person> getPersons() {
		return persons;
	}
	
	/**
	 * 
	 * @return Set of Customer objects
	 */
	public static Set<Customer> getCustomers() {
		return customers;
	}

	/**
	 * 
	 * @return Set of Product objects
	 */
	public static Set<Product> getProducts() {
		return products;
	}	

} // end of class 
