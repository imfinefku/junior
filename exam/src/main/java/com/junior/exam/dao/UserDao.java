package com.junior.exam.dao;

import org.apache.ibatis.annotations.Param;

import com.junior.exam.domain.User;

public interface UserDao {
	
	public User getUserByUsername(@Param("username")String username);
}
