package com.tencentflea.service.api;

import org.json.JSONArray;

public interface SellItemService {
	public JSONArray getItemList(int offset,int location,int filterType);
}
