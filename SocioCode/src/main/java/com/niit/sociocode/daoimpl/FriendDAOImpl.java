package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.FriendDAO;
import com.niit.sociocode.model.Friend;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	private static Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<Friend> getMyFriends(String userId) {
		String hql1 = "select friendId  from Friend where userId='" + userId + "' and status = 'A' ";

		String hql2 = "select userId from Friend where friendId='" + userId + "' and status = 'A'";

		log.debug("getMyFriends hql1 : " + hql1);
		log.debug("getMyFriends hql2 : " + hql2);

		@SuppressWarnings("unchecked")
		List<Friend> list1 = sessionFactory.openSession().createQuery(hql1).list();
		@SuppressWarnings("unchecked")
		List<Friend> list2 = sessionFactory.openSession().createQuery(hql2).list();

		list1.addAll(list2);

		return list1;

	}

	public Friend get(String userId, String friendId) {
		String hql = "from Friend where userId=" + "'" + userId + "' and friendId= '" + friendId + "'";

		log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();
	}

	@Transactional
	public boolean save(Friend friend) {
		try {
			log.debug("*********************Previous id " + getMaxId());
			friend.getUserId();
			log.debug("***********************generated id:" + getMaxId());
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(Friend friend) {
		try {
			log.debug("Starting of the method update");
			log.debug("user Id : " + friend.getUserId() + " Friend id :" + friend.getFriendId());
			sessionFactory.getCurrentSession().update(friend);
			log.debug("Successfully updated");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.debug("Not able to update the status");
			return false;
		}

	}

	@Transactional
	public boolean delete(String userId, String friendId) {
		log.debug("Starting of delete friend  method");
		Friend friend = get(userId, friendId);
		if (friend == null) {
			log.debug("No such friend exist");
			return false;
		}
		try {
			getCurrentSession().delete(friend);
		} catch (Exception e) {
			log.debug("Exception arised");
			e.printStackTrace();
			return false;
		}
		log.debug("Starting of delete friend method");
		return true;

	}

	public List<Friend> getNewFriendRequests(String friendId) {
		String hql = "select userId from Friend where friendId=" + "'" + friendId + "' and status ='" + "N'";

		log.debug(hql);
		return sessionFactory.openSession().createQuery(hql).list();

	}

	public void setOnline(String friendId) {
		getCurrentSession().createQuery("UPDATE Friend SET isOnline = 'Y' WHERE friendId = ?").setString(0, friendId)
				.executeUpdate();
	}

	public void setOffline(String friendId) {
		getCurrentSession().createQuery("UPDATE Friend SET isOnline = 'N' WHERE friendId = ?").setString(0, friendId)
				.executeUpdate();
	}

	private int getMaxId() {
		log.debug("Starting of method getMaxId");
		int maxId;
		try {
			maxId = (Integer) getCurrentSession().createQuery("select max(id) from Friend").uniqueResult();
			log.debug("Got max Id : " + maxId);
		} catch (Exception e) {
			log.debug("Exception arised! There may not be any rows in table. Returning 0 as starting value");
			e.printStackTrace();
			return 0;
		}
		log.debug("Returning max Id");
		return maxId;

	}

	@SuppressWarnings("unchecked")
	public List<Friend> getRequestsSendByMe(String userId) {
		String hql = "select friendId from Friend where userId=" + "'" + userId + "' and status ='" + "N'";

		log.debug(hql);
		return sessionFactory.openSession().createQuery(hql).list();

	}
}