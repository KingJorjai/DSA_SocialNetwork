package ehu.g612497.model;

import java.io.File;

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
	// falta documentacion
	public Person findPersonById(String id) {
		
		Person p = null;
		Iterator<Person> it = people.iterator();
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
	
	@Override
	public String toString() {
		String result = "";
		for (Person p : people) {
			result += p.toString() + "\n";
		}
		return result;
	}
	
	//falta documentacion
	public void loadFriends(File file) {
	    
		try {
			Iterator<Person> it = people.iterator();
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
	
	public void showFriendships() {
		Iterator<Person> it = people.iterator();
		Person p = null;
		
		while(it.hasNext()) {
			p = it.next();
			
			System.out.println("These are " + p.getIdperson() + "'s friends: ");
			System.out.println(p.friendsToString() + "\n");
		}
	}
	
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
	
	
	public void printPersonsFriends(String surname) {
		Iterator<Person> it = people.iterator(); 
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
	
	public void uploadPersonsFriends(String surname) {
		
		Iterator<Person> it = people.iterator(); 
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
	
	public void uploadAndPrintPersonsFriends(String surname) {
		
		Iterator<Person> it = people.iterator(); 
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
	
	public ArrayList<Person> retrieveFromBirthplace(String city) {
		Iterator<Person> it = people.iterator(); 
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
	
	public ArrayList<Person> retrieveFromBirthday() {
		
		System.out.println("Could you please write the minimum year you would like to accept?");
		int minYear = Utils.readInt();
		System.out.println("Now could you please write the maximum year you would like to accept?");
		int maxYear = Utils.readInt();
		
		Collections.sort(people);
		ArrayList<Person> bornInBetween = new ArrayList<Person>();
		Iterator<Person> it = people.iterator(); 
		Person p = null;
		
		while(it.hasNext()) {
			
			p=it.next();
			if((p.getYearOfBirth() > minYear) && (p.getYearOfBirth() < maxYear)) {
				bornInBetween.add(p);
			}
		}
		
		return bornInBetween;
	}
	
	public String arrayListToString(ArrayList<Person> peopleList) {
		String result = "";
		for (Person p : peopleList) {
			result += p.toString() + "\n";
		}
		return result;
	}
	
	public void identifyPeopleFromSameHoemtown(File file) {
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
