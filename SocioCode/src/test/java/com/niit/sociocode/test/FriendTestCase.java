package com.niit.sociocode.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.model.Friend;

public class FriendTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static FriendDAO friendDAO;
	@Autowired
	static Friend friend;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		friendDAO = (FriendDAO) context.getBean("friendDAO");
		friend = (Friend) context.getBean("friend");
	}
	@Test
	public void createFriendTestCase()
	{
		friend.setFriendId(9090);
		friend.setStatus("Enable");
		friend.setUserId(2045);
		boolean flag=friendDAO.insertFriend(friend);
		assertEquals("createFriendTestCase",flag,true);
	}

}
