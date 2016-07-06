package com.tencentflea.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Constant;

import com.tencentflea.dao.api.ListDao;
import com.tencentflea.dao.api.MainDao;
import com.tencentflea.dao.api.UserDao;
import com.tencentflea.forms.Item;
import com.tencentflea.forms.Message;
import com.tencentflea.forms.User;
import com.tencentflea.service.api.ListService;

@Transactional
@Service(value = "listServiceImpl")
public class ListServiceImpl implements ListService {
	@Resource(name = "listDaoImpl")
	ListDao listDao;
	@Resource(name = "mainDaoImpl")
	MainDao mainDao;
	@Resource(name = "userDaoImpl")
	UserDao userDao;

	@Override
	public JSONArray getSaleItemList(int offset, int location, int filterType) {
		try {
			JSONArray tempJsonArray = new JSONArray();
			JSONObject tempJsonObject = new JSONObject();
			JSONObject itemJson = new JSONObject();
			JSONArray itemJsonArray = new JSONArray();
			List<Item> items = listDao.getSaleItemList(offset, location, filterType);
			for (Item item : items) {
				itemJson = new JSONObject();
				tempJsonArray = new JSONArray();
				// 处理item信息
				itemJson.put("itemID", item.getId());
				itemJson.put("itemTitle", item.getItemTitle());
				itemJson.put("price", item.getItemPrice());
				;
				itemJson.put("location", userDao
						.getUserByRTX(item.getRtxName()).getWorkLocation());
				// 处理date信息
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				itemJson.put("releaseDate", sdf.format(item.getReleaseDate()));
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
					itemJson.put("picture", tempJsonArray);
				} else {
					itemJson.put("picture", tempJsonArray);
				}
				itemJsonArray.put(itemJson);
			}
			return itemJsonArray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONArray getCommentMessageByRtxName(String rtxName)
			throws JSONException {
		List<Message> messageList = listDao.getMessageByRtxName(rtxName);
		JSONObject tempJsonObject = null;
		JSONArray tempJsonArray = new JSONArray();
		if (messageList != null) {
			for (Message t : messageList) {
				if (t.getMessageType() == Constant.COMMENT_TYPE) {
					tempJsonObject = new JSONObject();
					Item item = mainDao.getItemByItemId(t.getItemId());
					tempJsonObject.put("itemTitle", item.getItemTitle());
					tempJsonObject.put("itemPrice", item.getItemPrice());
					tempJsonObject
							.put("description", item.getItemDescription());
					// profile
					String picture = item.getImageUrl();
					JSONArray pictureJsonArray = new JSONArray();
					if (picture != null) {
						String[] pictureArray = picture.split("\\*");
						for (String tempPicture : pictureArray) {
							JSONObject pictureJsonObject = new JSONObject();
							pictureJsonObject.put("imageUrl",
									Constant.IMAGE_PATH + tempPicture + ".jpg");
							pictureJsonArray.put(pictureJsonObject);
						}
					}
					tempJsonObject.put("picture", pictureJsonArray);
					// saleOrBuy
					tempJsonObject.put("saleOrBuy", item.getSaleOrBuy());
					// otherInfo
					tempJsonObject.put("itemID", t.getItemId());
					tempJsonObject.put("rtxName", t.getRtxName());
					tempJsonObject.put("profile", Constant.IMAGE_PATH
							+ userDao.getUserByRTX(t.getRtxName()).getIcon() + ".jpg");
					tempJsonObject.put("content", t.getMessageContent());
					tempJsonObject.put("isNew", t.getIsNew());
					// 设置日期格式
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					String date = sdf.format(t.getMessageDate());
					tempJsonObject.put("datetime", date);
					tempJsonArray.put(tempJsonObject);
				}
			}
		}
		return tempJsonArray;
	}

	@Override
	public JSONArray getCollectionMessageByRtxName(String rtxName)
			throws JSONException {
		List<Message> messageList = listDao.getMessageByRtxName(rtxName);
		JSONObject tempJsonObject = null;
		JSONArray tempJsonArray = new JSONArray();
		if (messageList != null) {
			for (Message t : messageList) {
				if (t.getMessageType() == Constant.COLLECTION_TYPE) {
					tempJsonObject = new JSONObject();
					tempJsonObject.put("itemID", t.getItemId());
					Item item = mainDao.getItemByItemId(t.getItemId());
					tempJsonObject.put("itemTitle", item.getItemTitle());
					tempJsonObject.put("itemPrice", item.getItemPrice());
					tempJsonObject
							.put("description", item.getItemDescription());
					// profile
					String picture = item.getImageUrl();
					JSONArray pictureJsonArray = new JSONArray();
					if (picture != null) {
						String[] pictureArray = picture.split("\\*");
						for (String tempPicture : pictureArray) {
							JSONObject pictureJsonObject = new JSONObject();
							pictureJsonObject.put("imageUrl",
									Constant.IMAGE_PATH + tempPicture + ".jpg");
							pictureJsonArray.put(pictureJsonObject);
						}
					}
					tempJsonObject.put("picture", pictureJsonArray);
					tempJsonObject.put("rtxName", t.getRtxName());
					tempJsonObject.put("profile", Constant.IMAGE_PATH
							+ userDao.getUserByRTX(t.getRtxName()).getIcon() + ".jpg");
					tempJsonObject.put("content", t.getMessageContent());
					tempJsonObject.put("isNew", t.getIsNew());
					// 设置日期格式
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					String date = sdf.format(t.getMessageDate());
					tempJsonObject.put("datetime", date);
					tempJsonArray.put(tempJsonObject);
				}
			}
		}
		return tempJsonArray;
	}

	@Override
	public List<Item> getBuyItemList(int offset, int location, int filterType)
			throws Exception {
		return listDao.getBuyItemList(offset, location, filterType);
	}

	@Override
	public List<User> getUserSearchList(String keyword) {
		return listDao.getUserSearchList(keyword);
	}

	@Override
	public List<Item> getItemSearchList(String keyword) {
		return listDao.getItemSearchList(keyword);
	}

	@Override
	public int setMessageOld(String rtxName) {
		return listDao.setMessageOld(rtxName);
	}
}
