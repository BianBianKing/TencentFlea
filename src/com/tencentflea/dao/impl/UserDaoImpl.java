package com.tencentflea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import util.Constant;

import com.tencentflea.dao.api.UserDao;
import com.tencentflea.forms.User;

@Transactional
@Repository(value = "userDaoImpl")
public class UserDaoImpl implements UserDao {

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public User getUserByRTX(String rtxName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"from User user where user.rtxName=:rtxName").setParameter(
				"rtxName", rtxName);
		List<User> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	@Override
	public int saveUser(User user) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"from User user where user.rtxName=:rtxName").setParameter(
					"rtxName", user.getRtxName());
			List<User> resultList = query.list();
			if (resultList.isEmpty()) {
				session.save(user);
				return Constant.SUCCESS;
			} else {
				return Constant.FAIL;
			}
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}

	@Override
	public User getUserByUserId(long userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, userId);
		return user;
	}

	@Override
	public int updateUser(User user) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(user);
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}
}
