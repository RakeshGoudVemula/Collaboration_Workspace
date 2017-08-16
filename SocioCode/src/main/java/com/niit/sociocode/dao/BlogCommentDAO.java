package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Blog;
import com.niit.sociocode.model.BlogComment;

public interface BlogCommentDAO {
	public boolean save(BlogComment blogComment);

	public boolean update(BlogComment blogComment);

	public boolean delete(int id);

	public BlogComment getBlogCommentById(int id);

	public List<BlogComment> getAllCommentsByBlogId(int blogId);

	public List<BlogComment> list();

	public int getMaxBlogCommentId();

}
