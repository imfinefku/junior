package com.junior.blog.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * 博客实体类，对应数据库表b_blog
 * 
 * @author xuduo
 *
 */
public class Blog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6901697604725212846L;

	public static final int YFB = 1;// 已发布
	public static final int WFB = 0;// 未发布

	/** id */
	private String id;
	/** 标题 */
	@NotEmpty(message = "标题不能为空")
	@Length(max = 30, message = "标题不能超过30个字符")
	private String title;
	/** 内容 */
	@NotEmpty(message = "博客内容不能为空")
	private String content;
	/** 配图 */
	private String picture;
	/** 作者id */
	private String author;
	/** 标签id */
	@NotEmpty(message = "标签不能为空")
	private String tag_id;
	/** 标签名称 */
	private String tag_name;
	/** 点赞数量 */
	private long likenum;
	/** 状态 0：未发布 1：已发布 */
	private int status;
	/** 添加时间 */
	private long addtime;
	/** 修改时间 */
	private long updatetime;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public long getLikenum() {
		return likenum;
	}

	public void setLikenum(long likenum) {
		this.likenum = likenum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}
}
