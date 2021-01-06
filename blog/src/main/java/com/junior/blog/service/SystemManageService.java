package com.junior.blog.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.junior.blog.dao.SystemManageDao;
import com.junior.blog.domain.Friend;
import com.junior.blog.domain.FriendApply;
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

	public List<FriendApply> getFriendApplyPage(Map map) {
		return dao.getFriendApplyPage(map);
	}

	public int getFriendApplyPageCount(Map map) {
		return dao.getFriendApplyPageCount(map);
	}

	public int updateFriendApplyStatus(int status, String id) {
		return dao.updateFriendApplyStatus(status, id);
	}

	/**
	 * 通过友链并添加到友情链接表中
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean passFriendApply(String id) {
		if (updateFriendApplyStatus(FriendApply.PASS, id) > 0) {
			FriendApply fa = dao.findFriendApplyById(id);
			Friend friend = new Friend();
			friend.setId(UUID.randomUUID().toString());
			friend.setName(fa.getName());
			friend.setUrl(fa.getUrl());
			friend.setAddtime(new Date().getTime());
			friend.setOrders(dao.findFriendMaxOrders());
			if (dao.insertFriend(friend) > 0) {
				return true;
			}
		}
		// 手动开启事务回滚
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return false;
	}

	public List<Friend> getFriendPage(Map map) {
		return dao.getFriendPage(map);
	}

	public int getFriendPageCount(Map map) {
		return dao.getFriendApplyPageCount(map);
	}

	public int updateFriendStatus(int status, String id) {
		return dao.updateFriendStatus(status, id);
	}

	public int deleteFriend(String id) {
		return dao.deleteFriend(id);
	}

	public int findFriendMaxOrders() {
		return dao.findFriendMaxOrders();
	}

	public int insertFriend(Friend friend) {
		return dao.insertFriend(friend);
	}

	public int updateFriend(Friend friend) {
		return dao.updateFriend(friend);
	}

	public int insertFriendApply(FriendApply friendApply) {
		return dao.insertFriendApply(friendApply);
	}

	public boolean checkEmailRepeat(String email) {
		int num = dao.findCountByEmail(email);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public boolean checkFriendApplyRepeat(String url) {
		int num = dao.findCountByFriendApply(url);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public boolean checkFriendRepeat(String url) {
		int num = dao.findCountByFriend(url);
		if (num > 0) {
			return true;
		}
		return false;
	}
}
