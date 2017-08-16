package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Job;
import com.niit.sociocode.model.JobApplied;

public interface JobDAO {
	// post a new job by admin
	public boolean save(Job job);

	// update job by admin
	public boolean update(Job job);

	// List all jobs for admin
	public List<Job> list();

	// Admin gets jobs by status
	// Students/Alumini gets jobs that are 'V'
	public List<Job> list(String status);

	// List all open jobs
	public List<Job> listAllOpenJobs();

	// Apply for Job
	public boolean save(JobApplied jobApplied);

	// Admin can Accept/Reject/CallForInterview
	public boolean update(JobApplied jobApplied);

	// Get all applied jobs by userId
	public List<JobApplied> listAllJobsAppliedByMe(String userId);

	// Get job application
	public JobApplied getJobApplication(String userId, String jobId);

	// get job application
	public JobApplied getJobApplication(int jobAppliedId);

	// public int getMaxJobId();
	public int getMaxJobApplicationId();

}
