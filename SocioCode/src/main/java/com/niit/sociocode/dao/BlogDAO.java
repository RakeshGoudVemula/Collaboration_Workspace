package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Blog;

public interface BlogDAO
{
	
	public boolean insertBlog(Blog blog);
	
	public List<Blog> list();
	
	public boolean deleteBlog(int id);
	
	public Blog getBlogById(int id);

}
