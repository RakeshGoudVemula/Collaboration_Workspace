package com.niit.sociocode.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.User;

public class UserTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static UserDAO userDAO;
	@Autowired
	static User user;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		userDAO = (UserDAO) context.getBean("userDAO");
		user = (User) context.getBean("user");
	}
	@Test
	public void createUserTestCase()
	{
		user.setUserId(497);
		user.setFirstName("rakesh");
		user.setLastName("vemula");
		user.setPassword("password");
		user.setRole("ROLE_USER");
		user.setIsOnline("True");
		user.setStatus("Enable");
		boolean flag=userDAO.insertUser(user);
		assertEquals("createUserTestCase",flag,true);
	}

}
