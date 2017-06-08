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

import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.User;

@RestController
public class UserController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> list() {

		List<User> listusers = userDAO.list();

		return new ResponseEntity<List<User>>(listusers, HttpStatus.OK);

	}

	// insert the user
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user) {

		user.setRole("ROLE_USER");
		user.setStatus("NA");
		user.setIsOnline("False");

		userDAO.insertUser(user);
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the user
	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) {

		userDAO.deleteUser(userId);

		return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
	}

	// update the user

	  @RequestMapping(value = "/updateUser/{userId}", method =
	  RequestMethod.PUT) public ResponseEntity<User>
	  updateUser(@PathVariable("userId") int userId, @RequestBody User user) {
	  User curr_user = userDAO.getUserById(userId);
	  curr_user.setUserId(user.getUserId());
	  userDAO.insertUser(curr_user); return new ResponseEntity<User>(curr_user,
	  HttpStatus.OK); }
	 

}