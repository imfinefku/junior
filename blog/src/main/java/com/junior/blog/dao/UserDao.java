package com.junior.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Role;
import com.junior.blog.domain.User;

public interface UserDao {
	
	public User getUserByUsername(@Param("username")String username);
	
	public List<User> getDataPage(Map dataMap);
	
	public int getDataPageCount(Map dataMap);
	
	public int deleteUser(@Param("id") String id);
	
	public List<Role> getRole();
}
