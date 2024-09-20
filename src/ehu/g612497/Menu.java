package ehu.g612497;

import java.io.File;

import javax.swing.JFileChooser;

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
            "Print or upload into a file a persons friends",
            "Print people that were born on a city you provide",
            "Print people taht where born in between some years",
            "Read from a file identifiers and upload into another file the people who where born in the same place(including themselves)"
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
	
	/**
	 * This method creates a new Menu instance and launches the
	 * menu loop automatically.
	 */
	public Menu() {
		
		this.selectedOption = -1;
		showTitle();
		
		while (selectedOption != 0) {
			
			showOptions();
			selectedOption = Utils.readInt();
			switch (selectedOption) {
			case 1:
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
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
				friendsFileChooser.showOpenDialog(null);
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
			case 6:
				System.out.println(SocialNetwork.getInstance().getPersonNetwork().retrieveFromBirthplace(Utils.readString()));
			case 7:
				System.out.println(SocialNetwork.getInstance().getPersonNetwork().arrayListToString(SocialNetwork.getInstance().getPersonNetwork().retrieveFromBirthday()));
			case 8:
				
				JFileChooser hometownFileChooser = new JFileChooser();
				hometownFileChooser.showOpenDialog(null);
				hometownFileChooser.setVisible(true);
				if (hometownFileChooser.getSelectedFile() != null) {
					File file = hometownFileChooser.getSelectedFile();
					SocialNetwork.getInstance().getPersonNetwork().identifyPeopleFromSameHoemtown(file);
				}
				
			}
			
		}
		
		System.out.println("Goodbye!");
	}
	

	
}
