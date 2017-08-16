package com.niit.sociocode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.sociocode.dao.JobDAO;
import com.niit.sociocode.model.Job;
import com.niit.sociocode.model.JobApplied;

@RestController
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	JobDAO jobDAO;
	@Autowired
	Job job;
	@Autowired
	JobApplied jobApplied;
	@Autowired
	HttpSession session;

	// @RequestMapping(value = "/getAllJobs", method = RequestMethod.GET) //
	// $http.get(base_url+"/getAllJobs/)
	@GetMapping("/getAllJobs")
	public ResponseEntity<List<Job>> getAllOpendJobs() {
		logger.debug("Starting of the method getAllOpendJobs");
		// List<Job> jobs = jobDAO.listAllOpenJobs();
		return new ResponseEntity<List<Job>>(jobDAO.listAllOpenJobs(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyAppliedJobs", method = RequestMethod.GET)

	public ResponseEntity<List<JobApplied>> getMyAppliedJobs() {
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		List<JobApplied> jobs = new ArrayList<JobApplied>();

		if (loggedInUserId == null || loggedInUserId.isEmpty()) {
			job.setErrorCode("404");
			job.setErrorMessage("You have to login to see you applied jobs");
			jobs.add(jobApplied);

		} else {
			jobs = jobDAO.listAllJobsAppliedByMe(loggedInUserId);
		}

		return new ResponseEntity<List<JobApplied>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		logger.debug("Starting of the method postAJob");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

		if (loggedInUserId != null) {

			if (loggedInUserRole.equals("ROLE_ADMIN")) {

				job.setStatus("V"); // 1. V-Vacant 2. F-Filled 3. P-Pending 4.

				if (jobDAO.save(job)) {
					job.setErrorCode("200");
					job.setErrorMessage("Successfully posted the job");
					logger.debug("Successfully posted the job");

				} else {
					job.setErrorCode("404");
					job.setErrorMessage("Not able to post a job");
					logger.debug("Not able to post a job");
				}
			} else {
				job.setErrorCode("404");
				job.setErrorMessage("Not authorised to post ajob");
				logger.debug("Not able to post a job");
			}
		} else {
			job.setErrorCode("404");
			job.setErrorMessage("Login to post the job");
			logger.debug("Login as admin to post the job");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{jobId}", method = RequestMethod.POST)
	public ResponseEntity<JobApplied> applyForJob(@PathVariable("jobId") String jobId) {
		logger.debug("Starting of the method applyForJob");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");

		if (loggedInUserId == null || loggedInUserId.isEmpty()) {
			jobApplied.setErrorCode("404");
			jobApplied.setErrorMessage("You have loggin to apply for a job");
		} else {

			if (isUserAppliedForTheJob(loggedInUserId, jobId) == false) {
				jobApplied.setJobId(jobId);
				jobApplied.setUserId(loggedInUserId);
				jobApplied.setStatus("N"); // N-Newly Applied; C->Call For
											// Interview, S->Selected
				jobApplied.setDateApplied(new Date(System.currentTimeMillis()));

				logger.debug("Applied Date : " + jobApplied.getDateApplied());

				if (jobDAO.save(jobApplied)) {
					jobApplied.setErrorCode("200");
					jobApplied.setErrorMessage(
							"You have successfully applied for the job :" + jobId + " Hr will getback to you soon.");
					logger.debug("Not able to apply for the job");
				}
			} else // If the user already applied for the job
			{
				jobApplied.setErrorCode("404");
				jobApplied.setErrorMessage("You already applied for the job" + jobId);
				logger.debug("Not able to apply for the job");
			}

		}

		// jobApplication = jobDAO.getJobApplication(jobId);

		return new ResponseEntity<JobApplied>(jobApplied, HttpStatus.OK);
	}

	private boolean isUserAppliedForTheJob(String userId, String jobId) {

		if (jobDAO.getJobApplication(userId, jobId) == null) {
			return false;
		}

		return true;
	}
}