package com.banking.sunnybank;

import java.util.List;
import java.util.Map;



public interface BankDAO {
	public boolean insertUser(User user);

	public User getUser(String name);

	public boolean updateUser(User user);

	public boolean deleteUser(User user);
	
	public Map<String, User> getAllUsers();
}
