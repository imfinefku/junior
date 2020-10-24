package com.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.base.controller.UserController;
import com.base.domain.User;
import com.base.util.Constant;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Component
public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("启动过滤器");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		httpresponse.setHeader("Access-Control-Allow-Origin", "*");// 允许跨域
		httpresponse.setHeader("Access-Control-Allow-Headers", "Origin,No-Cache,X-Requested-With,Content-Type");
		User user = (User) httprequest.getSession().getAttribute("user");
		String urli = httprequest.getServletPath();
		if (user == null && !urli.equals(Constant.LOGIN_URL)) {
			// 只过滤受保护资源
			if (urli.endsWith(".do") || urli.startsWith("/manager")) {
				// 未登录，跳到登录页面
				request.getRequestDispatcher(Constant.LOGIN_URL).forward(httprequest, httpresponse);
			} else {
				chain.doFilter(httprequest, httpresponse); // 不用过滤
			}
		} else {// 已登录
			chain.doFilter(httprequest, httpresponse); // 不用过滤
		}
	}

	@Override
	public void destroy() {
		logger.info("销毁过滤器");
	}

}
