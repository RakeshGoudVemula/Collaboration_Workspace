package com.niit.sociocode.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.ForumCommentDAO;
import com.niit.sociocode.model.ForumComment;

public class ForumCommentTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static ForumCommentDAO forumCommentDAO;
	@Autowired
	static ForumComment forumComment;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		forumCommentDAO = (ForumCommentDAO) context.getBean("forumCommentDAO");
		forumComment = (ForumComment) context.getBean("forumComment");
	}
	@Test
	public void createForumCommentTestCase() {
		forumComment.setForumCommentId(1090);
		forumComment.setForumId(1010);
		forumComment.setForumComment("Test");
		forumComment.setForumCommentDate(new Date());
		forumComment.setUserId(2020);
		forumComment.setUsername("Rakesh");
		
		boolean flag=forumCommentDAO.insertForumComment(forumComment);
		assertEquals("createForumCommentTestCase",true,flag);
		
	}
}
