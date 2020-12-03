package com.junior.base.domain;

import java.io.Serializable;

/**
 * 用户实体类 对应数据库表sys_user
 * 
 * @author junior
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 86907990065964328L;

	private String id;
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 名称 */
	private String name;
	/** 角色 */
	private int role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(id).append(username).append(password).append(name).append(role);
		return str.toString();
	}
}
