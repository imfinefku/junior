package com.base.bean;

public class Title {
	
	private String id;//id
	private String name;//标题名称
	private String introduce;//标题简介
	private String path;//页面路径
	private long sort;//显示顺序
	private long inserttime;//添加时间
	
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
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSort() {
		return sort;
	}
	public void setSort(long sort) {
		this.sort = sort;
	}
	public long getInserttime() {
		return inserttime;
	}
	public void setInserttime(long inserttime) {
		this.inserttime = inserttime;
	}
}
