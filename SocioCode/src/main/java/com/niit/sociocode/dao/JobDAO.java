package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Job;

public interface JobDAO 
{

	
	public boolean insertJob(Job job);
	
	public List<Job> list();
	
	public boolean deleteJob(int id);
	
	public Job getJobById(int id);



}
