package com.junior.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import com.junior.blog.domain.Menu;
import com.junior.blog.domain.MenuTree;
import com.junior.blog.domain.Role;
import com.junior.blog.domain.RoleMenu;
import com.junior.blog.domain.User;
import com.junior.blog.service.UserDetailsServiceImpl;

/**
 * 用户相关Controller
 * 
 * @author xuduo
 *
 */
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
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success(user.getMenuList())));
	}

	/**
	 * 注销成功
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.getWriter().write(objectMapper.writeValueAsString(CommonResult.success()));
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	@GetMapping("/getUserInformation")
	public CommonResult getUserInformation() {
		User user = (User) service.getCurrentUser();
		Map rtnMap = new HashMap();
		rtnMap.put("name", user.getName());
		rtnMap.put("menu", user.getMenuList());
		return CommonResult.success(rtnMap);
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
	 * 
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
	public CommonResult addUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) {
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

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param oldPassword
	 * @param newPassword
	 * @param repeatNew
	 * @return
	 */
	@PostMapping("/updatePassword")
	public CommonResult updatePassword(HttpServletRequest request, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("repeatNew") String repeatNew) {
		User user = service.getCurrentUser();
		if (!user.getPassword().equals(oldPassword)) {
			return CommonResult.failed("旧密码错误！");
		}
		if (!newPassword.equals(repeatNew)) {
			return CommonResult.failed("新密码两次输入不一致！");
		}
		if (oldPassword.equals(newPassword)) {
			return CommonResult.failed("新密码不能和旧密码相同！");
		}
		int result = service.updatePassword(user.getId(), newPassword);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 分页获取角色数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getRolePage")
	public CommonResult getRolePage(HttpServletRequest request, HttpServletResponse response, @RequestParam int page,
			@RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Role> rtnList = service.getRolePage(dataMap);
		int count = service.getRolePageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 * @param role
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/insertRole")
	public CommonResult insertRole(HttpServletRequest request, HttpServletResponse response,
			@Validated @RequestBody Role role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		role.setId(UUID.randomUUID().toString());
		int result = service.insertRole(role);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 修改角色信息
	 * 
	 * @param request
	 * @param response
	 * @param role
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/updateRole")
	public CommonResult updateRole(HttpServletRequest request, HttpServletResponse response,
			@Validated @RequestBody Role role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		int result = service.updateRole(role);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteRole")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CommonResult deleteRole(HttpServletRequest request, @RequestParam("id") String id) {
		int menuNum = service.getMenuNumByRoleId(id);
		// 删除角色权限菜单
		int result = service.deleteRoleMenu(id);
		// 删除角色
		int result2 = service.deleteRole(id);
		if (result == menuNum && result2 > 0) {
			return CommonResult.success();
		}
		// 手动开启事务回滚
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return CommonResult.failed();
	}

	/**
	 * 获取角色权限菜单树
	 * 
	 * @param request
	 * @param id
	 * @param spread
	 * @return
	 */
	@GetMapping("/getRoleMenuTree")
	public CommonResult getRoleMenuTree(HttpServletRequest request, @RequestParam("id") String id,
			@RequestParam("spread") boolean spread) {
		List<Menu> menuList = service.getAllMenuList();
		List<Menu> roleMenuList = service.getMenuListByRoleId(id);
		service.setMenuChecked(menuList, roleMenuList);
		List<MenuTree> menuTreeList = service.getMenuTreeByList(menuList, spread);
		return CommonResult.success(menuTreeList);
	}

	/**
	 * 保存角色的菜单权限
	 * 
	 * @param request
	 * @param id
	 * @param menuTreeList
	 * @return
	 */
	@PostMapping("/insertRoleMenu")
	public CommonResult insertRoleMenu(HttpServletRequest request, @RequestParam("id") String id,
			@RequestBody List<MenuTree> menuTreeList) {
		if (menuTreeList.size() > 0) {
			List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
			service.getRoleMenuList(menuTreeList, roleMenuList, id);
			if (roleMenuList.size() > 0) {
				service.deleteRoleMenu(id);
				int result = service.insertRoleMenuList(roleMenuList);
				if (result == roleMenuList.size()) {
					return CommonResult.success();
				}
			}
		}
		return CommonResult.failed();
	}
}