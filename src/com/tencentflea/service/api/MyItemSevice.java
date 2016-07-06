package com.tencentflea.service.api;

import java.util.List;

import org.json.JSONArray;

import com.tencentflea.forms.Item;

public interface MyItemSevice {
	public List<Item> getMyItemList(String rtxname, int startIndex)
			throws Exception;

	public JSONArray getMySellItemList(String rtxName, int offset);

	public JSONArray getMyCollectionItemListService(String rtxName, int offset) throws Exception ;
}
