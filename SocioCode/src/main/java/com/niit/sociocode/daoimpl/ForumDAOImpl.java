package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.ForumDAO;
import com.niit.sociocode.model.Forum;

@Repository("forumDAO")
@Transactional
public class ForumDAOImpl implements ForumDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
		
	}
	
	public boolean insertForum(Forum forum) {
		try{
			sessionFactory.getCurrentSession().saveOrUpdate(forum);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public List<Forum> list() {
		
		return sessionFactory.getCurrentSession().createQuery("from Forum").list();
		
	}

	
	public boolean deleteForum(int id) {
		try{
			sessionFactory.getCurrentSession().delete(getForumById(id));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public Forum getForumById(int id) {
		
		return (Forum) sessionFactory.getCurrentSession().get(Forum.class, id);
		
	}

	
	
}
