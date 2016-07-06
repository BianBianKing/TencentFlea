package com.tencentflea.service.api;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.tencentflea.forms.Item;
import com.tencentflea.forms.User;

public interface ListService {
	public List<Item> getBuyItemList(int offset, int location, int filterType)throws Exception;
	public List<User> getUserSearchList(String keyword) throws Exception;
	public List<Item> getItemSearchList(String keyword) throws Exception;
	public JSONArray getCommentMessageByRtxName(String rtxName) throws JSONException ;
	public JSONArray getCollectionMessageByRtxName(String rtxName) throws JSONException;
	public JSONArray getSaleItemList(int offset,int location,int filterType);
	public int setMessageOld(String rtxName);
}