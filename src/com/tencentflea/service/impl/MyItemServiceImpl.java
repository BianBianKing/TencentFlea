package com.tencentflea.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Constant;

import com.tencentflea.dao.api.MainDao;
import com.tencentflea.dao.api.MyItemDao;
import com.tencentflea.dao.api.UserDao;
import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Item;
import com.tencentflea.service.api.MyItemSevice;

@Transactional
@Service(value = "myItemServiceImpl")
public class MyItemServiceImpl implements MyItemSevice {
	@Resource(name = "mainDaoImpl")
	MainDao mainDao;
	@Resource(name = "userDaoImpl")
	UserDao userDao;
	@Resource(name = "myItemDaoImpl")
	MyItemDao myItemDao;

	@Override
	public JSONArray getMyCollectionItemListService(String rtxName, int offset) throws Exception {
		try {

			JSONObject itemJson = new JSONObject();
			JSONArray itemJsonArray = new JSONArray();
			List<Collection> collections = myItemDao.getMyCollectionItemList(
					rtxName, offset);
			if (collections != null) {
				for (Collection collection : collections) {
					itemJson = new JSONObject();
					// 处理item信息
					itemJson.put("itemID", collection.getItemId());

					// 处理date信息
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					itemJson.put("datetime",
							sdf.format(collection.getCollectionDate()));
					// 处理item
					Item item = myItemDao.getItemItemID(collection.getItemId());
					itemJson.put("itemTitle", item.getItemTitle());
					itemJson.put("description", item.getItemDescription());
					itemJson.put("price", item.getItemPrice());
					itemJson.put("collectionCount", item.getCollectionNum());
					itemJson.put("onShelf", item.getOnShelf());
					// 处理pitcure
					String picture = item.getImageUrl();
					JSONArray pictureJsonArray = new JSONArray();
					if (picture != null) {
						String[] pictureArray = picture.split("\\*");
						for (String tempPicture : pictureArray) {
							JSONObject pictureJsonObject = new JSONObject();
							pictureJsonObject.put("imageUrl", Constant.IMAGE_PATH
									+ tempPicture + ".jpg");
							pictureJsonArray.put(pictureJsonObject);
						}

					}
					itemJson.put("picture", pictureJsonArray);
					itemJsonArray.put(itemJson);
				}
			}
			return itemJsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("erro");
		}
	}

	@Override
	public List<Item> getMyItemList(String rtxname, int offset)
			throws Exception {
		return myItemDao.getMyItemList(rtxname, offset);
	}

	@Override
	public JSONArray getMySellItemList(String rtxName, int offset) {
		try {
			JSONArray tempJsonArray = new JSONArray();
			JSONObject tempJsonObject = new JSONObject();
			JSONObject itemJson = new JSONObject();
			JSONArray itemJsonArray = new JSONArray();
			List<Item> items = myItemDao.getMySellItemList(rtxName, offset);
			for (Item item : items) {
				itemJson = new JSONObject();
				tempJsonArray = new JSONArray();
				// 处理item信息
				itemJson.put("itemID", item.getId());
				itemJson.put("itemTitle", item.getItemTitle());
				itemJson.put("price", item.getItemPrice());
				itemJson.put("location", userDao
						.getUserByRTX(item.getRtxName()).getWorkLocation());
				itemJson.put("onShelf", item.getOnShelf());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				itemJson.put("releaseDate", sdf.format(item.getReleaseDate()));
				itemJson.put("description", item.getItemDescription());
				itemJson.put("collectionCount", item.getCollectionNum());
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
}
