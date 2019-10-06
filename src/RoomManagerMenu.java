/**
*	This is the Room Manager Menu class
*/
import java.util.Scanner;
class RoomManagerMenu extends Staff{
	Scanner scanner = new Scanner(System.in);
	public void Menu(){
		Database db = new Database();
		int choice;
		boolean exit = false;
		while(!exit){
		System.out.println("Good day Room Manager!");
		System.out.println("What would you like to do?");
		System.out.println("1 - Select Patient");
		System.out.println("2 - View Patient Details");
		System.out.println("3 - Assign Room");
		System.out.println("4 - Add Room Fees");
		System.out.println("5 - Exit ");
		choice = scanner.nextInt();
		switch(choice){
			case 1:
				db.selectPatient();
			break;
			case 2:
				db.viewPatientDetails();
			break;
			case 3:
				db.assignRoom();
			break;
			case 4:
				db.addRoomFees();
			break;
			case 5:
				exit = true;
			break;
		}
		}
	}
}
