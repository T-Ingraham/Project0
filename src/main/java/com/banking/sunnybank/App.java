package com.banking.sunnybank;

import java.io.IOException;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;



import java.util.*;


public class App {
	private static Scanner sc = new Scanner(System.in);
	private static User u = null;
	final static BankDAOImpal dao = BankDAOImpal.getInstance();
	final static Logger logger = Logger.getLogger(App.class);
	static Connection con = ConnectionUtil.getConnection();
	

	
	
	
	public static void main(String[] args) throws Exception {
		
		
		// ALL, TRACE, DEBUG, INFO, WARN, ERROR and FATAL
		logger.info("info level - System start");
		
		// input loop
		// no user logged in
		loop: while (true) {
			System.out.println("Options:");
			System.out.println("1 to log into an account");
			System.out.println("2 to create a customer account");
			System.out.println("3 to create an admin account");
			System.out.println("0 to exit system");
			System.out.println("Enter option: ");
			int option = sc.nextInt();
			sc.nextLine();
			logger.trace("option entered: " + option);

			switch (option) {
			// Login log out
			case 1:
				login();
				break;
			// create customer account
			case 2:
				createCustomer();
				System.out.println(u.name + " please wait until you are approved by a admin. Logging out...\n");
				continue;
			// create admin account
			case 3:
				createAdmin();
				break;
			// exit system
			case 0:
				break loop;
			}
			// != (not equal to) to null
			if (u != null) {
				loggedIn();
			}
		}

		sc.close();
		logger.trace("end of main.");
	}
	
	 
	public static void loggedIn() throws SQLException {
		// if = if user has not been approved 
		//! is reverse the true to false
		//will return user back to main menu
		if (!u.approved) {			
			System.out.println(u.name + " sorry but you were not approved for deposits and withdrawals by an admin. Logging out...\n");			
			u = null;
			return;
			
		}
		else {
		//the user is approved
		while (true) {
			System.out.println("Options:");
			System.out.println("1 to deposit");
			System.out.println("2 to withdraw");
			System.out.println("3 to transfer");
			if (u.admin) {
				System.out.println("4 to approve users");
			}
			System.out.println("0 to log out of: " + u.name);
			System.out.println("Enter option: ");

			int option = sc.nextInt();
			sc.nextLine();
			logger.trace("option entered: " + option);
			if (!u.admin && option > 3) {
				System.out.println("Invalid option. Please try again.");
				continue;
			}
			switch (option) {
			// log out
			case 0:
				System.out.println(u.name + " logging out...");
				u = null;
				return;
			case 1:
				deposit();
				break;
			case 2:
				withdraw();
				break;
			case 3:
				transfer();
				break;
			case 4:
				approveUsers();
				break;

			}
		}
		}
	}
	
	public static void login() throws IOException, SQLException {
		System.out.println("Log in...");

		boolean authenticated = false;
		while (!authenticated) {
			System.out.println("");
			System.out.println("Enter your user name: ");
			String name = sc.nextLine();
			u = dao.getUser(name);
			if (u == null) {
				System.out.println("Invalid user name: " + name);
				continue;
			}
			System.out.println("");
			System.out.println("Enter your password: ");
			String password = sc.nextLine();

			if (!u.password.equals(password)) {
				System.out.println("Invalid password for : " + name);
				logger.trace("password entered : " + password);
				logger.trace("password expected: " + u.password);
			} else
				authenticated = true;
		}
		
		System.out.println("");
		System.out.println("Welcome " + u.name);
		logger.trace("User logged in: " + u);
		System.out.println("");

		loggedIn();
	}

	public static void createCustomer() {
		System.out.println("Create a customer account");
		createUser(false, 0);
		System.out.println("Customer account created, username: " + u.name);
	}

	//when a admin account is created it admin boolean will be true automatically 
	public static void createAdmin() {
		System.out.println("Create an admin account");
		createUser(true, 0);
		System.out.println("Admin account created, username: " + u.name);
	}

	public static void createUser(boolean admin, int userId) {
		String name = null;
		while (true) {
			System.out.println("");
			System.out.println("Please create a new user name: ");
			name = sc.nextLine();
			if (dao.getUser(name) == null)
				break;
			System.out.println("User name already exists!");
		}
		System.out.println("");
		System.out.println("Please create a new password: ");
		String password = sc.nextLine();

		u = new User(userId, name, password, 0, admin, false); // logs in
		boolean inserted = dao.insertUser(u);
		if (inserted) {
			System.out.println("");
			logger.debug("User created: " + u);
		} else {
			logger.debug("User could NOT be created: " + u);
		}
	}

