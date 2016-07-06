package com.tencentflea.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Constant;

import com.tencentflea.service.api.DeleteItemService;

@Controller(value="deleteItemController")
public class DeleteController {
	@Resource(name="deleteItemServiceImpl")
	DeleteItemService deleteItemService;
	/**
	 * 删除商品
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/deleteItem")
	public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			long itemID =Long.parseLong(request.getParameter("itemID"));
			if(deleteItemService.deleteItem(itemID)==Constant.SUCCESS){
				
				ret.put("state", 0);
				ret.put("info", "success");
			}else{
				ret.put("state", 1);
				ret.put("info", "failure");
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 删除评论
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/deleteComment")
	public void deleteComment(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			long commentID =Long.parseLong(request.getParameter("commentID"));
			if(deleteItemService.deleteComment(commentID)==Constant.SUCCESS){
				ret.put("state", 0);
				ret.put("info", "success");
			}else{
				ret.put("state", 1);
				ret.put("info", "failure");
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}
}
