package com.base.bean;

public class Text {
    private String id;
    private String title;//文章标题
    private String image;//文章配图
    private String introduce;//文章简介
    private String content;//文章内容
    private String textType;//文章所属类型
    private int iftop;//是否置顶。1:置顶  0:不置顶
    private int ifcarousel;//是否添加到前台轮播。1:轮播显示  0:不轮播显示
    private long insertTime;//文章添加时间
    private long dianji;//点击量
    private long dianzan;//点赞量
    private String author;//作者id
    private String tag;//文章标签id
    
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
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTextType() {
		return textType;
	}
	
	public void setTextType(String textType) {
		this.textType = textType;
	}
	
	public int getIftop() {
		return iftop;
	}
	
	public void setIftop(int iftop) {
		this.iftop = iftop;
	}
	
	public int getIfcarousel() {
		return ifcarousel;
	}
	
	public void setIfcarousel(int ifcarousel) {
		this.ifcarousel = ifcarousel;
	}
	
	public long getInsertTime() {
		return insertTime;
	}
	
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
	
	public long getDianji() {
		return dianji;
	}
	
	public void setDianji(long dianji) {
		this.dianji = dianji;
	}
	
	public long getDianzan() {
		return dianzan;
	}
	
	public void setDianzan(long dianzan) {
		this.dianzan = dianzan;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
}
