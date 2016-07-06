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

import com.tencentflea.dao.api.MainDao;
import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;

@Transactional
@Repository(value = "mainDaoImpl")
public class MainDaoImpl implements MainDao {
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	/**
	 * 获取Item
	 */
	@Override
	public Item getItemByItemId(long itemId) {
		Session session = sessionFactory.getCurrentSession();
		Item item = (Item) session.get(Item.class, itemId);
		return item;
	}

	/**
	 * 获取Item对应的Comment
	 */
	@Override
	public List<Comment> getCommentByItemId(long itemId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"from Comment comment where comment.itemId=:itemId").setParameter(
				"itemId", itemId);
		List<Comment> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public int saveCollection(Collection collection) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from Collection collection where collection.rtxName=? and collection.itemId=?");
			query.setString(0, collection.getRtxName());
			query.setLong(1, collection.getItemId());
			List<Collection> resultList = query.list();
			Item item = (Item) session.get(Item.class, collection.getItemId());
			int returnState = Constant.COLLECT_FAIL;
			if (resultList.isEmpty()) {
				session.save(collection);
				item.setCollectionNum(item.getCollectionNum() + 1);
				session.save(item);
				returnState = Constant.COLLECT_SUCCESS;
			} else {
				Collection col = (Collection) session.get(Collection.class,
						resultList.get(0).getId());
				session.delete(col);
				item.setCollectionNum(item.getCollectionNum() - 1);
				session.save(item);
				returnState = Constant.UNCOLLECT_SUCCESS;
			}
			return returnState;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.COLLECT_FAIL;
	}

	@Override
	public int deleteMessage(String rtxName, long itemId, int messageType) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("delete Message as message where message.rtxName=? and message.messageType=? and message.itemId=?");
			query.setString(0, rtxName);
			query.setInteger(1, messageType);
			query.setLong(2, itemId);
			query.executeUpdate();
			return Constant.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.FAIL;
		}
	}

	@Override
	public int saveItem(Item item) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(item);
			return Constant.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.FAIL;
	}

	@Override
	public int updateItem(Item item) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(item);
			return Constant.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.FAIL;
	}

	@Override
	public int addCollectionNum(long itemId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Item item = (Item) session.get(Item.class, itemId);
			item.setCollectionNum(item.getCollectionNum() + 1);
			session.save(item);
			return Constant.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.FAIL;
	}

	@Override
	public int saveCommentItem(Comment comment) {
		try {
			// 获取当前会话
			Session session = sessionFactory.getCurrentSession();
			// 保存comment
			session.save(comment);
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}

	}

	@Override
	public int saveItemMessage(Message message) {
		try {
			// 获取当前会话
			Session session = sessionFactory.getCurrentSession();
			// 存储message
			session.save(message);
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}

	@Override
	public int saveInquiryItem(Item item) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(item);
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}

	@Override
	public int updateOnShelfSate(Item item) {
		try {
			// 获取当前会话
			Session session = sessionFactory.getCurrentSession();
			// 更新item
			session.update(item);
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}
}
