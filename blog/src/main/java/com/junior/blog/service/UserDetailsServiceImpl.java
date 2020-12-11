package com.junior.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.junior.blog.dao.MenuDao;
import com.junior.blog.dao.UserDao;
import com.junior.blog.domain.Menu;
import com.junior.blog.domain.Role;
import com.junior.blog.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao dao;
	@Autowired
	private MenuDao menuDao;

	/**
	 * 根据用户名获取用户信息，用于SpringSecurity验证用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.getUserByUsername(username);
		if (user != null && user.getRole_id() != null) {// 获取权限菜单
			List<Menu> menuList = menuDao.getMenuByUserId(user.getRole_id());
			user.setMenuList(menuList);
		}
		return user;
	}

	public User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public List<User> getDataPage(Map dataMap) {
		return dao.getDataPage(dataMap);
	}

	public int getDataPageCount(Map dataMap) {
		return dao.getDataPageCount(dataMap);
	}

	public int deleteUser(String id) {
		return dao.deleteUser(id);
	}

	public List<Role> getRole() {
		return dao.getRole();
	}

	/**
	 * 检查账号是否存在
	 * 
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username) {
		if (dao.getUserByUsername(username) != null) {
			return false;
		}
		return true;
	}
	
	public int insertUser(User user) {
		return dao.insertUser(user);
	}
	
	public int updateUser(User user) {
		return dao.updateUser(user);
	}
}
