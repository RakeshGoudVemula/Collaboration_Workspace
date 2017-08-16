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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.Friend;

@RestController
public class FriendController {
	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	Friend friend;

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserDAO userDAO;

	// getfriends
	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends() {
		logger.debug("->->->->calling method getMyFriends");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		List<Friend> myFriends = new ArrayList<Friend>();
		if (loggedInUserId == null) {
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);

		}

		logger.debug("getting friends of : " + loggedInUserId);
		myFriends = friendDAO.getMyFriends(loggedInUserId);

		if (myFriends.isEmpty()) {
			logger.debug("Friends does not exsit for the user : " + loggedInUserId);
			friend.setErrorCode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		logger.debug("Send the friend list ");
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}

	@RequestMapping(value = "/addFriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId) {
		logger.debug("->->->->calling method sendFriendRequest");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		logger.debug(loggedInUserId + " is sending request to " + friendId);
		friend.setUserId(loggedInUserId);
		friend.setFriendId(friendId);
		friend.setStatus("N"); // N - New, R->Rejected, A->Accepted
		friend.setIsOnline("N");
		// Is the user already sent the request previous?

		// check whether the friend exist in user table or not
		if (isUserExist(friendId) == false) {
			friend.setErrorCode("404");
			friend.setErrorMessage("No user exist with the id :" + friendId);
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}

		if (isRequestAlreadySent(friendId)) {
			friend.setErrorCode("404");
			friend.setErrorMessage("You already sent the friend request to " + friendId);

		} else {
			friendDAO.save(friend);

			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request successfull.." + friendId);
		}

		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	private boolean isUserExist(String id) {
		if (userDAO.getUserById(id) == null)
			return false;
		else
			return true;
	}

	private boolean isRequestAlreadySent(String friendId) {
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");

		if (friendDAO.get(loggedInUserId, friendId) == null)
			return false;
		else
			return true;
	}

	// Unfriend

	@RequestMapping(value = "/unFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId) {
		logger.debug("->->->->calling method unFriend");
		friend = updateRequest(friendId, "U");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	// Reject Friend
	@RequestMapping(value = "/rejectFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendFriendRequest(@PathVariable("friendId") String friendId) {
		logger.debug("->->->->calling method rejectFriendFriendRequest");
		friend = updateRequest(friendId, "R");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	// accept Friend

	@RequestMapping(value = "/acceptFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendFriendRequest(@PathVariable("friendId") String friendId) {
		logger.debug("->->->->calling method acceptFriendFriendRequest");

		friend = updateRequest(friendId, "A");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	private Friend updateRequest(String friendId, String status) {
		logger.debug("Starting of the method updateRequest");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		logger.debug("loggedInUserId : " + loggedInUserId);

		if (isRequestAlreadySent(friendId) == false) {
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist.  So you can not update to " + status);
		}

		if (status.equals("A") || status.equals("R"))
			friend = friendDAO.get(friendId, loggedInUserId);
		else
			friend = friendDAO.get(loggedInUserId, friendId);
		friend.setStatus(status); // N - New, R->Rejected, A->Accepted

		friendDAO.update(friend);

		friend.setErrorCode("200");
		friend.setErrorMessage(
				"Request from   " + friend.getUserId() + " To " + friend.getFriendId() + " has updated to :" + status);
		logger.debug("Ending of the method updateRequest");
		return friend;

	}

	// Get My Friend Requests

	@RequestMapping(value = "/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests() {
		logger.debug("->->->->calling method getMyFriendRequests");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		List<Friend> myFriendRequests = friendDAO.getNewFriendRequests(loggedInUserId);

		if (myFriendRequests.isEmpty()) {
			friend.setErrorCode("404");
			friend.setErrorMessage("You did not received any new requests");
			myFriendRequests.add(friend);
			return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);

		}
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);
	}

	@RequestMapping("/getRequestsSendByMe")
	public ResponseEntity<List<Friend>> getRequestsSendByMe() {
		logger.debug("->->->->calling method getRequestsSendByMe");
		String loggedInUserId = (String) httpSession.getAttribute("loggedInUserId");
		List<Friend> requestSendByMe = friendDAO.getRequestsSendByMe(loggedInUserId);
		logger.debug("No. of request send by you : " + requestSendByMe.size());
		if (requestSendByMe.isEmpty() || requestSendByMe.size() == 0) {
			friend.setErrorCode("404");
			friend.setErrorMessage("You did not send request to any body");
			requestSendByMe.add(friend);
			return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);

		}

		logger.debug("->->->->Ending method getRequestsSendByMe");
		return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);

	}

}