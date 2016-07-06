package com.tencentflea.service.api;

import org.json.JSONObject;

import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.User;

public interface MainService {
	public JSONObject getItemInfoByItemId(long itemId) throws Exception;

	public Item getItemByItemId(long itemId);

	public int saveCollection(Collection collection);

	public int saveItem(Item item);

	public int updateItem(Item item);

	public int deleteMessage(String rtxName, long itemId, int messageType);

	public int saveCommentItem(Comment comment);

	public int saveItemMessage(long itemId, String rtxName, String content,
			int type);

	public int saveInquiryItem(User user, String itemTitle, int itemType,
			String description);

	public int saveOnShelfState(int itemid);
}
