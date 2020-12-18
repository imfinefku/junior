package com.junior.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Menu;
import com.junior.blog.domain.Role;
import com.junior.blog.domain.RoleMenu;
import com.junior.blog.domain.User;

public interface UserDao {

	public User getUserByUsername(@Param("username") String username);

	public List<User> getDataPage(Map dataMap);

	public int getDataPageCount(Map dataMap);

	public int deleteUser(@Param("id") String id);

	public List<Role> getRole();

	public int insertUser(User user);

	public int updateUser(User user);

	public int updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);

	public List<Role> getRolePage(Map map);

	public int getRolePageCount(Map map);

	public int insertRole(Role role);

	public int updateRole(Role role);

	public int deleteRole(@Param("id") String id);

	public int deleteRoleMenu(@Param("id") String id);

	public int getMenuNumByRoleId(@Param("id") String id);

	public List<Menu> getMenuListByRoleId(@Param("id") String id);

	public List<Menu> getAllMenuList();

	public int insertRoleMenuList(List<RoleMenu> roleMenuList);
}
