package com.banking.sunnybank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


// bean with hash and toString()
public class User implements Map<String, User>{

	public int userId = 0;
	public String name;
	public String password = "ttt";
	public float balance = 0;
	public boolean admin = false;
	public boolean approved = false;
	
	public float getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public static Map<String, User> getUsers() {
		return users;
	}

	public static void setUsers(Map<String, User> users) {
		User.users = users;
	}

	

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getTransfer() {
		return transfer;
	}

	public void setTransfer(float transfer) {
		this.transfer = transfer;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public float transfer;
	
	public static Map<String, User> users = new HashMap<>(2);		// persistent storage :P
	
	public User(String name) {
		this.name = name;
		users.put(name, this);
	}
	
	public User(int userId, String name, String password,float balance, boolean admin, boolean approved, float transfer) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.balance = balance;
		this.admin = admin;
		this.approved = approved;
		this.transfer = transfer;
		users.put(name, this);
	}
	
	public void deposit(float deposit) {
		balance += deposit;
	}
	
	public void withdraw(float withdrawal) {
		balance -= withdrawal;
	}

	public void transfer(float transfered) {
		
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", balance=" + balance
				+ ", admin=" + admin + ", approved=" + approved + ", transfer=" + transfer + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin, approved, balance, name, password, transfer, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return admin == other.admin && approved == other.approved
				&& Float.floatToIntBits(balance) == Float.floatToIntBits(other.balance)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Float.floatToIntBits(transfer) == Float.floatToIntBits(other.transfer) && userId == other.userId;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<User> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(User e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean remove1(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(Collection<? extends User> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int index, Collection<? extends User> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public User get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public User set(int index, User element) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(int index, User element) {
		// TODO Auto-generated method stub
		
	}

	public User remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ListIterator<User> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator<User> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void put1(String name2, User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User put(String key, User value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends User> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<String, User>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

		
	
}
