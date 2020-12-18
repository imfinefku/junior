package com.junior.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.junior.blog.dao.MenuDao;
import com.junior.blog.dao.UserDao;
import com.junior.blog.domain.Menu;
import com.junior.blog.domain.MenuTree;
import com.junior.blog.domain.Role;
import com.junior.blog.domain.RoleMenu;
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

	public int updatePassword(String id, String newPassword) {
		return dao.updatePassword(id, newPassword);
	}

	public List<Role> getRolePage(Map map) {
		return dao.getRolePage(map);
	}

	public int getRolePageCount(Map map) {
		return dao.getRolePageCount(map);
	}

	public int insertRole(Role role) {
		return dao.insertRole(role);
	}

	public int updateRole(Role role) {
		return dao.updateRole(role);
	}

	public int deleteRoleMenu(String id) {
		return dao.deleteRoleMenu(id);
	}

	public int deleteRole(String id) {
		return dao.deleteRole(id);
	}

	public int getMenuNumByRoleId(String id) {
		return dao.getMenuNumByRoleId(id);
	}

	public List<Menu> getAllMenuList() {
		return dao.getAllMenuList();
	}

	public List<Menu> getMenuListByRoleId(String id) {
		return dao.getMenuListByRoleId(id);
	}

	public List<MenuTree> getMenuTreeByList(List<Menu> menuList, boolean spread) {
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		for (Menu menu : menuList) {
			MenuTree menuTree = new MenuTree();
			menuTree.setId(menu.getId());
			menuTree.setTitle(menu.getName());
			menuTree.setPid(menu.getPid());
			menuTree.setChecked(menu.getChecked());
			if (spread) {
				menuTree.setSpread(spread);
			}
			if (menu.getLevel() == 1) {
				menuTreeList.add(menuTree);
				continue;
			}
			setMenuTreeChildren(menuTreeList, menuTree);
		}
		return menuTreeList;
	}

	public void setMenuTreeChildren(List<MenuTree> menuTreeList, MenuTree menu) {
		for (MenuTree menuTree : menuTreeList) {
			if (menu.getPid().equals(menuTree.getId())) {
				if (menuTree.getChildren() == null) {
					menuTree.setChildren(new ArrayList<MenuTree>());
				}
				menuTree.getChildren().add(menu);
				return;
			}
			if (menuTree.getChildren() != null && menuTree.getChildren().size() > 0) {
				setMenuTreeChildren(menuTree.getChildren(), menu);
			}
		}
	}

	public void setMenuChecked(List<Menu> menuList, List<Menu> roleMenuList) {
		Map<String, Menu> roleMenuMap = new HashMap<String, Menu>();
		for (Menu menu : roleMenuList) {
			roleMenuMap.put(menu.getId(), menu);
		}
		for (Menu menu : menuList) {
			if (roleMenuMap.get(menu.getId()) != null) {
				menu.setChecked(true);
			}
		}
	}

	public void getRoleMenuList(List<MenuTree> menuTreeList, List<RoleMenu> roleMenuList, String id) {
		for (MenuTree menuTree : menuTreeList) {
			RoleMenu rm = new RoleMenu();
			rm.setId(UUID.randomUUID().toString());
			rm.setRole_id(id);
			rm.setMenu_id(menuTree.getId());
			roleMenuList.add(rm);
			if (menuTree.getChildren() != null && menuTree.getChildren().size() > 0) {
				getRoleMenuList(menuTree.getChildren(), roleMenuList, id);
			}
		}
	}

	public int insertRoleMenuList(List<RoleMenu> roleMenuList) {
		return dao.insertRoleMenuList(roleMenuList);
	}
}
