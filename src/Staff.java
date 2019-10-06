/**
*	This is the Staff class
*/
class Staff{
	Database db = new Database();
	public void viewPatientDetails(){
		db.viewPatientDetails();
	}
	public void selectPatient(){
		db.selectPatient();
	}
}