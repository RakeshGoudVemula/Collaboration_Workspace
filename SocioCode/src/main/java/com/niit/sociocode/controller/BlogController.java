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

import com.niit.sociocode.dao.BlogDAO;
import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.Blog;
import com.niit.sociocode.model.User;

@RestController
public class BlogController {

	private static Logger logger = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	BlogDAO blogDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	User user;
	@Autowired
	HttpSession session;
	@Autowired
	Blog blog;

	// get all the blogs
	@RequestMapping(value = "/getAllBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getAllBlogs() {
		logger.debug("---calling method getAllBlogs");
		logger.debug("---Starting getAllBlogs method");
		return new ResponseEntity<List<Blog>>(blogDAO.list(), HttpStatus.OK);
	}

	// insert the blog
	@RequestMapping(value = "/insertBlog", method = RequestMethod.POST)
	public ResponseEntity<String> addBlog(@RequestBody Blog blog) {
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			blog.setCreateDate(new Date());

			blog.setLikes(0);
			blog.setStatus("N");
			if (blogDAO.save(blog)) {
				logger.debug("---blog is created with" + blog.getBlogId());
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog created successfully");
			} else {
				logger.debug("----blog is not created with" + blog.getBlogId());
				blog.setErrorCode("404");
				blog.setErrorMessage("Blog not created");
			}
		} else {
			logger.debug("user not loggedin---***");
			blog.setErrorCode("404");
			blog.setErrorMessage("Please login to create blog");
		}
		return new ResponseEntity<String>("Successfully inserted", HttpStatus.OK);

	}

	// delete the blog
	@RequestMapping(value = "/deleteBlog/{blogId}", method = RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlogById(blogId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			if (loggedInUserRole.equals("ROLE_ADMIN") || blog.getUserId().equals(loggedInUserId)) {
				if (blogDAO.delete(blogId)) {
					// blog = new Blog();
					blog.setErrorCode("200");
					blog.setErrorMessage("Delete successfull");
				} else {
					blog.setErrorCode("404");
					blog.setErrorMessage("Delete falied");
				}
			} else {
				blog.setErrorCode("404");
				blog.setErrorMessage("Not Authorized!");
			}
		} else {
			blog.setErrorCode("404");
			blog.setErrorMessage("Please Login to perform this task!");
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// update the blog

	@RequestMapping(value = "/updateBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable("blogId") int blogId, @RequestBody Blog blog) {

		Blog curr_blog = blogDAO.getBlogById(blogId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			if (loggedInUserRole.equals("ROLE_ADMIN") || (curr_blog.getUserId()).equals(loggedInUserId)) {
				curr_blog.setBlogName(blog.getBlogName());
				curr_blog.setBlogContent(blog.getBlogContent());
				if (blogDAO.update(curr_blog)) {
					curr_blog.setErrorCode("200");
					curr_blog.setErrorMessage("Updated successfull");
				} else {
					curr_blog.setErrorCode("404");
					curr_blog.setErrorMessage("Not updated successfully");
				}
			} else {
				curr_blog.setErrorCode("404");
				curr_blog.setErrorMessage("Not Authorized!");
			}
		} else {
			curr_blog.setErrorCode("404");
			curr_blog.setErrorMessage("please login to do this operation");
		}
		return new ResponseEntity<Blog>(curr_blog, HttpStatus.OK);
	}

	// approve blog
	@RequestMapping(value = "/approveBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> approveBlog(@PathVariable("blogId") int blogId) {
		logger.debug("calling method approve blog");
		Blog blog = blogDAO.getBlogById(blogId);
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {

			if (loggedInUserRole.equals("ROLE_ADMIN")) {

				if (updateStatus(blog, "A", "Approved")) {
					blog.setErrorCode("200");
					blog.setErrorMessage("successfully updated the blog");
				} else {
					blog.setErrorCode("404");
					blog.setErrorMessage("Not updated successfully");
				}
			} else {
				blog.setErrorCode("404");
				blog.setErrorMessage("Not Authorized");
			}
		} else {
			blog.setErrorCode("404");
			blog.setErrorMessage("Please login to continue");
		}
		logger.debug("ending method of approveblog");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// reject blog
	@RequestMapping(value = "/rejectBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> rejectBlog(@PathVariable("blogId") int blogId) {
		logger.debug("----calling method reject method");
		Blog blog = blogDAO.getBlogById(blogId);
		logger.debug("getting the loggedinuserRole from session");
		String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
		logger.debug("getting the loggedinuserId from session");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if (loggedInUserId != null) {
			logger.debug("loggedInUserId is not null");
			if (loggedInUserRole.equals("ROLE_ADMIN") || (blog.getUserId()).equals(loggedInUserId)) {
				if (updateStatus(blog, "R", "Rejected")) {
					logger.debug("The blog is rejected successfully" + blog.getUserId());
					blog.setErrorCode("200");
					blog.setErrorMessage("successfully rejected");
				} else {
					logger.debug("failed to reject successfully");
					blog.setErrorCode("404");
					blog.setErrorMessage("failed to reject successfully");
				}
			} else {
				logger.debug("the user is not authorized");
				blog.setErrorCode("404");
				blog.setErrorMessage("you are not authorized");
			}
		} else {
			logger.debug("you have to login to reject the blog");
			blog.setErrorCode("404");
			blog.setErrorMessage("please login to do this operation");
		}
		logger.debug("--------ending of the method reject blog");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	private boolean updateStatus(Blog blog, String status, String remarks) {
		logger.debug("In changeStatus private method");
		blog.setStatus(status);
		blog.setRemarks(remarks);
		return blogDAO.update(blog);
	}

	// getApprovedBlogs
	@RequestMapping(value = "/getApprovedBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getApprovedBlogs() {
		logger.debug("---calling method getApprovedBlogs");
		logger.debug("---Starting getBlogs method");
		return new ResponseEntity<List<Blog>>(blogDAO.list("A"), HttpStatus.OK);
	}

	// getNewBlogs
	@RequestMapping(value = "/getNewBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getNewBlogs() {

		logger.debug("in /getNewBlogs");
		logger.debug("  Starting getNewBlogs method");
		return new ResponseEntity<List<Blog>>(blogDAO.list("N"), HttpStatus.OK);
	}

	// getting the rejected blogs
	@RequestMapping(value = "/getRejectedBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getRejectedBlogs() {
		logger.info(" ");
		logger.debug("---calling method getRejectedBlogs");
		logger.debug("---Starting of the method getRejectedBlogs");
		return new ResponseEntity<List<Blog>>(blogDAO.list("R"), HttpStatus.OK);
	}
}
