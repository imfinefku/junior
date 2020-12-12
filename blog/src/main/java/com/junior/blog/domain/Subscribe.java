package com.junior.blog.domain;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * 订阅实体类，对应数据库表sys_subscribe
 * @author xuduo
 *
 */
public class Subscribe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7672814712241645244L;

	/** id */
	private String id;
	/** 名称 */
	@NotEmpty(message = "昵称不能为空")
	@Length(max = 10, message = "昵称不能超过10个字符")
	private String name;
	/** 邮箱 */
	@NotEmpty(message = "邮箱不能为空")
	@Length(max = 255, message = "邮箱不能超过255个字符")
	@Email(message="邮箱格式不正确")
	private String email;
	/** 添加时间 */
	private long addtime;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
}
