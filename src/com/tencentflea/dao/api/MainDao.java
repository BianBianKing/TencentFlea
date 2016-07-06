package com.tencentflea.dao.api;

import java.util.List;

import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;

public interface MainDao {
	public Item getItemByItemId(long itemId);

	public List<Comment> getCommentByItemId(long itemId);

	public int saveCollection(Collection collection);

	public int saveItem(Item item);

	public int updateItem(Item item);

	public int addCollectionNum(long itemId);

	public int deleteMessage(String rtxName, long itemId, int messageType);

	public int saveCommentItem(Comment comment);

	public int saveItemMessage(Message message);

	public int saveInquiryItem(Item item);

	public int updateOnShelfSate(Item item);
}
