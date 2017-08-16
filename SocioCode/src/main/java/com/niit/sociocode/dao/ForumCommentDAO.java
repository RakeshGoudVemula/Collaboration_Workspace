package com.niit.sociocode.dao;

import java.util.List;

import com.niit.sociocode.model.Forum;
import com.niit.sociocode.model.ForumComment;

public interface ForumCommentDAO {

	public boolean save(ForumComment forumComment);

	public boolean update(ForumComment forumComment);

	public boolean delete(int id);

	public ForumComment getForumCommentById(int id);

	public List<ForumComment> list();

	public List<ForumComment> getAllCommentsByForumId(int forumid);

	public int getMaxForumCommentId();
}
