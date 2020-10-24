package com.base.dao;

import org.apache.ibatis.annotations.Mapper;

import com.base.domain.User;

@Mapper
public interface UserDao {
	
	public User getUserByUsername(User user);
	
	public void deleteUser();
	
	public void insertUser(User user);
}
