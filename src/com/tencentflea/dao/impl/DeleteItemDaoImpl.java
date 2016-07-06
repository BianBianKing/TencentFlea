package com.tencentflea.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import util.Constant;
import com.tencentflea.dao.api.DeleteItemDao;

@Repository(value = "deleteItemDaoImpl")
public class DeleteItemDaoImpl implements DeleteItemDao {
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public int deleteItem(long itemID) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"delete from Item item where item.id=:itemID")
					.setParameter("itemID", itemID);
			query.executeUpdate();
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}

	@Override
	public int deleteComment(long commentID) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(
					"delete from Comment comment where comment.id=:commentID")
					.setParameter("commentID", commentID);
			query.executeUpdate();
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}
}
