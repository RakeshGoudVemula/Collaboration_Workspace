package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.BlogCommentDAO;
import com.niit.sociocode.model.BlogComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository("blogCommentDAO")
@Transactional
public class BlogCommentDAOImpl implements BlogCommentDAO {

	@Autowired
	SessionFactory sessionFactory;

	private static Logger log = LoggerFactory.getLogger(BlogCommentDAOImpl.class);

	public BlogCommentDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(BlogComment blogComment) {
		log.debug("Starting of method insertBlogComment");
		try {
			getCurrentSession().save(blogComment);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.debug("Ending of method insertBlogComment");
		return true;
	}

	public boolean update(BlogComment blogComment) {
		log.debug("Starting of method insertBlogComment");
		try {
			getCurrentSession().update(blogComment);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.debug("Ending of method insertBlogComment");
		return true;
	}

	public boolean delete(int id) {
		log.debug("Starting of method deleteBlogComment");
		try {
			getCurrentSession().delete(getBlogCommentById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.debug("Ending of method deleteBlogComment");
		return true;
	}

	public BlogComment getBlogCommentById(int id) {
		log.debug("Starting of method getBlogCommentById");
		log.debug("Ending of method getBlogCommentById");
		return (BlogComment) getCurrentSession().get(BlogComment.class, id);
	}

	public List<BlogComment> getAllCommentsByBlogId(int blogid) {
		log.debug("Starting of method getAllComments in Blog Comment");
		log.debug("Ending of method getAllComments in Blog Comment");
		return getCurrentSession().createCriteria(BlogComment.class).add(Restrictions.eq("blogId", blogid)).list();
	}

	public List<BlogComment> list() {
		log.debug("Starting of method list in Blog Comment");
		log.debug("Ending of method list in Blog Comment");
		return getCurrentSession().createCriteria(BlogComment.class).list();
	}

	public int getMaxBlogCommentId() {
		return (Integer) getCurrentSession().createQuery("select max(blogCommentId) from BlogComment").uniqueResult();
	}

}
