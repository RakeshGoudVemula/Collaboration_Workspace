package com.niit.sociocode.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.BlogDAO;
import com.niit.sociocode.model.Blog;

public class BlogTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static BlogDAO blogDAO;
	@Autowired
	static Blog blog;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		blogDAO = (BlogDAO) context.getBean("blogDAO");
		blog = (Blog) context.getBean("blog");
	}
	
	@Test
	public void createBlogTestCase() {
		blog.setBlogId(1004);
		blog.setBlogName("Suveen");
		blog.setBlogContent("Java Developer");
		blog.setCreateDate(new Date());
		blog.setLikes(0);
		blog.setUserId(1002);
		blog.setStatus("NA");
		boolean flag=blogDAO.insertBlog(blog);
		assertEquals("createBlogTestCase",flag,true);
		
	}
}
