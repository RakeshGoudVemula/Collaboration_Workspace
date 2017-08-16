package com.niit.sociocode.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.sociocode.dao.ForumDAO;
import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.Forum;
import com.niit.sociocode.model.User;

@RestController
public class ForumController {

	private static Logger logger = LoggerFactory.getLogger(ForumController.class);

	@Autowired
	ForumDAO forumDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	User user;
	@Autowired
	HttpSession session;
	@Autowired
	Forum forum;

	// get all the forums
	@RequestMapping(value = "/getAllForums", method = RequestMethod.GET)
	public ResponseEntity<List<Forum>> getAllForums() {
		logger.debug("---Starting getAllForums method");
		return new ResponseEntity<List<Forum>>(forumDAO.list(), HttpStatus.OK);
	}

	// insert the forum
	@RequestMapping(value = "/insertForum", method = RequestMethod.POST)
	public ResponseEntity<String> addForum(@RequestBody Forum forum) {
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			forum.setCreateDate(new Date());

			forum.setStatus("N");
			forum.setUserId(loggedInUserId);
			if (forumDAO.save(forum)) {
				logger.debug("---forum is created with" + forum.getForumId());
				forum.setErrorCode("200");
				forum.setErrorMessage("Forum created successfully");
			} else {
				logger.debug("----forum is not created with");
				forum.setErrorCode("404");
				forum.setErrorMessage("Forum not created");
			}
		} else {
			logger.debug("user not loggedin---***");
			forum.setErrorCode("404");
			forum.setErrorMessage("Please login to create forum");
		}
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the forum
	@RequestMapping(value = "/deleteForum/{forumId}", method = RequestMethod.DELETE)
	public ResponseEntity<Forum> deleteForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForumById(forumId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			if (loggedInUserRole.equals("ROLE_ADMIN") || forum.getUserId().equals(loggedInUserId)) {
				if (forumDAO.delete(forumId)) {
					// forum = new Forum();
					forum.setErrorCode("200");
					forum.setErrorMessage("Delete successfull");
				} else {
					forum.setErrorCode("404");
					forum.setErrorMessage("Delete falied");
				}
			} else {
				forum.setErrorCode("404");
				forum.setErrorMessage("Not Authorized!");
			}
		} else {
			forum.setErrorCode("404");
			forum.setErrorMessage("Please Login to perform this task!");
		}
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	// update the forum

	@RequestMapping(value = "/updateForum/{forumId}", method = RequestMethod.PUT)
	public ResponseEntity<Forum> updateForum(@PathVariable("forumId") int forumId, @RequestBody Forum forum) {

		Forum curr_forum = forumDAO.getForumById(forumId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			if (loggedInUserRole.equals("ROLE_ADMIN") || (curr_forum.getUserId()).equals(loggedInUserId)) {
				curr_forum.setForumName(forum.getForumName());
				curr_forum.setForumContent(forum.getForumContent());
				if (forumDAO.update(curr_forum)) {
					curr_forum.setErrorCode("200");
					curr_forum.setErrorMessage("Updated successfull");
				} else {
					curr_forum.setErrorCode("404");
					curr_forum.setErrorMessage("Not updated successfully");
				}
			} else {
				curr_forum.setErrorCode("404");
				curr_forum.setErrorMessage("Not Authorized!");
			}
		} else {
			curr_forum.setErrorCode("404");
			curr_forum.setErrorMessage("please login to do this operation");
		}
		return new ResponseEntity<Forum>(curr_forum, HttpStatus.OK);
	}

	// approve forum
	@RequestMapping(value = "/approveForum/{forumId}", method = RequestMethod.PUT)
	public ResponseEntity<Forum> approveForum(@PathVariable("forumId") int forumId) {
		logger.debug("calling method approve forum");
		Forum forum = forumDAO.getForumById(forumId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {

			if (loggedInUserRole.equals("ROLE_ADMIN")) {

				if (updateStatus(forum, "A", "Approved")) {
					forum.setErrorCode("200");
					forum.setErrorMessage("successfully updated the forum");
				} else {
					forum.setErrorCode("404");
					forum.setErrorMessage("Not updated successfully");
				}
			} else {
				forum.setErrorCode("404");
				forum.setErrorMessage("Not Authorized");
			}
		} else {
			forum.setErrorCode("404");
			forum.setErrorMessage("Please login to continue");
		}
		logger.debug("ending method of approveforum");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	// reject forum
	@RequestMapping(value = "/rejectForum/{forumId}", method = RequestMethod.PUT)
	public ResponseEntity<Forum> rejectForum(@PathVariable("forumId") int forumId) {
		logger.debug("----calling method reject method");
		Forum forum = forumDAO.getForumById(forumId);
		logger.debug("getting the loggedinuserRole from session");
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		logger.debug("getting the loggedinuserId from session");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			logger.debug("loggedInUserId is not null");
			if (loggedInUserRole.equals("ROLE_ADMIN") || (forum.getUserId()).equals(loggedInUserId)) {
				if (updateStatus(forum, "R", "Rejected")) {
					logger.debug("The forum is rejected successfully" + forum.getUserId());
					forum.setErrorCode("200");
					forum.setErrorMessage("successfully rejected");
				} else {
					logger.debug("failed to reject successfully");
					forum.setErrorCode("404");
					forum.setErrorMessage("failed to reject successfully");
				}
			} else {
				logger.debug("the user is not authorized");
				forum.setErrorCode("404");
				forum.setErrorMessage("you are not authorized");
			}
		} else {
			logger.debug("you have to login to reject the forum");
			forum.setErrorCode("404");
			forum.setErrorMessage("please login to do this operation");
		}
		logger.debug("--------ending of the method reject forum");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);

	}

	private boolean updateStatus(Forum forum, String status, String remarks) {
		logger.debug("In changeStatus private method");
		forum.setStatus(status);
		forum.setRemarks(remarks);
		return forumDAO.update(forum);
	}

	// getApprovedForums
	@RequestMapping(value = "/getApprovedForums", method = RequestMethod.GET)
	public ResponseEntity<List<Forum>> getApprovedForums() {
		logger.debug("---calling method getApprovedForums");
		logger.debug("---Starting getForums method");
		return new ResponseEntity<List<Forum>>(forumDAO.list("A"), HttpStatus.OK);
	}

	// getNewForums
	@RequestMapping(value = "/getNewForums", method = RequestMethod.GET)
	public ResponseEntity<List<Forum>> getNewForums() {

		logger.debug("in /getNewForums");
		logger.debug("  Starting getNewForums method");
		return new ResponseEntity<List<Forum>>(forumDAO.list("N"), HttpStatus.OK);
	}

	// getting the rejected forums
	@RequestMapping(value = "/getRejectedForums", method = RequestMethod.GET)
	public ResponseEntity<List<Forum>> getRejectedForums() {
		logger.info(" ");
		logger.debug("---calling method getRejectedForums");
		logger.debug("---Starting of the method getRejectedForums");
		return new ResponseEntity<List<Forum>>(forumDAO.list("R"), HttpStatus.OK);
	}
}
