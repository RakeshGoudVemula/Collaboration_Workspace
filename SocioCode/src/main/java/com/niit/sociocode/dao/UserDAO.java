package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.User;

public interface UserDAO {
	public boolean save(User user);

	public boolean update(User user);

	public boolean delete(String userId);

	public User getUserById(String userId);

	public List<User> list();

	// public User getUserByEmail(String email);

	public User validate(String userId, String password);
	
	public void setOnline(String userID);

	public void setOffLine(String userID);


}
