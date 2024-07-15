package package2;

import java.util.Scanner;

public class MyBaseClass {

	//class with fields and methods that we plan to inherit
	String CEO = "test person1";
	
	public void greetMe() {
		System.out.println("Welcom to ABD Company");
		
	}
	
	public void callhotline() {
		Scanner scan1 = new Scanner(System.in);
		System.out.print(""
				+ "Enter Office to call:\n"
				+ "[1]Pasig\n"
				+ "[2]Baguio\n"
				+ "[3]Manila\n\n"
				+ "choice: ");
		byte mychoice = scan1.nextByte();
		
		if(mychoice==1) {
			System.out.println("Calling Pasig hotline....");
		}
		else if(mychoice==2){
			System.out.println("Calling Baguio hotline....");	
		}
		else if(mychoice==3){
			System.out.println("Calling Manila hotline....");	
		}
		else {
			System.out.println("Please enter [1-3] only.");	
		}
		
		scan1.close();
			
	}
}
