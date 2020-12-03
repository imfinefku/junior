package com.junior.program.dao;

import org.apache.ibatis.annotations.Param;

import com.junior.program.domain.User;

public interface UserDao {
	
	public User getUserByUsername(@Param("username")String username);
}
