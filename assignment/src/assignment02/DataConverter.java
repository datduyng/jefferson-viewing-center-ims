package assignment02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class DataConverter {
	
	private static int NUM_OF_CUSTOMER;
	private static int NUM_OF_PERSON;
	private static int NUM_OF_PRODUCT;
	
	private static ArrayList<Person> personList = new ArrayList<Person>();
	

	
	
	public static void main(String[] args) {
		String buffer = "";
		int i = 0; // iteration 
		// read customer.dat file
		
		
		
		try {
			String line = null;
			
			// create a BufferReader from File Reader
			BufferedReader br = new BufferedReader(new FileReader("data/Persons.dat"));
			// read the first line 
			line = br.readLine();
			NUM_OF_PERSON = Integer.parseInt(line);
			
			//Person[] personList = new Person[NUM_OF_PERSON];
			
			
			
//			// read the next line.
			line = br.readLine();
			
			//while not null(end of file) keep reading 
			while(line != null) {
				

				
				// process current line
				String[] token = line.split(";",-1);
				String personCode = token[0];
				
				String[] name = token[1].split(",");
				String lastName = name[0];
				String firstName = name[1];
				
				String[] addr = token[2].split(",");
				String street = addr[0];
				String city   = addr[1];
				String state  = addr[2];
				String zip	  = addr[3];
				String country= addr[4];
				//create address object
				Address address = new Address(street,city,
						state,zip,country);
				
				String email = token[3];
				
				// create person object 
//				persons[i] = new Person(personCode, firstName,
//						lastName, address, email);
				
				
				// create arraylist of object 
				personList.add( new Person(personCode, firstName,
						lastName, address, email) );
				//print out data
				//System.out.printf("%s\n",persons[i].toStringPerson());

				
				// 1. Java object to JSON, and save into a file
				//gson.toJson(persons[i], new FileWriter("data/Persons.json"));
				
				
				
				i++;


				// read the next line.
				line = br.readLine();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		
		
		
		
		//use to print neat tree model JSON result 
		gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonInString = gson.toJson(personList);
		System.out.println(jsonInString);
		
		// parse JSON output to aa file 
		FileWriter output = null;
		try {
			output = new FileWriter("data/persons.json");
			output.write(jsonInString);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// close output file
		try {
			output.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}// end main
 
	//test
}// end DataConverter
