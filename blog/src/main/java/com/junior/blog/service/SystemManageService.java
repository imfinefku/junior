package com.junior.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junior.blog.dao.SystemManageDao;
import com.junior.blog.domain.Subscribe;

@Service
public class SystemManageService {

	@Autowired
	private SystemManageDao dao;

	public List<Subscribe> getSubscribePage(Map map) {
		return dao.getSubscribePage(map);
	}

	public int getSubscribePageCount(Map map) {
		return dao.getSubscribePageCount(map);
	}

	public int deleteSubscribe(String id) {
		return dao.deleteSubscribe(id);
	}

	public int insertSubscribe(Subscribe subscribe) {
		return dao.insertSubscribe(subscribe);
	}

	public int updateSubscribe(Subscribe subscribe) {
		return dao.updateSubscribe(subscribe);
	}
}
