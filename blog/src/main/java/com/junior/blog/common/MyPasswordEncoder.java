package com.junior.blog.common;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

	/**
	 * 加密
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	/**
	 * 比对
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

}
