package com.tencentflea.controller;

import java.io.IOException;
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
import com.tencentflea.forms.User;
import com.tencentflea.service.api.ListService;
import com.tencentflea.service.api.UserService;

@Controller(value = "searchSellItemController")
public class SearchSellItemController {
	@Resource(name = "listServiceImpl")
	ListService listService;
	@Resource(name = "userServiceImpl")
	UserService userService;

	/**
	 * 根据关键字获取商品列表
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/searchSellItem")
	public void searchListAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String keyword = request.getParameter("keyword");

			List<Item> items = listService.getItemSearchList(keyword);
			List<User> users = listService.getUserSearchList(keyword);
			if (items != null) {
				JSONArray itemArray = new JSONArray();
				for (Item tempItem : items) {
					JSONObject tempItemJson = new JSONObject();
					tempItemJson.put("itemID", tempItem.getId());
					tempItemJson.put("itemTitle", tempItem.getItemTitle());
					tempItemJson.put("price", tempItem.getItemPrice());
					tempItemJson.put("location",
							userService.getUserByRTX(tempItem.getRtxName())
									.getWorkLocation());
					itemArray.put(tempItemJson);
				}
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", itemArray);
			}
			if (users != null) {
				JSONArray userArray = new JSONArray();
				for (User tempUser : users) {
					JSONObject tempUserJson = new JSONObject();
					tempUserJson.put("rtxName", tempUser.getRtxName());
					tempUserJson.put("profile", tempUser.getIcon());
				}
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", userArray);
			}
			if (users == null && items == null) {
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("itemList", new JSONArray());
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}
}
