package com.junior.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Menu;

public interface MenuDao {
	
	public List<Menu> getMenuByUserId(@Param("roleId")String roleId);
}
