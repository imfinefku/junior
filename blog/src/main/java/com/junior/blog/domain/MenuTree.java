package com.junior.blog.domain;

import java.util.List;

/**
 * 菜单树
 * 
 * @author xuduo
 *
 */
public class MenuTree {

	/** id */
	private String id;
	/** 菜单名称 */
	private String title;
	/** 下级菜单 */
	private List<MenuTree> children;
	/** 上级菜单id */
	private String pid;
	/** 设置节点勾选 */
	private boolean checked=false;
	/** 设置是否展开节点 */
	private boolean spread=false;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}
}
