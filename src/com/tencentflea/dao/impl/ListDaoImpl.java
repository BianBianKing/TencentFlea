package com.tencentflea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import util.Constant;

import com.tencentflea.dao.api.ListDao;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;
import com.tencentflea.forms.User;

@Repository(value = "listDaoImpl")
public class ListDaoImpl implements ListDao {
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public List<Message> getMessageByRtxName(String rtxName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(
						"from Message message where message.actionRtxName=:rtxName order by messageDate desc")
				.setParameter("rtxName", rtxName);
		List<Message> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public List<Comment> getCommentByRtxName(String rtxName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(
						"from Comment comment where comment.actionRtxName=:rtxName order by messageDate desc")
				.setParameter("rtxName", rtxName);
		List<Comment> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public List<Item> getSaleItemList(int offset, int location, int filterType) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		// 按照releaseDate倒序
		// filterType=-1,则返回所有商品
		if (filterType == -1) {
			if (location == -1) {
				query = session
						.createQuery("select item from Item item where item.saleOrBuy="
								+ Constant.SALE_ITEM
								+ " order by releaseDate desc");
			} else {
				query = session
						.createQuery("select item from Item item, User user where item.saleOrBuy="
								+ Constant.SALE_ITEM
								+ " and item.rtxName=user.rtxName and user.workLocation="
								+ location + " order by releaseDate desc");
				query = session
						.createQuery("select item from Item item, User user where item.saleOrBuy="
								+ Constant.SALE_ITEM
								+ " and item.rtxName=user.rtxName and user.workLocation="
								+ location + " order by releaseDate desc");
			}
		} else {
			if (location == -1) {
				query = session
						.createQuery(
								"select item from Item item where item.saleOrBuy="
										+ Constant.SALE_ITEM
										+ " and item.itemType=:itemType order by releaseDate desc")
						.setParameter("itemType", filterType);
			} else {
				query = session
						.createQuery(
								"select item from Item item, User user where item.saleOrBuy="
										+ Constant.SALE_ITEM
										+ " and item.itemType=:itemType and item.rtxName=user.rtxName and user.workLocation="
										+ location
										+ " order by releaseDate desc")
						.setParameter("itemType", filterType);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(Constant.PAGE_SIZE);
		List<Item> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public List<Item> getBuyItemList(int startIndex, int location,
			int filterType) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		// 按照releaseDate倒序
		// filterType=-1,则返回所有商品
		if (filterType == -1) {
			if (location == -1) {
				query = session
						.createQuery("select item from Item item where item.saleOrBuy="
								+ Constant.BUY_ITEM
								+ " order by releaseDate desc");
			} else {
				query = session
						.createQuery("select item from Item item, User user where item.saleOrBuy="
								+ Constant.BUY_ITEM
								+ " and item.rtxName=user.rtxName and user.workLocation="
								+ location + " order by releaseDate desc");
				query = session
						.createQuery("select item from Item item, User user where item.saleOrBuy="
								+ Constant.BUY_ITEM
								+ " and item.rtxName=user.rtxName and user.workLocation="
								+ location + " order by releaseDate desc");
			}
		} else {
			if (location == -1) {
				query = session
						.createQuery(
								"select item from Item item where item.saleOrBuy="
										+ Constant.BUY_ITEM
										+ " and item.itemType=:itemType order by releaseDate desc")
						.setParameter("itemType", filterType);
			} else {
				query = session
						.createQuery(
								"select item from Item item, User user where item.saleOrBuy="
										+ Constant.BUY_ITEM
										+ " and item.itemType=:itemType and item.rtxName=user.rtxName and user.workLocation="
										+ location
										+ " order by releaseDate desc")
						.setParameter("itemType", filterType);
			}
		}
		query.setFirstResult(startIndex);
		query.setMaxResults(Constant.PAGE_SIZE);
		List<Item> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public List<Item> getItemSearchList(String keyword) {
		keyword = keyword.trim();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Item item where item.itemTitle like ? order by releaseDate desc";
		Query query = session.createQuery(hql);
		query.setString(0, "%" + keyword + "%");

		List<Item> itemSearchList = query.list();
		if (itemSearchList.isEmpty()) {
			return null;
		} else {
			return itemSearchList;
		}
	}

	// 根据关键字搜索商品
	@Override
	public List<User> getUserSearchList(String keyword) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User user where lower(user.rtxName) like ?";
		Query query = session.createQuery(hql);
		query.setString(0, "%" + keyword + "%");
		List<User> userSearchList = query.list();
		if (userSearchList.isEmpty()) {
			return null;
		} else {
			return userSearchList;
		}
	}

	@Override
	public int setMessageOld(String rtxName) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("update Message message set message.isNew="
							+ Constant.MESSAGE_TYPE_OLD
							+ " where message.actionRtxName=?");
			query.setString(0, rtxName);
			query.executeUpdate();
			return Constant.SUCCESS;
		} catch (Exception e) {
			return Constant.FAIL;
		}
	}
}