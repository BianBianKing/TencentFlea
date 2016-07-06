package com.tencentflea.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Constant;

import com.tencentflea.dao.api.MainDao;
import com.tencentflea.dao.api.UserDao;
import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Comment;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;
import com.tencentflea.forms.User;
import com.tencentflea.service.api.MainService;

@Transactional
@Service(value = "mainServiceImpl")
public class MainServiceImpl implements MainService {
	@Resource(name = "mainDaoImpl")
	MainDao mainDao;
	@Resource(name = "userDaoImpl")
	UserDao userDao;

	@Override
	public int saveCommentItem(Comment comment) {
		return mainDao.saveCommentItem(comment);
	}

	@Override
	public int saveItemMessage(long itemId, String rtxName, String content,
			int type) {
		// 通过itemId获取item对象
		Item item = mainDao.getItemByItemId(itemId);
		// 新建一个message
		Message message = new Message();
		message.setItemId(itemId);
		message.setMessageDate(new Date());
		message.setMessageContent(content);
		message.setRtxName(rtxName);
		message.setActionRtxName(item.getRtxName());
		message.setMessageType(type);
		message.setIsNew(Constant.MESSAGE_TYPE_NEW);
		// 保存message
		return mainDao.saveItemMessage(message);
	}

	@Override
	public int saveInquiryItem(User user, String itemTitle, int itemType,
			String description) {
		// 创建一个新的item
		Item item = new Item();
		// 设置item相关参数
		item.setRtxName(user.getRtxName());
		item.setItemTitle(itemTitle);
		item.setItemType(itemType);
		item.setItemDescription(description);
		item.setSaleOrBuy(Constant.BUY_ITEM);
		item.setReleaseDate(new Date());

		item.setOnShelf(Constant.ON_SHELF);
		// 保存item
		return mainDao.saveInquiryItem(item);
	}

	@Override
	public int saveOnShelfState(int itemid) {
		Item item = mainDao.getItemByItemId(itemid);
		int shelf = 1 - item.getOnShelf();// 设置的当前商品是出于上架还是下架
		item.setOnShelf(shelf);
		int res = mainDao.updateOnShelfSate(item);// 更新商品的上架/下架信息
		if (res == Constant.SUCCESS) {
			if (shelf == 0) {
				return Constant.ON_SHELF;
			} else if (shelf == 1) {
				return Constant.OFF_SHELF;
			} else {
				return Constant.FAIL_SHELF;
			}
		} else {
			return Constant.FAIL_SHELF;
		}
	}

	@Override
	public JSONObject getItemInfoByItemId(long itemId) throws Exception {
		JSONArray tempJsonArray = new JSONArray();
		JSONObject tempJsonObject = new JSONObject();
		JSONObject returnJson = new JSONObject();
		Item item = mainDao.getItemByItemId(itemId);
		List<Comment> commentList = mainDao.getCommentByItemId(itemId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (item != null) {
			returnJson.put("itemID", itemId);
			returnJson.put("itemTitle", item.getItemTitle());
			returnJson.put("price", item.getItemPrice());
			returnJson.put("itemType", item.getItemType());
			returnJson.put("description", item.getItemDescription());
			returnJson.put("collectionCount", item.getCollectionNum());
			// 处理UserInfo
			User user = userDao.getUserByRTX(item.getRtxName());
			returnJson.put("rtxName", user.getRtxName());
			returnJson.put("location", user.getWorkLocation());
			returnJson.put("profile", Constant.IMAGE_PATH + user.getIcon()
					+ ".jpg");
			returnJson.put("onShelf", item.getOnShelf());
			
			String date = sdf.format(item.getReleaseDate());
			returnJson.put("releaseDate", date);
			// 处理ImageUrl
			String picture = item.getImageUrl();
			if (picture != null) {
				String[] pictureArray = picture.split("\\*");
				for (String t : pictureArray) {
					tempJsonObject = new JSONObject();
					tempJsonObject.put("imageUrl", Constant.IMAGE_PATH + t
							+ ".jpg");
					tempJsonArray.put(tempJsonObject);
				}
			}
			returnJson.put("picture", tempJsonArray);
			// 处理comment
			tempJsonArray = new JSONArray();
			if (commentList != null) {
				for (Comment t : commentList) {
					user = userDao.getUserByRTX(t.getRtxName());
					tempJsonObject = new JSONObject();
					tempJsonObject.put("commentID", t.getId());
					tempJsonObject.put("rtxName", t.getRtxName());
					tempJsonObject.put("profile",
							Constant.IMAGE_PATH + user.getIcon() + ".jpg");
					tempJsonObject.put("content", t.getCommentContent());
					tempJsonObject.put("datetime",
							sdf.format(t.getCommentDate()));
					tempJsonArray.put(tempJsonObject);
				}

			}
			returnJson.put("commentList", tempJsonArray);
		} else {
			throw new Exception("Item not exist!");
		}
		return returnJson;
	}

	@Override
	public int saveCollection(Collection collection) {
		int state = Constant.FAIL;
		try {
			state = mainDao.saveCollection(collection);
			return state;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public int saveItem(Item item) {
		return mainDao.saveItem(item);
	}

	@Override
	public Item getItemByItemId(long itemId) {
		return mainDao.getItemByItemId(itemId);
	}

	@Override
	public int updateItem(Item item) {
		return mainDao.updateItem(item);
	}

	@Override
	public int deleteMessage(String rtxName, long itemId, int messageType) {
		return mainDao.deleteMessage(rtxName, itemId, messageType);
	}
}
