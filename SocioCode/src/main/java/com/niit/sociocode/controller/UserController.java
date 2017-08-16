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

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.User;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> list() {

		List<User> listusers = userDAO.list();

		return new ResponseEntity<List<User>>(listusers, HttpStatus.OK);

	}

	// insert the user
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {

		logger.debug("->->->->calling method createUser");
		if (userDAO.getUserById(user.getUserId()) == null) {
			logger.debug("->->->->User is going to create with id:" + user.getUserId());
			user.setIsOnline("N");
			user.setStatus("N");
			if (userDAO.save(user) == true) {
				user.setErrorCode("200");
				user.setErrorMessage(
						"Thank you  for registration. You have successfully registered as " + user.getRole());
			} else {
				user.setErrorCode("404");
				user.setErrorMessage("Could not complete the operatin please contact Admin");

			}

			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			logger.debug("->->->->User already exist with id " + user.getUserId());
			user.setErrorCode("404");
			user.setErrorMessage("User already exist with id : " + user.getUserId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	// update user
	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		logger.debug("->->->->calling method updateUser");
		if (userDAO.getUserById(user.getUserId()) == null) {
			logger.debug("->->->->User does not exist with id " + user.getUserId());
			user = new User(); // ?
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + user.getUserId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		userDAO.update(user);
		logger.debug("->->->->User updated successfully");
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	// delete the user
	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {

		userDAO.delete(userId);

		return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
	}

	// get user

	@RequestMapping(value = "/getUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
		logger.debug("->->calling method getUser");
		logger.debug("->->id->->" + userId);
		User user = userDAO.getUserById(userId);
		if (user == null) {
			logger.debug("->->->-> User does not exist wiht id" + userId);
			user = new User(); // To avoid NLP - NullPointerException
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		logger.debug("->->->-> User exist wiht id" + userId);
		logger.debug(user.getFirstName());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Accept User

	@RequestMapping(value = "/acceptUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> accept(@PathVariable("userId") String userId) {
		logger.debug("Starting of the method accept");

		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

		if (loggedInUserRole.equals("ROLE_ADMIN")) {
			user = updateStatus(userId, "A", "");
			logger.debug("Ending of the method accept");
		}

		else {
			user.setErrorCode("404");
			user.setErrorMessage("please login as admin to  ");
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	// Reject USer

	@RequestMapping(value = "/rejectUser/{userId}/{remarks}", method = RequestMethod.GET)
	public ResponseEntity<User> reject(@PathVariable("userId") String userId, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method reject");

		user = updateStatus(userId, "R", remarks);
		logger.debug("Ending of the method reject");
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	// Update Status

	private User updateStatus(String userId, String status, String remarks) {
		logger.debug("Starting of the method updateStatus");

		logger.debug("status: " + status);
		user = userDAO.getUserById(userId);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Could not update the status to " + status);
		} else {

			user.setStatus(status);
			user.setRemarks(remarks);

			userDAO.update(user);

			user.setErrorCode("200");
			user.setErrorMessage("Updated the status successfully");
		}
		logger.debug("Ending of the method updateStatus");
		return user;

	}

	// Login User

	@RequestMapping(value = "/login", method = RequestMethod.POST)

	public ResponseEntity<User> login(@RequestBody User user) {
		logger.debug("->->->->calling method authenticate");
		user = userDAO.validate(user.getUserId(), user.getPassword());
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials.  Please enter valid credentials");
			logger.debug("->->->->In Valid Credentials");

		} else

		{
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged in.");
			user.setIsOnline("Y");
			;
			logger.debug("->->->->Valid Credentials");
			/* session.setAttribute("loggedInUser", user); */
			session.setAttribute("loggedInUserId", user.getUserId());
			session.setAttribute("loggedInUserRole", user.getRole());

			logger.debug("You are loggin with the role : " + session.getAttribute("loggedInUserRole"));

			friendDAO.setOnline(user.getUserId());
			userDAO.setOnline(user.getUserId());
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Logout user

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {
		logger.debug("->->->->calling method logout");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");

		userDAO.update(user);

		friendDAO.setOffline(loggedInUserId);
		userDAO.setOffLine(loggedInUserId);

		session.invalidate();
		user = userDAO.getUserById(loggedInUserId);

		user.setErrorCode("200");
		user.setErrorMessage("You have successfully logged out");
		user.setStatus("N");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}