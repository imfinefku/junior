package com.junior.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Subscribe;

public interface SystemManageDao {

	public List<Subscribe> getSubscribePage(Map map);

	public int getSubscribePageCount(Map map);

	public int deleteSubscribe(@Param("id") String id);

	public int insertSubscribe(Subscribe subscribe);

	public int updateSubscribe(Subscribe subscribe);
}
