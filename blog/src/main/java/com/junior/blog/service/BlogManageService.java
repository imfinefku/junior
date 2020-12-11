package com.junior.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junior.blog.dao.BlogManageDao;
import com.junior.blog.domain.Blog;
import com.junior.blog.domain.Tag;

/**
 * 博客管理service
 * 
 * @author xuduo
 *
 */
@Service
public class BlogManageService {

	@Autowired
	private BlogManageDao dao;

	public List<Tag> getDataPage(Map map) {
		return dao.getDataPage(map);
	}

	public int getDataPageCount(Map map) {
		return dao.getDataPageCount(map);
	}

	public int insertTag(Tag tag) {
		return dao.insertTag(tag);
	}

	public int updateTag(Tag tag) {
		return dao.updateTag(tag);
	}

	public int deleteTag(String id) {
		return dao.deleteTag(id);
	}

	public List<Blog> getBlogPage(Map map) {
		return dao.getBlogPage(map);
	}

	public int getBlogPageCount(Map map) {
		return dao.getBlogPageCount(map);
	}

	public int updateBlogStatus(String id, int status) {
		return dao.updateBlogStatus(id, status);
	}

	public int deleteBlog(String id) {
		return dao.deleteBlog(id);
	}
}
