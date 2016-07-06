package com.tencentflea.dao.api;

import java.util.List;

import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;
import com.tencentflea.forms.User;

public interface ListDao {
	public List<Item> getBuyItemList(int offset, int location, int filterType);

	public List<Item> getSaleItemList(int offset, int location, int filterType);

	public List<Item> getItemSearchList(String keyword);

	public List<User> getUserSearchList(String keyword);

	public List<Comment> getCommentByRtxName(String rtxName);

	public List<Message> getMessageByRtxName(String rtxName);

	public int setMessageOld(String rtxName);
}