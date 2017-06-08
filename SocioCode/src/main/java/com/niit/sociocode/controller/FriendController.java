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

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.model.Friend;

@RestController
public class FriendController {

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	Friend friend;

	@RequestMapping(value = "/getFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> list() {

		List<Friend> listfriends = friendDAO.list();

		return new ResponseEntity<List<Friend>>(listfriends, HttpStatus.OK);

	}

	// insert the friend
	@RequestMapping(value = "/insertFriend", method = RequestMethod.POST)
	public ResponseEntity<String> addFriend(@RequestBody Friend friend) {

		friend.setStatus("NA");
		friend.setUserId(102);
		friendDAO.insertFriend(friend);
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the friend
	@RequestMapping(value = "/deleteFriend/{friendId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFriend(@PathVariable("friendId") int friendId) {

		friendDAO.deleteFriend(friendId);

		return new ResponseEntity<String>("Friend Deleted Successfully", HttpStatus.OK);
	}

	// update the friend

	@RequestMapping(value = "/updateFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> updateFriend(@PathVariable("friendId") int friendId) {
		Friend curr_friend = friendDAO.getFriendById(friendId);
		curr_friend.setStatus("NA");
		friendDAO.insertFriend(curr_friend);
		return new ResponseEntity<Friend>(curr_friend, HttpStatus.OK);

	}

}