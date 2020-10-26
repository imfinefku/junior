package com.base.service;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.UserDao;
import com.base.domain.User;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public void test(){
		User user = new User();
		user.setUsername("1");
		User rtnUser=dao.getUserByUsername(user);
		System.out.println(rtnUser.getName());
	}
}
