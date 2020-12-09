package com.junior.blog.domain;

import java.io.Serializable;

/**
 * 菜单实体类，对应数据库表sys_menu
 * @author xuduo
 *
 */
public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2100217423573516059L;
	
	/** id */
	private String id;
	/** 菜单名称 */
	private String name;
	/** 菜单路径 */
	private String link;
	/** 菜单级别 */
	private int level;
	/** 父级菜单id */
	private String pid;
	/** 菜单顺序 */
	private int orders;

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}
}
