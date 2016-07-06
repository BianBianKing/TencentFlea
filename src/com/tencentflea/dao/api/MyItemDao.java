package com.tencentflea.dao.api;

import java.util.List;

import com.tencentflea.forms.Collection;
import com.tencentflea.forms.Item;

public interface MyItemDao {
	public List<Item> getMyItemList(String rtxname, int startIndex);

	public List<Item> getMySellItemList(String rtxName, int offset);

	public List<Collection> getMyCollectionItemList(String rtxName, int offset);

	public Item getItemItemID(long itemID);
}