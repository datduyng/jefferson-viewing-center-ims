package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import assignment02.Person;
public class DataConverter {
	
	private static Set<Person> persons = new HashSet<Person>();
	private static Set<Customer> customers = new HashSet<Customer>();
	private static Set<Product> products = new HashSet<Product>();
	
	public static void main(String[] args) {
	
		Scanner s;
		
		//parse Persons Data File, create appropriate objects
		s = DataConverter.openFile("data/Persons.dat");
		int numberOfPeople = 0;
		if(s.hasNext()) {
			numberOfPeople = Integer.parseInt(s.nextLine());
		}
		for(int i = 0; i < numberOfPeople; i++) {
			String nextLine = s.nextLine();
			Person p = new Person(nextLine);
			persons.add(p);
		}
		
		//parse Customer Data File, create appropriate objects
		s = DataConverter.openFile("data/Customers.dat");
		int numberOfCustomers = 0;
		if(s.hasNext()) {
			numberOfCustomers = Integer.parseInt(s.nextLine());
		}
		
		for(int i = 0; i < numberOfCustomers; i++) {
			String nextLine = s.nextLine();
			Customer c = new Customer(nextLine);
			customers.add(c);
		}
		
		//parse Product Data File, create appropriate objects
		s = DataConverter.openFile("data/Products.dat");
		int numberOfProducts = 0;
		if(s.hasNext()) {
			numberOfProducts = Integer.parseInt(s.nextLine());
		}
		
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
		
		s.close();
		
		for(Person person : persons) {
			System.out.println(person.toString());
			
		}
		
		System.out.println();
		
		for(Customer customer : customers) {
			System.out.println(customer.toString());
			
		}
		
		System.out.println();
		
		for(Product product : products) {
			System.out.println(product.toString());
			
		}
		
		System.out.println();
		
		/*
		Gson gson = new Gson();
		
		//use to print neat tree model JSON result 
		gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonInString = gson.toJson(customers);
		System.out.println(jsonInString);
		*/
		
		
		
		//output objects to XML
	
	} // end of main
	
	private static Scanner openFile(String fileName) {
		Scanner s;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e2) {
			throw new RuntimeException("Error: File Not Found!");
		}
		return s;
	}
	
	public static Person findPerson(String personCode, Set<Person> persons) {
		Person p = null;
		for(Person person : persons) {
			if(person.getPersonCode().equals(personCode)) {
				p = person;
			}
		}
		return p;
	}

	public static Set<Person> getPersons() {
		return persons;
	}

	public static Set<Customer> getCustomers() {
		return customers;
	}

	public static Set<Product> getProducts() {
		return products;
	}
	
	

} // end of class 
