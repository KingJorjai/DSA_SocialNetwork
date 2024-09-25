package ehu.g612497;

import java.util.Scanner;

public class Utils {
	
	/**Returns an Integer that is written as an input
	 * 
	 * @return num
	 */
	
	public static int readInt() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print(Messages.SELECTOR);
		
		int num;
		while (true) {
            try {
                num = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(Messages.INVALID_NUMBER);
            }
        }
		
		return num;
		
	}
	
	/**Returns a String that is written as an input
	 * 
	 * @return string
	 */
	
	public static String readString() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print(Messages.SELECTOR);
		
		String string;
		while (true) {
            try {
                string = sc.nextLine();
                break;
            } catch (NumberFormatException e) {  			// Excepcion de string al acabar
                System.out.println(Messages.INVALID_NUMBER); // Crear la excepcion al acabar
            }
        }
		
		return string;
	}
}
