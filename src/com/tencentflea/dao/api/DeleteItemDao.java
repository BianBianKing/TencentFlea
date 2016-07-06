package com.tencentflea.dao.api;

public interface DeleteItemDao {
	public int deleteItem(long itemID);

	public int deleteComment(long commentID);
}
