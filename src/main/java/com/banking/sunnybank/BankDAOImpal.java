package com.banking.sunnybank;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

//import com.training.pms.jdbc_demo.jdbc_demo.utility.DBConnection;

public class BankDAOImpal implements BankDAO {
	
	Connection con = ConnectionUtil.getConnection();
	private static BankDAOImpal instance;

	private BankDAOImpal() {
	}

	public static BankDAOImpal getInstance() {
		if (instance == null) {
			instance = new BankDAOImpal();
		}
		return instance;
	}

	public User getUser(String name) {
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT userid, name, password, balance, admin, approved FROM user_account WHERE name = ?");
			ps.setString(1, name);
			App.logger.trace("getUser query executing...");
			ResultSet rs = ps.executeQuery();
			App.logger.trace("query done.");
			if (rs.next()) {
				return new User(rs.getInt("userid"),  rs.getString("name"), rs.getString("password"), rs.getFloat("balance"), rs.getBoolean("admin"),
						rs.getBoolean("approved"), 0);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		App.logger.debug("No user found (Result set empty)");
		return null;

	}

	public boolean insertUser(User u) {
		try  {
			int idx = 0;
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO user_account (name, password, balance, admin, approved) "
					+ "VALUES (?, ?, ?, ?, ?)");
			ps.setString(++idx, u.name);
			ps.setString(++idx, u.password);
			ps.setFloat(++idx, u.balance);
			ps.setBoolean(++idx, u.admin);
			ps.setBoolean(++idx, u.approved);

			App.logger.trace("executing INSERT...");
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.print(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		App.logger.debug("INSERT user failed: " + u);
		return false;
	}

	public boolean deleteUser(User u) {
		// DELETE FROM user_account WHERE name = 'Ian B';
		try  {
			PreparedStatement ps = con.prepareStatement("DELETE FROM user_account WHERE name = ?");
			ps.setString(1, u.name);

			App.logger.trace("executing DELETE...");
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		App.logger.debug("DELETE user failed: " + u);
		return false;
	}

	public boolean updateUser(User u) {
		// UPDATE user_account SET balance = 4.0, approved = 0 WHERE name = ?;
		PreparedStatement ps = null;
		int idx = 0;
		try {
			
			ps = con.prepareStatement("update user_account set balance = ?, approved = ?, admin = ? WHERE name = ?");
			ps.setFloat(++idx, u.balance);
			ps.setBoolean(++idx, u.approved);
			ps.setBoolean(++idx, u.admin);
			ps.setString (++idx, u.name);
			
			idx = ps.executeUpdate();
			App.logger.trace("executing UPDATE to User..." + u.name);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}
		return false;
		
	}
	



	@Override
	public Map<String, User> getAllUsers() {
		Map<String, User> um = new HashMap<>();
		try (Connection con = ConnectionUtil.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT userid, name, password, balance, admin, approved "
							+ "FROM user_account");
			App.logger.trace("getAllUsers query executing...");
			ResultSet rs = ps.executeQuery();
			App.logger.trace("query done.");
			while (rs.next()) {
				User u = new User(rs.getInt("userid"), 
							rs.getString("name"), 
							rs.getString("password"), 
							rs.getFloat("balance"),
							rs.getBoolean("admin"),
							rs.getBoolean("approved"), 0);
				um.put(u.name, u);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		App.logger.debug("getAll elements in map: " + um.size());
		return um;
	}


	


	
	


}