package com.tencentflea.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencentflea.dao.api.DeleteItemDao;
import com.tencentflea.service.api.DeleteItemService;

@Transactional
@Service(value = "deleteItemServiceImpl")
public class DeleteItemServiceImpl implements DeleteItemService {
	@Resource(name = "deleteItemDaoImpl")
	DeleteItemDao deleteItemDao;

	@Override
	public int deleteItem(long itemID) {
		return deleteItemDao.deleteItem(itemID);
	}

	@Override
	public int deleteComment(long commentID) {
		return deleteItemDao.deleteComment(commentID);
	}
}
