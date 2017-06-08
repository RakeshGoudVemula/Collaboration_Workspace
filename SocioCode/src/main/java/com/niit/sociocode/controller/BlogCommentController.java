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

import com.niit.sociocode.dao.BlogCommentDAO;
import com.niit.sociocode.model.Blog;
import com.niit.sociocode.model.BlogComment;

@RestController
public class BlogCommentController {

	@Autowired
	BlogCommentDAO blogCommentDAO;

	@Autowired
	BlogComment blogComment;

	@RequestMapping(value = "/getBlogComments", method = RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> list() {

		List<BlogComment> listblogComments = blogCommentDAO.list();

		return new ResponseEntity<List<BlogComment>>(listblogComments, HttpStatus.OK);
	}

	// insert the blogComment
	@RequestMapping(value = "/insertBlogComment", method = RequestMethod.POST)
	public ResponseEntity<String> addBlogComment(@RequestBody BlogComment blogComment) {

		blogComment.setBlogCommentDate(new Date());
		blogComment.setBlogId(102);
		blogComment.setUserId(102);
		blogComment.setUsername("rakhi");
		blogCommentDAO.insertBlogComment(blogComment);
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete blogcomment

	@RequestMapping(value = "/deleteBlogComment/{blogCommentId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBlogComment(@PathVariable("blogCommentId") int blogCommentId) {
		blogCommentDAO.deleteBlogComment(blogCommentId);
		return new ResponseEntity<String>("deleted Successfully", HttpStatus.OK);

	}

	// update blogComment

	@RequestMapping(value = "/updateBlogComment/{blogCommentId}", method = RequestMethod.PUT)
	public ResponseEntity<BlogComment> updateBlog(@PathVariable("blogCommentId") int blogCommentId, @RequestBody BlogComment blogComment) {
		BlogComment curr_blogcomment = blogCommentDAO.getBlogCommentById(blogCommentId);
		curr_blogcomment.setBlogComment(blogComment.getBlogComment());
		blogCommentDAO.insertBlogComment(curr_blogcomment);
		return new ResponseEntity<BlogComment>(curr_blogcomment, HttpStatus.OK);

	}
}