/**
*	This is the Database class
*/

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

class Database{
	public void addPatient() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Scanner sc = new Scanner(System.in);
		Boolean bool = true;
		System.out.print("Enter Patient Name: ");
		String pname = sc.nextLine();
		System.out.print("Enter Patient Address: ");
		String paddress = sc.nextLine();
		System.out.print("Enter Patient Contact Number: ");
		String pcontact = sc.nextLine();
		System.out.print("Enter Patient Birthdate (yyyy/mm/dd):");
		String pbdate = sc.nextLine();
		System.out.print("Is an Inpatient?(1/0): ");
		byte inpatient = sc.nextByte();
		sc.nextLine();
		if(inpatient == 1)
		{
			System.out.print("Enter Room ID: ");
			int rid = sc.nextInt();
		}
		sc.nextLine();
		try
		{
			java.util.Date date = format.parse(pbdate);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
			String query = "insert into Patient (PatientName, Address, ContactNumber, Birthdate, isInpatient)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement prepStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prepStmt.setString(1, pname);
			prepStmt.setString(2, paddress);
			prepStmt.setString(3, pcontact);
			prepStmt.setDate(4, sqlDate);
			prepStmt.setByte(5, inpatient);
			prepStmt.execute();
			ResultSet rs = prepStmt.getGeneratedKeys();
			int pid = 0;
			if(rs.next())
			{
				pid = rs.getInt(1);
			}
			if(inpatient == 1)
			{
				System.out.print("Enter Room ID: ");
				int rid = sc.nextInt();
				query = "insert into InPatient (Patient_PatientID, Room_RoomID)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query);
				prepStmt.setInt(1, pid);
				prepStmt.setInt(2, rid);
				prepStmt.execute();
			}
			query = "insert into Bill (Patient_PatientID)" + " values (?)";
			prepStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prepStmt.setInt(1, pid);
			prepStmt.execute();
			rs = prepStmt.getGeneratedKeys();
			int bid = 0;
			if(rs.next())
			{
				bid = rs.getInt(1);
			}
			query = "insert into Charges (Bill_BillingID)" + " values (?)";
			prepStmt = connect.prepareStatement(query);
			prepStmt.setInt(1, bid);
			prepStmt.execute();

			connect.close();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}

	}
	public void editPatientData() throws Exception {
		Scanner sc = new Scanner(System.in);
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
		System.out.print("Enter Patient ID: ");
		int pid = sc.nextInt();
		sc.nextLine();
		System.out.println("Select data to edit: ");
		System.out.println("0 - Cancel\n1 - Name\n2 - Address\n3 - Contact Number\n4 - Birthdate");
		System.out.print("Input: ");
		int c = sc.nextInt();
		sc.nextLine();
		switch(c)
		{
			case 0: return;
			case 1:
				System.out.print("Enter Patient Name: ");
				String pname = sc.nextLine();
				try
				{
					String query = "update Patient" + " set PatientName = ?" + " where PatientID = ?";
					PreparedStatement prepStmt = connect.prepareStatement(query);
					prepStmt.setString(1, pname);
					prepStmt.setInt(2, pid);
					prepStmt.execute();

					connect.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
				break;
			case 2:
				System.out.print("Enter Patient Address: ");
				String paddress = sc.nextLine();
				try
				{
					String query = "update Patient" + " set Address = ?" + " where PatientID = ?";
					PreparedStatement prepStmt = connect.prepareStatement(query);
					prepStmt.setString(1, paddress);
					prepStmt.setInt(2, pid);
					prepStmt.execute();

					connect.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
				break;
			case 3:
				System.out.print("Enter Patient Contact Number: ");
				String pcontact = sc.nextLine();
				try
				{
					String query = "update Patient" + " set ContactNumber = ?" + " where PatientID = ?";
					PreparedStatement prepStmt = connect.prepareStatement(query);
					prepStmt.setString(1, pcontact);
					prepStmt.setInt(2, pid);
					prepStmt.execute();

					connect.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
				break;
			case 4:
				System.out.print("Enter Patient Birthdate (yyyy/mm/dd): ");
				String pbdate = sc.nextLine();
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date = format.parse(pbdate);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				try
				{
					String query = "update Patient" + " set Birthdate = ?" + " where PatientID = ?";
					PreparedStatement prepStmt = connect.prepareStatement(query);
					prepStmt.setDate(1, sqlDate);
					prepStmt.setInt(2, pid);
					prepStmt.execute();

					connect.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
				break;
		}
	}
	public void addAdditionalFees() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Patient ID: ");
		int pid = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Fee Name: ");
		String feeName = sc.nextLine();
		System.out.print("Enter amount: ");
		double feeAmount = sc.nextDouble();
		sc.nextLine();
		BigDecimal sqlFee = new BigDecimal(feeAmount);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
			String query = "insert into AddFees (AddFeeName, AddFeeAmount)" + " values(?, ?)";
			PreparedStatement prepStmt = connect.prepareStatement(query);
			prepStmt.setString(1, feeName);
			prepStmt.setBigDecimal(2, sqlFee);
			prepStmt.execute();

			connect.close();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	public void viewBilling(){

	}
	public void addDoctorFees() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Amount: ");
		double docFee = sc.nextDouble();
		sc.nextLine();
		BigDecimal sqlDocFee = new BigDecimal(docFee);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
			String query = "insert into Services (ServiceName, Cost)" + " values(Doctor Fee, ?)";
			PreparedStatement prepStmt = connect.prepareStatement(query);
			prepStmt.setBigDecimal(1, sqlDocFee);
			prepStmt.execute();

			connect.close();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	public void addServices() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Patient ID: ");
		int pid = sc.nextInt();
		System.out.println("Enter Service Type: ");
		System.out.println("0 - Cancel\n1 - Medicine\n2 - Laboratory\n3 - Equipment");
		System.out.print("Input: ");
		int c = sc.nextInt();
		sc.nextLine();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
		Statement stmt;
		PreparedStatement prepStmt;
		ResultSet rs;
		String query;
		int sid = 0;
		switch(c)
		{
			case 0:
				return;
			case 1:
				System.out.print("Enter Medicine ID: ");
				int mid = sc.nextInt();
				sc.nextLine();
				double temp = 0;
				query = "select * from Medicines where MedID = " + mid;
				stmt = connect.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					temp = rs.getDouble("Cost");
				}
				BigDecimal mcost = new BigDecimal(temp);
				query = "insert into Services (ServiceName, Cost)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				prepStmt.setString(1, "Medicine");
				prepStmt.setBigDecimal(2, mcost);
				prepStmt.execute();
				rs = prepStmt.getGeneratedKeys();
				if(rs.next())
				{
					sid = rs.getInt(1);
				}

				query = "insert into Services_has_Medicines (Services_ServiceID, Medicines_MedID)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query);
				prepStmt.setInt(1, sid);
				prepStmt.setInt(2, mid);
				prepStmt.execute();

				connect.close();
				break;
			case 2:
				System.out.print("Enter Laboratory ID: ");
				int lid = sc.nextInt();
				sc.nextLine();
				double lcost = 0;
				query = "select * from Laboratory where LabID = " + lid;
				stmt = connect.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					lcost = rs.getDouble("Cost");
				}
				BigDecimal sqllcost = new BigDecimal(lcost);
				query = "insert into Services (ServiceName, Cost)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				prepStmt.setString(1, "Laboratory");
				prepStmt.setBigDecimal(2, sqllcost);
				prepStmt.execute();
				rs = prepStmt.getGeneratedKeys();
				if(rs.next())
				{
					sid = rs.getInt(1);
				}

				query = "insert into Services_has_Medicines (Services_ServiceID, Laboratory_LabID)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query);
				prepStmt.setInt(1, sid);
				prepStmt.setInt(2, lid);
				prepStmt.execute();

				connect.close();
				break;
			case 3:
				System.out.print("Enter Equipment ID: ");
				int eid = sc.nextInt();
				sc.nextLine();
				double ecost = 0;
				query = "select * from Equipment where EquipID = " + eid;
				stmt = connect.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					ecost = rs.getDouble("Cost");
				}
				BigDecimal sqlecost = new BigDecimal(ecost);
				query = "insert into Services (ServiceName, Cost)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				prepStmt.setString(1, "Equipment");
				prepStmt.setBigDecimal(2, sqlecost);
				prepStmt.execute();
				rs = prepStmt.getGeneratedKeys();
				if(rs.next())
				{
					sid = rs.getInt(1);
				}

				query = "insert into Services_has_Medicines (Services_ServiceID, Equipment_EquipID)" + " values (?, ?)";
				prepStmt = connect.prepareStatement(query);
				prepStmt.setInt(1, sid);
				prepStmt.setInt(2, eid);
				prepStmt.execute();

				connect.close();
				break;
		}
	}
	public void assignRoom() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Patient ID: ");
		int pid = sc.nextInt();
		System.out.print("Enter Room ID: ");
		int rid = sc.nextInt();
		sc.nextLine();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
		String query = "insert into InPatient (Patient_PatientID, Room_RoomID)" + " values (?, ?)";
		PreparedStatement prepStmt = connect.prepareStatement(query);
		prepStmt.setInt(1, pid);
		prepStmt.setInt(2, rid);
		prepStmt.execute();

		connect.close();

	}
	public void addRoomFees() throws Exception {
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Patient ID: ");
			int pid = sc.nextInt();
			int bid = 0;
			int rid = 0;
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
			String query = "select * from Bill" + " where PatientID = " + pid;
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				bid = rs.getInt("BillingID");
			}
			query = "select * from InPatient" + " where PatientID = " + pid;
			stmt = connect.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				rid = rs.getInt("RoomID");
			}
			query = "insert into Charges (Room_RoomID)" + " values(?)" + " where BillingID = ?";
			PreparedStatement prepStmt = connect.prepareStatement(query);
			prepStmt.setInt(1, rid);
			prepStmt.setInt(2, bid);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	public void viewPatientDetails() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Patient ID: ");
		int pid = sc.nextInt();
		sc.nextLine();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalbillingsys", "root", "admin");
		String query = "select * from Patient where PatientID = " + pid;
		Statement stmt = connect.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			String pname = rs.getString("PatientName");
			String paddress = rs.getString("Address");
			String pcontact = rs.getString("ContactNumber");
			Date pbdate = rs.getDate("Birthdate");
			byte isInpatient = rs.getByte("isInpatient");
			System.out.println("Patient Information");
			System.out.println("Patient Name: " + pname + "\nAddress: " + paddress + "\nContact Number: " + pcontact + "\nBirthdate: " + pbdate + "\nInpatient: " + isInpatient);
		}
	}
}
