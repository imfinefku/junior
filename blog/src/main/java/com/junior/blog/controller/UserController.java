package com.junior.blog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junior.blog.common.CommonResult;
import com.junior.blog.domain.Role;
import com.junior.blog.domain.User;
import com.junior.blog.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("user")
public class UserController
		implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserDetailsServiceImpl service;

	/**
	 * 未登录请求处理
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/unloginRequestDeal")
	public CommonResult login(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String url = savedRequest.getRedirectUrl();
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
		User user = (User) authentication.getPrincipal();
		Cookie name = new Cookie("name", user.getName());
		response.addCookie(name);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(user.getMenuList())));
	}

	/**
	 * 注销成功
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Cookie name = new Cookie("name", null);
		response.addCookie(name);
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success()));
	}

	/**
	 * 分页获取用户数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getDataPage")
	public CommonResult<Map> getDataPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int page, @RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<User> rtnList = service.getDataPage(dataMap);
		int count = service.getDataPageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/insertUser")
	public CommonResult insertUser(HttpServletRequest request, HttpServletResponse response,
			@Validated @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		if (service.checkUsername(user.getUsername())) {
			user.setId(UUID.randomUUID().toString());
			service.insertUser(user);
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/updateUser")
	public CommonResult updateUser(HttpServletRequest request, HttpServletResponse response,
			@Validated @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		service.updateUser(user);
		return CommonResult.success();
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteUser")
	public CommonResult addUser(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
		int result = service.deleteUser(id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 获取所有的角色信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/getRole")
	public CommonResult<List<Role>> getRole(HttpServletRequest request, HttpServletResponse response) {
		List<Role> rtnList = service.getRole();
		return CommonResult.success(rtnList);
	}
}