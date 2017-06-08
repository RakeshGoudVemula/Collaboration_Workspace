package com.niit.sociocode.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.sociocode.dao.JobDAO;
import com.niit.sociocode.model.Job;

@RestController
public class JobController {

	@Autowired
	JobDAO jobDAO;

	@Autowired
	Job job;

	@RequestMapping(value = "/getJobs", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> list() {

		List<Job> listjobs = jobDAO.list();

		return new ResponseEntity<List<Job>>(listjobs, HttpStatus.OK);

	}

	// insert the job
	@RequestMapping(value = "/insertJob", method = RequestMethod.POST)
	public ResponseEntity<String> addJob(@RequestBody Job job) {

		job.setPostDate(new Date());
		job.setStatus("NA");
		jobDAO.insertJob(job);
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the job
	@RequestMapping(value = "/deleteJob/{jobId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteJob(@PathVariable("jobId") int jobId) {

		jobDAO.deleteJob(jobId);

		return new ResponseEntity<String>("Job Deleted Successfully", HttpStatus.OK);
	}

	// update the job

	@RequestMapping(value = "/updateJob/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateJob(@PathVariable("jobId") int jobId) {
		Job curr_job = jobDAO.getJobById(jobId);
		curr_job.setStatus("A");
		jobDAO.insertJob(curr_job);
		return new ResponseEntity<Job>(curr_job, HttpStatus.OK);

	}

}