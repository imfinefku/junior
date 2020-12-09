package com.junior.blog.domain;

import java.io.Serializable;

/**
 * 角色菜单对应实体，对应数据库表sys_role_menu
 * @author xuduo
 *
 */
public class RoleMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5643219507447795419L;
	
	/** id */
	private String id;
	/** 角色id */
	private String role_id;
	/** 菜单id */
	private String menu_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
}
