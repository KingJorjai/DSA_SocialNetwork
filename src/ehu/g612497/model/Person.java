package ehu.g612497.model;

import java.util.Calendar;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Iterator;

import ehu.g612497.Messages;
import java.util.Collections;

public class Person implements Comparable<Person>{
		
	private String idperson;
	private String name;
	private String lastname;
	private Date birthdate;
	private String gender;
	private String birthplace;
	private String home;
	private String studiedat;
	private ArrayList<String> workplaces;
	private ArrayList<String> films;
	private String groupcode;
	private ArrayList<Person> friends; // He optado por ponerlo aqui porque me parece logico que cada persona tenga su lista de amigos.
	
	/**
	 * This method creates a new Person object
	 * 
	 * @param idperson the id of the person
	 * @param name the name of the person
	 * @param lastname the surname of the person
	 * @param birthdate the birthdate of the person
	 * @param gender the gender of the person
	 * @param birthplace the birthplace of the person
	 * @param home where the person lives
	 * @param studiedat where the person studied
	 * @param workplaces where the person worked
	 * @param films
	 * @param friends
	 * @param groupcode
	 */
	public Person(String idperson,
			String name,
            String lastname,
            Date birthdate,
            String gender,
            String birthplace,
            String home,
            String studiedat,
            ArrayList<String> workplaces,
            ArrayList<String> films,  
            String groupcode) {
		
		setIdperson(idperson);
		setName(name);
		setLastname(lastname);
		setBirthdate(birthdate);
		setGender(gender);
		setBirthplace(birthplace);
		setHome(home);
		setStudiedat(studiedat);
		setWorkplaces(workplaces);
		setFilms(films);
		friends = new ArrayList<Person>();
		
		
	}
	
	@Override
	public String toString() {
        return "Person [idperson=" + idperson + ", name=" + name + ", lastname=" + lastname + ", birthdate=" + birthdate +"]";
        }
	
	/**
	 * Adds a workplace to the list of workplaces
	 * 
	 * @param workplace the new workplace to add
	 */
	public void addWorkplace(String workplace) {
		workplaces.add(workplace);
	}
	
	/**
	 * Adds a film to the list of films
	 * 
	 * @param film the new film to add
	 */
	public void addFilm(String film) {
		films.add(film);
	}
	
	/**
	 * Adds a friend to the list of friends
	 * 
	 * @param friend the new friend to add
	 */
	public void addFriends(Person friend) {
		friends.add(friend);
	}
	
	/**
	 * Removes a workplace from the list of workplaces
	 * 
	 * @param workplace the workplace to remove
	 */
	public void removeWorkplace(String workplace) {
		workplaces.remove(workplace);
	}
	
	/**
	 * Removes a film from the list of films
	 * 
	 * @param film the film to remove
	 */
	public void removeFilm(String film) {
		films.remove(film);
	}
	
	/**
	 * Removes a friend from the list of friends
	 * 
	 * @param friend the friend to remove
	 */
	public void removeFriend(Person friend) {
		friends.remove(friend);
	}
	
	
	/**
	 * This method returns the id of the person
	 * 
	 * @return the id of the person
	 */
	public String getIdperson() {
		return idperson;
	}
	
	/**
	 * This method sets the id of the person
	 * 
	 * @param idperson the new id of the person
	 */
	public void setIdperson(String idperson) {
		this.idperson = idperson;
	}
	

	/**
	 * This method returns the name of the person
	 * 
	 * @return the name of the person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the person
	 * 
	 * @param name the new name of the person
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the lastname of the person
	 * 
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * This method sets the lastname of the person
	 * 
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * This method returns the birthdate of the person
	 * 
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	
	/**
	 * This method sets the birthdate of the person
	 * 
	 * @param birthdate the new birthdate
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * This method returns the gender of the person
	 * 
	 * @return the gender of the person
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * This method sets the new gender of the person
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
     * This method returns the birthplace of the person
     * 
     * @return the birthplace of the person
     */
	public String getBirthplace() {
		return birthplace;
	}
	
	/**
	 * This method sets the birthplace of the person
	 * 
	 * @param birthplace the new birthplace of the person
	 */
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	
	/**
	 * This method returns where the person lives
	 * 
	 * @return where the person lives
	 */
	public String getHome() {
		return home;
	}
	
	/**
	 * This method sets the new location where the person lives
	 * 
	 * @param home the new location where the person lives
	 */
	public void setHome(String home) {
		this.home = home;
	}
	
	/**
	 * This method returns where the person studied
	 * 
	 * @return where the person studied
	 */
	public String getStudiedat() {
		return studiedat;
	}
	
	/**
	 * This method sets where the person studied
	 * 
	 * @param studiedat the new place where the person studied
	 */
	public void setStudiedat(String studiedat) {
		this.studiedat = studiedat;
	}
	
	/**
	 * This method returns the places where the person has worked
	 * 
	 * @return the places where the person has worked at
	 */
	public ArrayList<String> getWorkplaces() {
		return workplaces;
	}
	
	/**
	 * This method sets where the person has worked
	 * 
	 * @param workplaces the new places where the person has worked
	 */
	public void setWorkplaces(ArrayList<String> workplaces) {
		this.workplaces = workplaces;
	}
	
	/**
     * This method returns the films that person likes
     * 
     * @return the films that person likes
     */
	public ArrayList<String> getFilms() {
		return films;
	}
	
	/**
	 * This method sets the films that person likes
	 * 
	 * @param films the new films that person likes
	 */
	public void setFilms(ArrayList<String> films) {
		this.films = films;
	}
	
	/**
	 * This method returns the persons friends
	 * 
	 * @return the persons friends
	 */
	public ArrayList<Person> getFriends() {
		return friends;
	}
	
	/**
	 * This method sets the films that person likes
	 * 
	 * @param films the new films that person likes
	 */
	public void setFriends(ArrayList<Person> friends) {
		this.friends = friends;
	}
	
	/**
	 * This method returns the group code of the person in DSA
	 * 
	 * @return the group code of the person in DSA
	 */
	public String getGroupcode() {
		return groupcode;
	}
	
	/**
	 * This method sets the group code of the person in DSA
	 * 
	 * @param groupcode the new group code of the person in DSA
	 */
	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}
	
	/**Return the toString String from every Person in the friends ArrayList
	 * 
	 * @return toString
	 */
	
	public String friendsToString() {
		Iterator<Person> it = friends.iterator();
		String toString = new String();
		Person p;
		
		while(it.hasNext()) {
			p = it.next();
			if(p != null) {
				toString += p.getIdperson() + "; ";
			}	
		}
		return toString;
	}
	
	/**Return the year of birth from the Person
	 * 
	 * @return yearOfBirth
	 */
	
	public int getYearOfBirth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getBirthdate());
		return calendar.get(Calendar.YEAR);
	}
	
	/**Compares this Person data type with another for the collections.sort function
	 * 
	 * return compareTo
	 */
	
	public int compareTo(Person o) {
		if(this.getBirthplace().equals(o.getBirthplace())) {
			if(this.getLastname().equals(o.getLastname())) {
				return this.getName().compareTo(o.getName());
			}else {
				return this.getLastname().compareTo(o.getLastname());
			}
		}else {
			return (this.getBirthplace().compareTo(o.getBirthplace()));
		}
		
	}
	

}
