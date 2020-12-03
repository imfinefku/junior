package com.junior.blog.dao;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.User;

public interface UserDao {
	
	public User getUserByUsername(@Param("username")String username);
}
