package com.tencentflea.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.Constant;

import com.tencentflea.forms.User;
import com.tencentflea.service.api.UserService;

@Controller(value = "userController")
public class UserController {
	@Resource(name = "userServiceImpl")
	UserService userService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "app/login")
	public void loginAction(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			String password = request.getParameter("password");
			User user = userService.getUserByRTX(rtxName);
			System.out.println(request.getSession().getServletContext()
					.getRealPath("/"));
			if (user == null) {
				ret.put("state", 1);
				ret.put("info", "用户名不存在或密码错误");
				ret.put("rtxName", "");
				ret.put("profile", "");
				ret.put("location", "");
			} else if (password.equals(user.getPassword())) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				ret.put("state", 0);
				ret.put("info", "success");
				ret.put("rtxName", user.getRtxName());
				ret.put("profile", Constant.IMAGE_PATH + user.getIcon()
						+ ".jpg");
				ret.put("location", user.getWorkLocation());
			} else {
				ret.put("state", 1);
				ret.put("info", "fail");
				ret.put("profile", "");
				ret.put("location", "");
				ret.put("rtxName", "");
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
			ret.put("profile", "");
			ret.put("location", "");
			ret.put("rtxName", "");
		}
		response.getWriter().write(ret.toString());
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JSONException
	 * @RequestParam("uploadfile") CommonsMultipartFile[] files,
	 */
	@RequestMapping(value = "app/signup")
	public void signupAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		JSONObject ret = new JSONObject();
		try {
			String rtxName = request.getParameter("rtxName");
			String password = request.getParameter("password");
			String location = request.getParameter("location");
			int workLocation = -1;
			if (location != null) {
				workLocation = Integer.parseInt(location);
			}
			User user = new User();
			user.setRtxName(rtxName);
			user.setWorkLocation(workLocation);
			user.setPassword(password);
			user.setIcon("default");
			int result = userService.saveUser(user);
			if (result == Constant.SUCCESS) {
				ret.put("state", 0);
				ret.put("info", "success");
			} else {
				ret.put("state", 1);
				ret.put("info", "rtxName已存在");
			}
		} catch (Exception e) {
			ret.put("state", 1);
			ret.put("info", e.getMessage());
		}
		response.getWriter().write(ret.toString());
	}
}
