package com.niit.sociocode.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.BlogCommentDAO;
import com.niit.sociocode.model.BlogComment;

public class BlogCommentTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static BlogCommentDAO blogCommentDAO;
	@Autowired
	static BlogComment blogComment;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		blogCommentDAO = (BlogCommentDAO) context.getBean("blogCommentDAO");
		blogComment = (BlogComment) context.getBean("blogComment");
	}
	
	@Test
	public void createBlogCommentTestCase() {
		blogComment.setBlogCommentId(1090);
		blogComment.setBlogId(1010);
		blogComment.setBlogComment("Test");
		blogComment.setBlogCommentDate(new Date());
		blogComment.setUserId(2020);
		blogComment.setUsername("Rakesh");
		
		boolean flag=blogCommentDAO.insertBlogComment(blogComment);
		assertEquals("createBlogCommentTestCase",true,flag);
		
	}
}
