package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.model.Friend;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean insertFriend(Friend friend) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(friend);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Friend> list() {
		return sessionFactory.getCurrentSession().createQuery("from Friend").list();
	}

	public boolean deleteFriend(int id) {
		try {
			sessionFactory.getCurrentSession().delete(getFriendById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Friend getFriendById(int id) {
		return (Friend) sessionFactory.getCurrentSession().get(Friend.class, id);
	}

}