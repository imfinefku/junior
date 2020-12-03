package com.junior.program.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.junior.program.dao.UserDao;
import com.junior.program.domain.User;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao dao;

	/**
	 * 根据用户名获取用户信息，用于SpringSecurity验证用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=dao.getUserByUsername(username);
		return user;
	}
}
