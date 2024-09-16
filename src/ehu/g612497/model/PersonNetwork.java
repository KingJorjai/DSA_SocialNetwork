package ehu.g612497.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import ehu.g612497.Messages;

public class PersonNetwork {
	
	ArrayList<Person> people;
	
	/**
	 * This method creates a new PersonNetwork,
	 * which is a list of people in the network
	 */
	public PersonNetwork() {
		people = new ArrayList<Person>();
	}
	
	public void addPerson(Person p) {
		people.add(p);
	}
	
	public void loadPeopleFromFile(File file) {
    
		try {
			Scanner sc = new Scanner(file);
			int lineCount = 0;
			while (sc.hasNextLine()) {
				
				lineCount++;
				
                try {
					String line = sc.nextLine();
					String[] parts = line.split(",");
					
					String idperson = parts[0];
					
					String name = parts[1];
					
					String lastname = parts[2];
					
					String[] dateParts = parts[3].split("\\D+");
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
					cal.set(Calendar.MONTH, Integer.parseInt(dateParts[1]));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
					Date birthdate = cal.getTime();
					
					String gender = parts[4];
					
					String birthplace = parts[5];
					
					String home = parts[6];
					
					String studiedat = parts[7];
					
					ArrayList<String> workplaces = new ArrayList<String>();
					String[] workplacesParts = parts[8].split(";");
					for (String workplace : workplacesParts) {
					    workplaces.add(workplace);
					}
					
					ArrayList<String> films = new ArrayList<String>();
					String[] filmsParts = parts[9].split(";");
					for (String film : filmsParts) {
					    films.add(film);
					}
					
					String groupcode = parts[10];
					
					Person p = new Person(
							idperson,
							name,
							lastname,
							birthdate,
							gender,
							birthplace,
							home,
							studiedat,
					        workplaces,
							films,
							groupcode);
					addPerson(p);
				} catch (Exception e) {
                    System.out.println("Error in line " + lineCount + ": " + e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(Messages.FILE_NOT_FOUND);
		}
    
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Person p : people) {
			result += p.toString() + "\n";
		}
		return result;
	}

}
