package ehu.g612497.model;

public class SocialNetwork {
	
	/** Singleton instance */
	private static SocialNetwork INSTANCE;
	
	private PersonNetwork personNetwork;
	
	/**
     * This is the private constructor of the SocialNetwork class.
     * It throws an exception if the class is instantiated
     */
	private SocialNetwork() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		
		personNetwork = new PersonNetwork();
	}
	
	/**
	 * This method returns the singleton instance of the SocialNetwork class.
	 * 
	 * @return the singleton instance of the SocialNetwork class
	 */
	public static SocialNetwork getInstance() {
		if (INSTANCE == null) {
            INSTANCE = new SocialNetwork();
        }
		
        return INSTANCE;
	}
	
	public PersonNetwork getPersonNetwork() {
        return personNetwork;
    }

}
