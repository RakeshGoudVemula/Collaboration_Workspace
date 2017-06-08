package com.niit.sociocode.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.sociocode.dao.JobDAO;
import com.niit.sociocode.model.Job;

public class JobTestCase {
	
	@Autowired
	static AnnotationConfigApplicationContext context;
	@Autowired
	static JobDAO jobDAO;
	@Autowired
	static Job job;

	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.sociocode");
		context.refresh();
		jobDAO = (JobDAO) context.getBean("jobDAO");
		job = (Job) context.getBean("job");
	}
	@Test
	public void createJobTestCase()
	{
		job.setJobId(4040);
		job.setJobProfile("JAVA Developer");
		job.setJobDescription("Coding in java");
		job.setQualification("B.tech");
		job.setPostDate(new Date());
		job.setStatus("NA");
		boolean flag=jobDAO.insertJob(job);
		assertEquals("createJobTestCase",flag,true);
	}

}
