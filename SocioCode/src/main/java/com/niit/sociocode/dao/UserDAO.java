package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.User;

public interface UserDAO
{
public boolean insertUser(User user);
	
	public List<User> list();
	
	public boolean deleteUser(int id);
	
	public User getUserById(int id);

}
