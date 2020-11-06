package com.base.bean;

public class LiuYan {
	
	private String id;
	private String name;//留言姓名
	private String email;//留言邮箱
	private String content;//留言内容
	private long zan;//点赞数量
	private long cai;//踩数量
	private int lytype;//0：留言板   1：文章留言
	private int type;//0：留言   1：回复
	private String ssly;//所属留言，回复用
	private String sstext;//所属文章，文章留言用
	private long time;//留言时间
	private String ip;//留言者ip
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getZan() {
		return zan;
	}
	public void setZan(long zan) {
		this.zan = zan;
	}
	public long getCai() {
		return cai;
	}
	public void setCai(long cai) {
		this.cai = cai;
	}
	public int getLytype() {
		return lytype;
	}
	public void setLytype(int lytype) {
		this.lytype = lytype;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSsly() {
		return ssly;
	}
	public void setSsly(String ssly) {
		this.ssly = ssly;
	}
	public String getSstext() {
		return sstext;
	}
	public void setSstext(String sstext) {
		this.sstext = sstext;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
