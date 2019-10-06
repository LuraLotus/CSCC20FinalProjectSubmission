/**
*	This is the Cashier Menu class
*/
import java.util.Scanner;
class CashierMenu extends Staff{
	Scanner scanner = new Scanner(System.in);
	public void Menu(){
		Database db = new Database();
		int choice;
		boolean exit = false;
		while(!exit){
		System.out.println("Good day Cashier!");
		System.out.println("What would you like to do?");
		System.out.println("1 - Add patient data");
		System.out.println("2 - Edit patient data ");
		System.out.println("3 - Add misc fees ");
		System.out.println("4 - View Billing ");
		System.out.println("5 - View Patient Details ");
		System.out.println("6 - Select Patient ");
		System.out.println("7 - Exit ");
		choice = scanner.nextInt();
		switch(choice){
			case 1:
				db.addPatient();
			break;
			case 2:
				db.editPatientData();
			break;
			case 3:
				db.addAdditionalFees();
			break;
			case 4:
				db.viewBilling();
			break;
			case 5:
				db.viewPatientDetails();
			break;
			case 6:

			break;
			case 7:
				exit = true;
			break;
		}
	}
	}
}
