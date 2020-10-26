package com.base.dao;

import com.base.domain.User;

public interface UserDao {
	
	public User getUserByUsername(User user);
	
	public void deleteUser();
	
	public void insertUser(User user);
}
