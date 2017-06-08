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

import com.niit.sociocode.dao.ForumDAO;
import com.niit.sociocode.model.Forum;

@RestController
public class ForumController {

	@Autowired
	ForumDAO forumDAO;

	@Autowired
	Forum forum;

	@RequestMapping(value = "/getForums", method = RequestMethod.GET)
	public ResponseEntity<List<Forum>> list() {

		List<Forum> listforums = forumDAO.list();

		return new ResponseEntity<List<Forum>>(listforums, HttpStatus.OK);

	}

	// insert the forum
	@RequestMapping(value = "/insertForum", method = RequestMethod.POST)
	public ResponseEntity<String> addForum(@RequestBody Forum forum) {
		forum.setCreateDate(new Date());
		forum.setStatus("NA");
		forum.setUserId(102);
		forumDAO.insertForum(forum);
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the forum
	@RequestMapping(value = "/deleteForum/{forumId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteForum(@PathVariable("forumId") int forumId) {

		forumDAO.deleteForum(forumId);

		return new ResponseEntity<String>("Forum Deleted Successfully", HttpStatus.OK);
	}

	// update the forum

	@RequestMapping(value = "/updateForum/{forumId}", method = RequestMethod.PUT)
	public ResponseEntity<Forum> updateForum(@PathVariable("forumId") int forumId, @RequestBody Forum forum) {
		Forum curr_forum = forumDAO.getForumById(forumId);
		curr_forum.setForumContent(forum.getForumContent());
		forumDAO.insertForum(curr_forum);
		return new ResponseEntity<Forum>(curr_forum, HttpStatus.OK);

	}

}
