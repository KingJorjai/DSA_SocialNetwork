package ehu.g612497.model;

import java.io.File;
import ehu.g612497.dataTypes.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ehu.g612497.Messages;
import ehu.g612497.Utils;


public class PersonNetwork {
	
	
	//ArrayList<Person> people;
	LinkedBinaryTree<Person> people;
	
	
	/**
	 * This method creates a new PersonNetwork,
	 * which is a list of people in the network
	 */
	public PersonNetwork() {
		people = new LinkedBinaryTree<Person>();
	}
	
	/**This method adds a Person data type to the people ArrayList
	 * 
	 * @param p
	 */
	
	public void addPerson(Person p) {
		people.addPerson(p);
	}
	
	
	
	public void removePerson(Person p) {
		if(people.contains(p)) {
			people.removeIt(p);
		}
	}
	/**This method finds a specific Person data type from the people ArrayList
	 * 
	 * @param id
	 * @return p
	 */
	public Person findPersonById(String id) {
		
		Person p = null;
		Iterator<Person> it = people.iteratorInOrder();
		boolean found = false;
		
		while(it.hasNext() && !found) {
			p = it.next();
			if(p.getIdperson().equals(id)) {
				found = true;
			}else {
				p = null;
			}
		}
		
		return p;
	}
	
	/**Loads Person data types from the file to the people ArrayList
	 * 
	 * @param file
	 */
	
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
					cal.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
					cal.set(Calendar.MONTH, Integer.parseInt(dateParts[1]));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
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
	
	/** Returns a String data type from the people ArrayList
	 * 
	 * @return result
	 */
	
	@Override
	public String toString() {
		String result = "";
		Iterator<Person> it = people.iteratorInOrder();
		
		while (it.hasNext()) {
			result += it.next().toString() + "\n";
		}
		return result;
	}
	
	/**Loads each persons friends from the txt file you select
	 * 
	 * @param file
	 */
	public void loadFriends(File file) {
	    
		try {
			Iterator<Person> it = people.iteratorInOrder();
			Person p = null;
			while(it.hasNext()) {
				p = it.next();
				Scanner sc = new Scanner(file);
				int lineCount = 0;
				while (sc.hasNextLine()) {
				
					lineCount++;
				
					try {
						String line = sc.nextLine();
						String[] parts = line.split(",");
					
						String idperson1 = parts[0];
					
						String idperson2 = parts[1];
					
						if(p.getIdperson().equals(idperson1)) {
						
							p.addFriends(this.findPersonById(idperson2));
						
						}else if(p.getIdperson().equals(idperson2)) {
							p.addFriends(this.findPersonById(idperson1));
						}
					} catch (Exception e) {
						System.out.println("Error in line " + lineCount + ": " + e.getMessage());
					}
				}
			}
			} catch (FileNotFoundException e) {
				System.out.println(Messages.FILE_NOT_FOUND);
		
			}
	}
	
	/**Shows each persons friends
	 * 
	 */
	
	public void showFriendships() {
		Iterator<Person> it = people.iteratorInOrder();
		Person p = null;
		
		while(it.hasNext()) {
			p = it.next();
			
			System.out.println("These are " + p.getIdperson() + "'s friends: ");
			System.out.println(p.friendsToString() + "\n");
		}
	}
	
	/**You will either print a Person data types friends, upload them into a file or do both according to the number you input.
	 * 
	 */
	
	public void retrieveFriendsBySurname() {
		
		boolean tried = false;
		while(!tried){
				
				System.out.println("Please, write the surname of the person who's friends list you want to see: ");
				String surname = Utils.readString();
				
				System.out.println("Thanks, now what do you want us to do?");
				
				System.out.println("1- Print that persons friends list");
				System.out.println("2- Get this persons friends into a file you will be able to find in the data folder");
				System.out.println("3- Both of the above");
				
				int chooseToDo = Utils.readInt();
				
				switch(chooseToDo) {
				
				case 1:
					
					printPersonsFriends(surname);
					
					tried = true;
					break;
					
				case 2:
					
					uploadPersonsFriends(surname);
				
					tried = true;
					break;
				case 3:
					
					uploadAndPrintPersonsFriends(surname);
					
					tried = true;
					break;
				default:
					System.out.println("Please choose a number thats valid.");
				}
		}			
	}
	
	/**Print a Person data types friends
	 * 
	 * @param surname
	 */
	public void printPersonsFriends(String surname) {
		Iterator<Person> it = people.iteratorInOrder(); 
		Person p = null;
		
		while(it.hasNext()) {
			p = it.next();
			if(p.getLastname().equals(surname)) {
				Iterator<Person> iterator = p.getFriends().iterator();
				System.out.println("These are " + p.getIdperson() + "'s friends:" + "\n");
				while(iterator.hasNext()) {
					System.out.println(iterator.next().toString());
				}
				System.out.println();
			}
		}
	}
	
	/**Upload a Person data types friends to a file with a name of your choice
	 * 
	 * @param surname
	 */
	
