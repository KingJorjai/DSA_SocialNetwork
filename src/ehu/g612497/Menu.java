package ehu.g612497;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ehu.g612497.model.Person;
import ehu.g612497.model.SocialNetwork;

/**
 * This class handles the menu of the application
 */
public class Menu {

	private String[] options =
		{
			"Load people from file",
            "Show loaded people",
            "Load friend list from file",
            "Show every friendship relationship",
            "Print or upload into a file a person's friends",
            "Print people that were born in a city you provide",
            "Print people that were born between the given years",
            "Read from a file identifiers and upload into another file the people who where born in the same place (including themselves)",
            "Group people by favorite movies",
            "Find the shortest chain of friends between two people, given their IDs",
            "Find a different chain of friends between two people, given their IDs",
            "Find all cliques that have more than 4 friends"
        };
	
	private int selectedOption;
	
		
	public void showTitle() {
		System.out.println(Messages.WELCOME);
    }
	
	/**
	 * This method shows the options of the menu.
	 * The options are stored in the {@link #options} array}.
	 * The last option is always "Exit", represented by the number 0.
	 */
	public void showOptions() {
		
		System.out.println("Choose an option:");
		
		for (int i = 0; i < options.length; i++) {
            System.out.printf("%2d. %s\n", i + 1, options[i]);
        }
		
		System.out.printf("%2d. Exit\n", 0);
    }
	private JFrame parentFrame;//used so that JFileChooser appears on top of other windows
	/**
	 * This method creates a new Menu instance and launches the
	 * menu loop automatically.
	 */
	public Menu() {
		
		parentFrame=new JFrame();
		parentFrame.setAlwaysOnTop(true);
		this.selectedOption = -1;
		showTitle();
		
		while (selectedOption != 0) {
			
			showOptions();
			selectedOption = Utils.readInt();
			switch (selectedOption) {
			case 1:
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(parentFrame);
				fileChooser.setVisible(true);
				if (fileChooser.getSelectedFile() != null) {
					File file = fileChooser.getSelectedFile();
					SocialNetwork.getInstance().getPersonNetwork().loadPeopleFromFile(file);
				}
				else {
					System.out.println(Messages.FILE_NOT_SELECTED);
				}
				
				break;
				
			case 2:
				System.out.println(SocialNetwork.getInstance().getPersonNetwork().toString());
				break;
				
			case 3: 
				
				JFileChooser friendsFileChooser = new JFileChooser();
				friendsFileChooser.showOpenDialog(parentFrame);
				friendsFileChooser.setVisible(true);
				if (friendsFileChooser.getSelectedFile() != null) {
					File file = friendsFileChooser.getSelectedFile();
					SocialNetwork.getInstance().getPersonNetwork().loadFriends(file);;
				}
				else {
					System.out.println(Messages.FILE_NOT_SELECTED);
				}
				break;
			case 4: 
				SocialNetwork.getInstance().getPersonNetwork().showFriendships();
				break;
			case 5:
				SocialNetwork.getInstance().getPersonNetwork().retrieveFriendsBySurname();
				break;
			case 6:
				System.out.println("Please, enter the name of the city: ");//tambien se podría poner en la función, pero aquí lo veo bien porque la funcion no se repite al haber un error.
				System.out.println(SocialNetwork.getInstance().getPersonNetwork().retrieveFromBirthplace(Utils.readString()));
				break;
			case 7:
				System.out.println(SocialNetwork.getInstance().getPersonNetwork().arrayListToString(SocialNetwork.getInstance().getPersonNetwork().retrieveFromBirthday()));
				break;
			case 8:
				
				JFileChooser hometownFileChooser = new JFileChooser();
				hometownFileChooser.showOpenDialog(parentFrame);
				hometownFileChooser.setVisible(true);
				if (hometownFileChooser.getSelectedFile() != null) {
					File file = hometownFileChooser.getSelectedFile();
					SocialNetwork.getInstance().getPersonNetwork().identifyPeopleFromSameHometown(file);
				}
				break;
			case 9:
				ArrayList<ArrayList<Person>> groups = SocialNetwork.getInstance().getPersonNetwork().groupUsersByFavoriteMovies(SocialNetwork.getInstance().getPersonNetwork().getPeople());
				for (int i = 0; i < groups.size(); i++) {
				    System.out.println("Group " + (i + 1) + ":");
				    ArrayList<Person> group = groups.get(i);
				    for (Person person : group) {
				        System.out.println("ID: " + person.getIdperson()); 
				    }
				    System.out.println();
				}

				break;
			case 10:
				System.out.println("Write the first person's ID:");
				String id1 = Utils.readString();
				System.out.println("Write the second person's ID:");
				String id2 = Utils.readString();
				Person p1 = SocialNetwork.getInstance().getPersonNetwork().findPersonById(id1);
				Person p2 = SocialNetwork.getInstance().getPersonNetwork().findPersonById(id2);
				if (p1 == null || p2 == null) {
				    System.out.println("One or both of the specified IDs do not exist in the network.");
				} else {
				    System.out.println(SocialNetwork.getInstance().getPersonNetwork().findShortestFriendChain(p1, p2));
				}
				break;
			case 11:
				System.out.println("Write the first person's ID:");
				String id3 = Utils.readString();
				System.out.println("Write the second person's ID:");
				String id4 = Utils.readString();
				Person p3 = SocialNetwork.getInstance().getPersonNetwork().findPersonById(id3);
				Person p4 = SocialNetwork.getInstance().getPersonNetwork().findPersonById(id4);
				if (p3 == null || p4 == null) {
				    System.out.println("One or both of the specified IDs do not exist in the network.");
				} else {
					System.out.println(SocialNetwork.getInstance().getPersonNetwork().findAlternativeFriendChain(p3, p4));
				}
				
				break;
			case 12:
				ArrayList<ArrayList<Person>> cliques = SocialNetwork.getInstance().getPersonNetwork().findCliquesWithMoreThanFourFriends();
				for (int i = 0; i < cliques.size(); i++) {
				    System.out.println("Clique " + (i + 1) + ":");
				    ArrayList<Person> clique = cliques.get(i);
				    for (Person person : clique) {
				        System.out.println("ID: " + person.getIdperson());
				    }
				    System.out.println();
				}				
			}			
		}
		System.out.println("Goodbye!");
	}	
}
