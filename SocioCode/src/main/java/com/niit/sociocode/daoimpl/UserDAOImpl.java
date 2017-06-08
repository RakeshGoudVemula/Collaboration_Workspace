package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.UserDAO;
import com.niit.sociocode.model.User;

@Repository("userDao")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public boolean insertUser(User user) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	
	public List<User> list() {

		return sessionFactory.getCurrentSession().createQuery("from User").list();

	}

	
	public boolean deleteUser(int id) {
		try {
			sessionFactory.getCurrentSession().delete(getUserById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public User getUserById(int id) {

		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	
	public boolean validate(String emailId, String password) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from usertable where emailId=? and password=?");
		query.setString(0, emailId);
		query.setString(1, password);
		
		if(query.uniqueResult()== null)
		{
			return false;
			//if no row exist i.e., invalid credentials
		}
		else
		{
			//if row exist i.e., valid credentials
			return true;
		}
	}

}