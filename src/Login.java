/**
*	This is the login class
*/
import java.util.Scanner;
public class Login{
	public void access(){
		CashierMenu cashier = new CashierMenu();
		DoctorMenu	doctor = new DoctorMenu();
		RoomManagerMenu RM = new RoomManagerMenu();
		Scanner scanner = new Scanner(System.in);
		int user = 0, password = 1234, ispassword = 0;
		boolean correctpass = false, exit = false;
		while(!exit){
		System.out.println("Good day! Select a user");
		System.out.println("Enter a number");
		System.out.println("1 - Cashier");
		System.out.println("2 - Doctor");
		System.out.println("3 - Room Manager");
		System.out.println("4 - Exit program");
		user = scanner.nextInt();
		switch(user){
			case 1:
				while(!correctpass){
					System.out.println("Input Cashier Password");
					ispassword = scanner.nextInt();
					if(ispassword == password){
						cashier.Menu();
						correctpass = true;
					}
					else{
						System.out.println("Incorrect Password, try again");
					}
				}
				correctpass = false;
			break;
			case 2:
				while(!correctpass){
					System.out.println("Input Doctor Password");
					ispassword = scanner.nextInt();
					if(ispassword == password){
						doctor.Menu();
						correctpass = true;
					}
					else{
						System.out.println("Incorrect Password, try again");
					}
				}
				correctpass = false;
			break;
			case 3:
				while(!correctpass){
					System.out.println("Input Room Manager Password");
					ispassword = scanner.nextInt();
					if(ispassword == password){
						RM.Menu();
						correctpass = true;
					}
					else{
						System.out.println("Incorrect Password, try again");
					}
				}
				correctpass = false;
			break;
			case 4:
				exit = true;
			break;
		}
		}
	}
}
