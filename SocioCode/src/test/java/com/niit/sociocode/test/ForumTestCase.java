package com.niit.sociocode.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.ForumDAO;
import com.niit.sociocode.model.Forum;

public class ForumTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static ForumDAO forumDAO;
	@Autowired
	static Forum forum;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		forumDAO = (ForumDAO) context.getBean("forumDAO");
		forum = (Forum) context.getBean("forum");
	}

	@Test
	public void createForumTestCase()
	{
		forum.setForumId(1013);
		forum.setForumName("rakesh");
		forum.setUserId(2020);
		forum.setStatus("NA");
		boolean flag=forumDAO.insertForum(forum);
		assertEquals("createBlogTestCase",flag,true);
	}
}
