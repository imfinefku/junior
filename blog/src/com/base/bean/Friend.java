package com.base.bean;

public class Friend {
	
	private String id;
	private String url;//友链地址
	private String name;//网站名称
	private String remarks;//备注
	private int sort;//显示顺序
	private long inserttime;//添加时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public long getInserttime() {
		return inserttime;
	}
	public void setInserttime(long inserttime) {
		this.inserttime = inserttime;
	}
}
