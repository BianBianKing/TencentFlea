package com.tencentflea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import util.Constant;

import com.tencentflea.dao.api.MyItemDao;
import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Item;

@Repository(value = "myItemDaoImpl")
public class MyItemDaoImpl implements MyItemDao {
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public List<Collection> getMyCollectionItemList(String rtxName, int offset) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"from Collection collection where collection.rtxName=:rtxName")
				.setParameter("rtxName", rtxName);
		query.setFirstResult(offset);
		query.setMaxResults(Constant.PAGE_SIZE);
		List<Collection> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}

	@Override
	public Item getItemItemID(long itemId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"from Item item where item.id=:itemId").setParameter("itemId",
				itemId);
		List<Item> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	@Override
	public List<Item> getMySellItemList(String rtxName, int offset) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(
						"from Item item where item.saleOrBuy="
								+ Constant.SALE_ITEM
								+ "and item.rtxName=:rtxName  order by releaseDate desc")
				.setParameter("rtxName", rtxName);
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
	public List<Item> getMyItemList(String rtxname, int startIndex) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(
						"from Item item where item.saleOrBuy="
								+ Constant.BUY_ITEM
								+ " and  item.rtxName=:rtxname  order by releaseDate desc")
				.setParameter("rtxname", rtxname);
		query.setFirstResult(startIndex);
		query.setMaxResults(Constant.PAGE_SIZE);
		List<Item> resultList = query.list();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList;
		}
	}
}