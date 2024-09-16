package ehu.g612497;

import ehu.g612497.model.SocialNetwork;

public class Main {

	public static void main(String[] args) {
		
		// Instatiate the SocialNetwork singleton
		SocialNetwork socialNetwork = SocialNetwork.getInstance();
		/*
		 * Create a new Menu instance (automatically starts)
		 * it is not necessary to store the reference to the Menu instance
		 * as it is not going to be used anymore
		 */
		new Menu();
	}
}
