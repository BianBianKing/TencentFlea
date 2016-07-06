package com.tencentflea.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

//@WebFilter(filterName = "authority", urlPatterns = {"*"}, initParams = {
//		@WebInitParam(name = "encoding", value = "UTF-8"),
//		@WebInitParam(name = "loginAction", value = "login"),
//		@WebInitParam(name = "signupAction", value = "signup")})
public class AccessFilter implements Filter {
	// FilterConfig可用于访问Filter的配置信
	private FilterConfig config;

	// 实现初始化方
	public void init(FilterConfig config) {
		this.config = config;
	}

	// 实现毁方法
	public void destroy() {
		this.config = null;
	}

	// 执行过滤的核心方法
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 获取该Filter的配置参数
		String encoding = config.getInitParameter("encoding");
		String login = config.getInitParameter("loginAction");
		String signupAction = config.getInitParameter("signupAction");
		// 设置request编码用的字符集
		request.setCharacterEncoding(encoding);
		HttpServletRequest requ = (HttpServletRequest) request;
		HttpSession session = requ.getSession(true);
		String requestPath = requ.getPathInfo();
		// 如果session范围的user为null，即表明没有登录
		// 且用户请求的既不是登录，也不是注册
		if (session.getAttribute("user") == null) {
			if (requestPath.endsWith(login) || requestPath.endsWith(signupAction)) {
				chain.doFilter(request, response);
			} else {
				JSONObject ret = new JSONObject();
				try {
					ret.put("state", 1);
					ret.put("info", "用户未登录");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(ret.toString());
			}
		} else if (session.getAttribute("user") != null) {
			chain.doFilter(request, response);
		}
	}
}