	public void uploadPersonsFriends(String surname) {
		
		Iterator<Person> it = people.iteratorInOrder(); 
		Person p = null;
		
		try{
		
			String file = JOptionPane.showInputDialog("What should the name of your file be? ");
			FileWriter fs = new FileWriter ("./data/" + file);
			
			while(it.hasNext()){
				p = it.next();
				if(p.getLastname().equals(surname)) {
					Iterator<Person> iter = p.getFriends().iterator();
					fs.write("These are " + p.getIdperson() + "'s friends:" + "\n");
					while(iter.hasNext()) {
						fs.write(iter.next().toString() + "\n");
					}
					System.out.println();
				}
			}
			fs.close();
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,e.getMessage(), "I/O Exception", JOptionPane.PLAIN_MESSAGE);
			System.out.println("Izena gaizki sartu da.");
		}
	}
	
	/**Uploads and prints a Person data types friends
	 * 
	 * @param surname
	 */
	
	public void uploadAndPrintPersonsFriends(String surname) {
		
		Iterator<Person> it = people.iteratorInOrder(); 
		Person p = null;
		
		try {
			
			String file2 = JOptionPane.showInputDialog("What should the name of your file be? ");
			FileWriter fs2 = new FileWriter ("./data/" + file2);
			
			while(it.hasNext()) {
				p = it.next();
				if(p.getLastname().equals(surname)) {
					Iterator<Person> iter = p.getFriends().iterator();
					Iterator<Person> iterator = p.getFriends().iterator();
					fs2.write("These are " + p.getIdperson() + "'s friends:" + "\n");
					System.out.println("These are " + p.getIdperson() + "'s friends:" + "\n");
					while(iterator.hasNext()) {
						fs2.write(iter.next().toString() + "\n");
						System.out.println(iterator.next().toString());
					}
					System.out.println();
				}
			}
			fs2.close();
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,e.getMessage(), "I/O Exception", JOptionPane.PLAIN_MESSAGE);
			System.out.println("Izena gaizki sartu da.");
		}
	}
	
	/**Retrieves an ArrayList filled with Person variebles with the birthplace the function recieves as an argument
	 * 
	 * @param city
	 * @return citizen
	 */
	
	public ArrayList<Person> retrieveFromBirthplace(String city) {
		Iterator<Person> it = people.iteratorInOrder(); 
		Person p = null;
		ArrayList<Person> citizen = new ArrayList<Person>();
		
		while(it.hasNext()) {
			p = it.next();
			if(p.getBirthplace().equals(city)) {
				citizen.add(p);
			}
		}
		
		return citizen;
	}
	
	/** Retrieves an ArrayList filled with Person variables with a birthdate that is in between the the two years you input manually
	 * 
	 * @return bornInBetween
	 */
	
	public ArrayList<Person> retrieveFromBirthday() {
		
		System.out.println("Could you please write the minimum year you would like to accept?");
		int minYear = Utils.readInt();
		System.out.println("Now could you please write the maximum year you would like to accept?");
		int maxYear = Utils.readInt();
		
		ArrayList<Person> bornInBetween = new ArrayList<Person>();
		Iterator<Person> it = people.iteratorInOrder(); 
		Person p = null;
		
		while(it.hasNext()) {
			
			p=it.next();
			if((p.getYearOfBirth() > minYear) && (p.getYearOfBirth() < maxYear)) {
				bornInBetween.add(p);
			}
		}
		
		return bornInBetween;
	}
	
	/**Returns a String with all the information from all the Person data types in the ArrayList received as an argument
	 * 
	 * @param peopleList
	 * @return result
	 */
	
	public String arrayListToString(ArrayList<Person> peopleList) {
		String result = "";
		Iterator<Person> it = peopleList.iterator();
		
		while (it.hasNext()) {
			result += it.next().toString() + "\n";
		}
		
		return result;
	}
	
	/**Identify the people from the same hometown as the people received from a file
	 * 
	 * @param file
	 */
	
	public void identifyPeopleFromSameHometown(File file) {
		try {
			
			Scanner sc = new Scanner(file);
			int lineCount = 0;
			
			String file2 = JOptionPane.showInputDialog("We are writting the name, surname, birthplace and what they studied on a file\nWhat should the name of your file be? ");
			FileWriter fs2 = new FileWriter ("./data/" + file2);
			
			while (sc.hasNextLine()) {
			
				lineCount++;
			
				try {
					String line = sc.nextLine();
					Person p = findPersonById(line);
					ArrayList<Person> citizens = retrieveFromBirthplace(p.getBirthplace());
					Iterator<Person> it = citizens.iterator(); 
					Person citizen = null;
					fs2.write("These are the people that where born in " + p.getBirthplace() + ":" + "\n");
					while(it.hasNext()) {
						
						citizen = it.next();
						
						fs2.write(citizen.getName() + ";" + citizen.getLastname() + ";" + citizen.getBirthplace() + ";" + citizen.getStudiedat() + "\n");
						
					}
					
					
					
				} catch (Exception e) {
					System.out.println("Error in line " + lineCount + ": " + e.getMessage());
				}
			}
			fs2.close();
		} catch (FileNotFoundException e) {
			System.out.println(Messages.FILE_NOT_FOUND);
	
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,e.getMessage(), "I/O Exception", JOptionPane.PLAIN_MESSAGE);
			System.out.println("Izena gaizki sartu da.");
		}
	}
	
}
