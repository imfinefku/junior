package com.junior.blog.domain;

import java.io.Serializable;

/**
 * 角色实体类，对应数据库表sys_role
 * @author xuduo
 *
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5959010896447518749L;
	
	/** id */
	private String id;
	/** 角色名称 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
