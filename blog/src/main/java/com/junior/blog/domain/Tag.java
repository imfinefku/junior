package com.junior.blog.domain;

import java.io.Serializable;

/**
 * 标签实体类，对应数据库表b_tag
 * @author xuduo
 *
 */
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3933997433023485927L;

	/** id */
	private String id;
	
	/** 标签名 */
	private String name;
	
	/** 添加时间 */
	private long addtime;
	
	/** 对应博客数量 */
	private int num;

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

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
