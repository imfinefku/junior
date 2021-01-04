package com.junior.blog.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * 友链申请表
 * 
 * @author xuduo
 *
 */
public class FriendApply implements Serializable {

	public static final int PASS = 1;
	
	public static final int REFUSE = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2817152704875750213L;

	/** id */
	private String id;
	/** 网站名称 */
	@NotEmpty(message = "网站名称不能为空")
	@Length(max = 10, message = "网站名称不能超过10个字符")
	private String name;
	/** 地址 */
	@NotEmpty(message = "网址不能为空")
	@Length(max = 100, message = "网址不能超过100个字符")
	private String url;
	/** 申请时间 */
	private long applytime;
	/** 状态 0：未审批 1：通过 2：拒绝 */
	private int status;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getApplytime() {
		return applytime;
	}

	public void setApplytime(long applytime) {
		this.applytime = applytime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
