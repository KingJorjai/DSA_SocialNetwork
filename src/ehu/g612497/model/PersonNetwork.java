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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;

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
	/**
	 * This method returns the people ArrayList
	 * 
	 * @return people
	 */
	public LinkedBinaryTree<Person> getPeople() {
		return people;
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
			sc.nextLine();
            lineCount++;//skip first line. the first line is an example to show the file's format
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
	
	/**Retrieves an ArrayList filled with Person variables with the birthplace the function receives as an argument
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
	/**
	 * Classifies all people on the network by groups, where each group has a common list of favorite movies. 
	 * @param users
	 * @return userGroupList
	 */
	public static ArrayList<ArrayList<Person>> groupUsersByFavoriteMovies(LinkedBinaryTree<Person> people) {
	    HashMap<String, ArrayList<Person>> movieGroups = new HashMap<>();
	    Iterator<Person> it = people.iteratorLevelOrder();
	    while (it.hasNext()) {
	        Person user = it.next();	      
	        ArrayList<String> sortedMovies = new ArrayList<>();	        
	        if (user.getFilms() != null) {
	            sortedMovies.addAll(user.getFilms());
	        }
	        Collections.sort(sortedMovies);       
	        String movieKey = String.join(",", sortedMovies);
	        movieGroups.computeIfAbsent(movieKey, k -> new ArrayList<>()).add(user);//use movieKey to classify by groups
	    }
	    return new ArrayList<>(movieGroups.values());
	}


	/**
	 * Finds the shortest chain of friends between two people, implemented with BFS (Breadth First Search), iterator used: iteratorLevelOrder
	 * 
	 * @param person1
	 * @param person2
	 * @return personList
	 */
	public ArrayList<String> findShortestFriendChain(Person p1, Person p2) {
		if (p1.getIdperson().equals(p2.getIdperson())) {
		    return new ArrayList<>(Collections.singletonList(p1.getIdperson()));
		}
	    Queue<List<Person>> queue = new LinkedList<>();
	    ArrayList<Person> initialPath = new ArrayList<>();
	    initialPath.add(p1);
	    queue.offer(initialPath);
	    Iterator<Person> it = people.iteratorLevelOrder();

	    while (!queue.isEmpty()) {
	        List<Person> currentPath = queue.poll();
	        Person lastPersonInPath = currentPath.get(currentPath.size() - 1);
	        while (it.hasNext()) {
	        	Person friend = it.next();
	        	if (lastPersonInPath.getFriends().contains(friend)) {
	                if (friend.equals(p2)) { //chain found
	                    ArrayList<Person> newPath = new ArrayList<>(currentPath);
	                    newPath.add(friend);
	                    ArrayList<String> idPath = new ArrayList<>();
	                    for (Person person : newPath) {
	                        idPath.add(person.getIdperson());
	                    }
	                    return idPath;
	                }

	                if (!currentPath.contains(friend)) { //friend not analyzed yet
	                    List<Person> newPath = new ArrayList<>(currentPath);
	                    newPath.add(friend);
	                    queue.offer(newPath);
	                }
	            }
	        }        
	        it = people.iteratorLevelOrder();//reset iterator for next level
	    }
	    return null;
	}
	/**
	 * Finds an alternative chain to the shortest one, implemented with DFS (Depth First Search), iterator used: iteratorPreOrder
	 * 
	 * @param person1
	 * @param person2
	 * @return personList
	 */
	public ArrayList<String> findAlternativeFriendChain(Person p1, Person p2) {
		if (p1.getIdperson().equals(p2.getIdperson())) {
		    return new ArrayList<>(Collections.singletonList(p1.getIdperson()));
		}
		Set<Person> visited = new HashSet<>();//used to avoid revisiting already analyzed people
	    Stack<List<Person>> stack = new Stack<>();//used to store paths
	    List<Person> initialPath = new ArrayList<>();
	    initialPath.add(p1);
	    stack.push(initialPath);
	    Iterator<Person> it = people.iteratorPreOrder();	    
	    while (!stack.isEmpty()) {
	        List<Person> currentPath = stack.pop();
	        Person lastPersonInPath = currentPath.get(currentPath.size() - 1);	        
	        if (visited.contains(lastPersonInPath)) {//person already analyzed, skip him/her
	            continue;
	        }	        
	        visited.add(lastPersonInPath);	        
	        if (lastPersonInPath.equals(p2)) { //chain found
	            ArrayList<String> idPath = new ArrayList<>();
	            for (Person person : currentPath) {
	                idPath.add(person.getIdperson());
	            }
	            return idPath;
	        }	       
	        while (it.hasNext()) {//check last persons's friends
	            Person friend = it.next();	            
	            if (lastPersonInPath.getFriends().contains(friend) && !visited.contains(friend)) {
	                List<Person> newPath = new ArrayList<>(currentPath);
	                newPath.add(friend);
	                stack.push(newPath);
	            }
	        }
	        it = people.iteratorPreOrder();//reset iterator for next depth
	    }
	    return null;
	}
	/**
	 * Finds all cliques with >4 friends.
	 * @return cliqueList
	 */
	public ArrayList<ArrayList<Person>> findCliquesWithMoreThanFourFriends() {
	    ArrayList<ArrayList<Person>> cliques = new ArrayList<>();
	    Set<Person> visited = new HashSet<>();	    
	    Iterator<Person> it = people.iteratorPreOrder();
	    while (it.hasNext()) {
	        Person p = it.next();
	        if (!visited.contains(p)) {
	            // For each person, we try to form a clique by adding them and their friends
	            Set<Person> currentCliqueSet = new HashSet<>();
	            ArrayList<Person> currentClique = new ArrayList<>();
	            Stack<Person> stack = new Stack<>();
	            stack.push(p);
	            currentCliqueSet.add(p);
	            currentClique.add(p);

	            while (!stack.isEmpty()) {
	                Person current = stack.pop();
	                for (Person friend : current.getFriends()) {//find current person's clique
	                    boolean canAdd = true;
	                    for (Person member : currentCliqueSet) {
	                        if (!member.getFriends().contains(friend)) {
	                            canAdd = false;
	                            break;
	                        }
	                    }
	                    if (canAdd && !currentCliqueSet.contains(friend)) {
	                        currentCliqueSet.add(friend);
	                        currentClique.add(friend);
	                        stack.push(friend);
	                    }
	                }
	            }
	            if (currentClique.size() >= 5) {
	                cliques.add(currentClique);
	            }
	            visited.addAll(currentCliqueSet);//add analyzed people to the visited hashset
	        }
	    }

	    return cliques;
	}	
}
