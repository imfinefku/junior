package com.junior.blog.domain;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * 友情链接
 * 
 * @author xuduo
 *
 */
public class Friend implements Serializable {

	public static final int USE = 0;
	public static final int UNUSE = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567608985218472172L;
	/** id */
	private String id;
	/** 网站名称 */
	@NotEmpty(message = "网站名称不能为空")
	@Length(max = 10, message = "网站名称不能超过10个字符")
	private String name;
	/** 网址 */
	@NotEmpty(message = "网址不能为空")
	@Length(max = 100, message = "网址不能超过100个字符")
	private String url;
	/** 显示顺序 */
	@Max(value = 10000)
	private int orders;
	/** 添加时间 */
	private long addtime;
	/** 状态,0:正常 1:禁用 */
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

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
