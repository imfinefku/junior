package com.junior.blog.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户实体类 对应数据库表sys_user
 * 
 * @author junior
 * 
 */
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 86907990065964328L;

	private String id;
	/** 用户名 */
	@NotEmpty(message = "账号不能为空")
	@Length(max = 20, message = "账号不能超过20个字符")
	private String username;
	/** 密码 */
	@NotEmpty(message = "密码不能为空")
	@Length(max = 20, message = "密码不能超过20个字符")
	private String password;
	/** 名称 */
	@NotEmpty(message = "用户名不能为空")
	@Length(max = 10, message = "用户名不能超过10个字符")
	private String name;
	/** 角色 */
	private String role_id;
	/** 权限菜单 */
	private List<Menu> menuList;
	/** 角色名 */
	private String rolename;

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

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(id).append(username).append(password).append(name).append(role_id);
		return str.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	/**
	 * 账户没有过期
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 账户没有锁定
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 密码没有过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 用户是否被删掉了
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
