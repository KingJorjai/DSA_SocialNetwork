package ehu.g612497;

import java.util.Scanner;

public class Utils {
	
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
}
