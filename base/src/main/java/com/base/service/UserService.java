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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test() throws Throwable {
		User user1 = new User();
		user1.setId(UUID.randomUUID().toString());
		user1.setName("1");
		user1.setUsername("1");
		user1.setPassword("1");
		dao.insertUser(user1);
		System.out.println("Throwable");
		Throwable t=new Error();
		throw t;
//		User user2 = new User();
//		user2.setId(UUID.randomUUID().toString());
//		user2.setName("2");
//		user2.setUsername("2");
//		user2.setPassword("2");
//		dao.insertUser(user2);
//		User user3 = new User();
//		user3.setId(UUID.randomUUID().toString());
//		user3.setName("3");
//		user3.setUsername("3");
//		user3.setPassword("3");
//		dao.insertUser(user3);
	}
}