	/*public static boolean userApprovedCheck(boolean approved) 
	   {
		  //User u =new User();
		  PreparedStatement stat;
		  try 
		  {
			stat = con.prepareStatement("select approved from user_account where name = ?");
			stat.setBoolean(1, approved);
			
			ResultSet res = stat.executeQuery();
			
		  } 
		  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return approved;
	   }*/
	
	
	
	
	
	private static void approveUsers() {
		
		//Using map to associate the user id with approved status instantly.
		//Map was used to pick a specific user from collection.
		System.out.println("Approve users");
		
		Map<String, User> m = dao.getAllUsers();
		
		
		//if the table is empty and there are no users
		if (m.isEmpty()) {
			System.out.println("No users in the system.");
			return ;
		}
		//Will get users and admin
		System.out.println("Users in the system:");
		//K is the user name
		for (String k : m.keySet()) {
			
			//userApprovedCheck(approved);
			//System.out.print(userApprovedCheck(false));
			System.out.println(k + " " + u.approved );
			
			
			
		}	
		//ERROR: USER SHOW APPROVED EVEN THOUGH THEY ARE NOT
		//Changing approved from false to true
		System.out.println("");
		System.out.println("Enter the name of the user to approve: ");
		String name = sc.nextLine();
		logger.debug("name: " + name);
		User enteredUser = m.get(name);
		enteredUser.approved = true;
		dao.updateUser(enteredUser);
		System.out.println("");
		System.out.println("Approved by " + u.name);
		System.out.println(enteredUser + " has been approved.");
		logger.trace("" + dao.getUser(u.name));
		System.out.println("");
		
		
		
	}

	public static void deposit() {
		float deposit = 0;
		System.out.println(u.name + " your current balance is : $" + u.balance);
		System.out.println("Enter the amount to deposit: ");
		System.out.print("$");
		deposit = sc.nextFloat();
		//user will not be able to deposit a negative amount
		if(deposit <= 0) 
		{
			System.out.println("You can not deposit a neagtive amount.");
			logger.trace("amount entered: " + deposit);
		}
		else 
		{
		logger.trace("amount entered: " + deposit);
		u.deposit(deposit);
		dao.updateUser(u);		
		System.out.println(u.name + " your new balance is : $" + u.balance);
		}
		
	}

	public static void withdraw() {
		System.out.println(u.name + " your current balance is : $" + u.balance);
		System.out.println("Enter the amount to withdraw: ");
		System.out.print("$");
		float withdrawal = sc.nextFloat();
		//User will not be able to withdraw more than balance
		if (withdrawal > u.balance) 
		{
			System.out.println("You can not overdraft your account.");
			logger.trace("amount entered: " + withdrawal);
		}
		else
		{
		logger.trace("amount entered: " + withdrawal);
		u.withdraw(withdrawal);
		dao.updateUser(u);
		System.out.println(u.name + " your new balance is : $" + u.balance);
		}
	}
   public static void transfer() throws SQLException {
	   	
		System.out.println("Enter the account name to credit the amount : ");
		String reciever = sc.next();	
		System.out.println("Enter the amount to be transfered : ");
		float amount = sc.nextFloat();
		//User will not be able to transfer less than balance
		if (u.balance < amount) 
		{
			System.out.println("You do not have the funds to do a transfer.");
			logger.trace("amount transfered: " + amount);
		}
		else
		{
		con = ConnectionUtil.getConnection();
		CallableStatement stat = con.prepareCall("call transfer (?,?,?)");
		stat.setString(1, u.name);
		stat.setString(2, reciever);
		stat.setFloat(3, amount);
		
		stat.execute();
		System.out.println("Transfer completed");
		System.out.println(u.name + " your new balance is : $" + u.balance);
		logger.trace("amount transfered: " + amount);
		}
   } 
   
   
   
  /* public static void userApprovedCheck() 
   {
	  
	  try 
	  {
		PreparedStatement stat = con.prepareStatement("select approved from user_account where name = ?");
		ResultSet res = stat.executeQuery();
		
		while(res.next()) {
		stat.setBoolean(1, u.approved);
		}
	  } 
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }*/
   
   
	// @Override
	protected void finalize() throws Throwable {
		super.finalize();

	}
}
