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
import com.tencentflea.service.api.ListService;
import com.tencentflea.service.api.UserService;

@Controller(value = "listController")
public class ListController {
	@Resource(name = "listServiceImpl")
	ListService listService;
	@Resource(name = "userServiceImpl")
	UserService userService;

	/**
	 * 获取商品列表
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/inquiryList")
	public void inquiryListAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			int offset = Integer.parseInt(request.getParameter("offset"));
			int location = Integer.parseInt(request.getParameter("location"));
			int filterType = Integer.parseInt(request
					.getParameter("filterType"));
			List<Item> items = listService.getBuyItemList(offset, location,
					filterType);
			if (items != null) {
				JSONArray itemArray = new JSONArray();
				for (Item tempItem : items) {
					JSONObject tempItemJson = new JSONObject();
					tempItemJson.put("itemID", tempItem.getId());
					tempItemJson.put("itemTitle", tempItem.getItemTitle());
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					tempItemJson.put("commentCount",
							tempItem.getCollectionNum());
					tempItemJson.put("releaseDate",
							sdf.format(tempItem.getReleaseDate()));
					tempItemJson.put("rtxName", tempItem.getRtxName());
					tempItemJson.put("description",
							tempItem.getItemDescription());
					tempItemJson.put("location",
							userService.getUserByRTX(tempItem.getRtxName())
									.getWorkLocation());
					itemArray.put(tempItemJson);
				}
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", itemArray);
			} else {
				ret.put("state", 1);
				ret.put("info", "fail");
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 获取item列表
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */

	@RequestMapping(value = "app/sellList")
	public void sellList(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			int offset = Integer.parseInt(request.getParameter("offset"));
			int location = Integer.parseInt(request.getParameter("location"));
			int filterType = Integer.parseInt(request
					.getParameter("filterType"));
			JSONArray itemJsonArray = listService.getSaleItemList(offset,
					location, filterType);
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

	/**
	 * 获取消息
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/messageList")
	public void messageListAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			ret.put("collectionList",
					listService.getCollectionMessageByRtxName(rtxName));
			ret.put("commentList",
					listService.getCommentMessageByRtxName(rtxName));
			listService.setMessageOld(rtxName);
			ret.put("state", 0);
			ret.put("info", "success");
		} catch (Exception e) {
			ret.put("info", e.getMessage());
			ret.put("state", 1);
		}
		response.getWriter().write(ret.toString());
	}
}