package com.niit.sociocode.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.sociocode.dao.JobDAO;
import com.niit.sociocode.model.Job;

@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public JobDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean insertJob(Job job) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Job> list() {
		return sessionFactory.getCurrentSession().createQuery("from Job").list();

	}

	public boolean deleteJob(int id) {
		try {
			sessionFactory.getCurrentSession().delete(getJobById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Job getJobById(int id) {
		return (Job) sessionFactory.getCurrentSession().get(Job.class, id);
	}

}