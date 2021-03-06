package com.junior.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Blog;
import com.junior.blog.domain.Friend;
import com.junior.blog.domain.Tag;

public interface BlogManageDao {

	public List<Tag> getDataPage(Map map);

	public int getDataPageCount(Map map);

	public int insertTag(Tag tag);

	public int updateTag(Tag tag);

	public int deleteTag(@Param("id") String id);

	public List<Blog> getBlogPage(Map map);

	public int getBlogPageCount(Map map);

	public int updateBlogStatus(@Param("id") String id, @Param("status") int status);

	public int deleteBlog(@Param("id") String id);

	public int insertBlog(Blog blog);

	public int updateBlog(Blog blog);

	public List<Tag> getAllTag();

	public Blog getBlogById(@Param("id") String id);

	public int addHits(@Param("id") String id);

	public List<Blog> getViewBlogPage(Map map);

	public int getViewBlogPageCount(Map map);

	public List<Blog> getLastAndNext(@Param("id") String id);

	public int addLikeNum(@Param("id") String id);

	public List<Tag> getViewTags();
	
	public List<Friend> getViewFriends();
}
