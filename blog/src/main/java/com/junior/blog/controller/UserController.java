package com.junior.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junior.blog.common.CommonResult;
import com.junior.blog.domain.User;

@RestController
@RequestMapping("user")
public class UserController implements AuthenticationSuccessHandler,AuthenticationFailureHandler,LogoutSuccessHandler{
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 *未登录请求处理
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/unloginRequestDeal")
	public CommonResult login(HttpServletRequest request,HttpServletResponse response) throws Throwable {
		SavedRequest savedRequest=requestCache.getRequest(request, response);
		if (savedRequest!=null) {
			String url=savedRequest.getRedirectUrl();
			if (StringUtils.endsWithIgnoreCase(url, ".html")) {
				response.sendRedirect("/login.html");
				return null;
			}
		}
		return CommonResult.unauthorized(null);
	}

	/**
	 * 登录失败
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.failed("账号或密码错误")));
	}

	/**
	 * 登录成功
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user=(User)authentication.getPrincipal();
		Cookie name=new Cookie("name",user.getName());
		response.addCookie(name);
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success()));
	}

	/**
	 * 注销成功
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Cookie name=new Cookie("name",null);
		response.addCookie(name);
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success()));
	}
	
	
}
