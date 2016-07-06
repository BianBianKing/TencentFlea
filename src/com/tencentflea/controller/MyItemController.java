package com.tencentflea.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencentflea.forms.Item;
import com.tencentflea.service.api.MyItemSevice;

@Controller(value = "myItemController")
public class MyItemController {
	@Resource(name = "myItemServiceImpl")
	MyItemSevice myItemService;

	@RequestMapping(value = "app/myCollectionItemList")
	public void myCollectionItemList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			int offset = Integer.parseInt(request.getParameter("offset"));
			JSONArray itemJsonArray = myItemService
					.getMyCollectionItemListService(rtxName, offset);
			ret.put("state", 0);
			ret.put("info", "success");
			ret.put("itemList", itemJsonArray);
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
			ret.put("itemList", "");
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 获取自己的购买列表
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/myInquiryItemList")
	public void inquiryMyListAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxname = request.getParameter("rtxName");
			int offset = Integer.parseInt(request.getParameter("offset"));
			List<Item> items = myItemService.getMyItemList(rtxname, offset);
			JSONArray itemArray = new JSONArray();
			if (items != null) {
				for (Item tempItem : items) {
					JSONObject tempItemJson = new JSONObject();
					tempItemJson.put("itemID", tempItem.getId());
					tempItemJson.put("itemTitle", tempItem.getItemTitle());
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					tempItemJson.put("datetime",
							sdf.format(tempItem.getReleaseDate()));
					tempItemJson.put("commentCount",
							tempItem.getCollectionNum());
					tempItemJson.put("description",
							tempItem.getItemDescription());
					itemArray.put(tempItemJson);
				}
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", itemArray);
			} else {
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", itemArray);
			}
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			e.printStackTrace();
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 获取我买的商品列表
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */

	@RequestMapping(value = "app/mySellItemList")
	public void mySellItemList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			int offset = Integer.parseInt(request.getParameter("offset"));
			JSONArray itemJsonArray = myItemService.getMySellItemList(rtxName,
					offset);
			ret.put("state", 0);
			ret.put("info", "success");
			if (itemJsonArray == null) {
				ret.put("itemList", new JSONArray());
			} else {
				ret.put("itemList", itemJsonArray);
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
			ret.put("itemList", "");
		}
		response.getWriter().write(ret.toString());
	}
}