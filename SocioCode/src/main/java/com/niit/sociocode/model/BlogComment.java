package com.niit.sociocode.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table
public class BlogComment extends BaseDomain{
	@Id
	private int blogCommentId;
	private int blogId;
	private String blogComment;
	private Date blogCommentDate;
	private String userId;
	private String userName;

	public int getBlogCommentId() {
		return blogCommentId;
	}

	public void setBlogCommentId(int blogCommentId) {
		this.blogCommentId = blogCommentId;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getBlogComment() {
		return blogComment;
	}

	public void setBlogComment(String blogComment) {
		this.blogComment = blogComment;
	}

	public Date getBlogCommentDate() {
		return blogCommentDate;
	}

	public void setBlogCommentDate(Date blogCommentDate) {
		this.blogCommentDate = blogCommentDate;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

}