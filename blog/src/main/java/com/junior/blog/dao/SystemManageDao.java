package com.junior.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.junior.blog.domain.Friend;
import com.junior.blog.domain.FriendApply;
import com.junior.blog.domain.Subscribe;

public interface SystemManageDao {

	public List<Subscribe> getSubscribePage(Map map);

	public int getSubscribePageCount(Map map);

	public int deleteSubscribe(@Param("id") String id);

	public int insertSubscribe(Subscribe subscribe);

	public int updateSubscribe(Subscribe subscribe);

	public List<FriendApply> getFriendApplyPage(Map map);

	public int getFriendApplyPageCount(Map map);

	public int updateFriendApplyStatus(@Param("status") int status, @Param("id") String id);

	public FriendApply findFriendApplyById(@Param("id") String id);

	public int findFriendMaxOrders();

	public int insertFriend(Friend friend);

	public List<Friend> getFriendPage(Map map);

	public int getFriendPageCount(Map map);

	public int updateFriendStatus(@Param("status") int status, @Param("id") String id);

	public int deleteFriend(@Param("id") String id);

	public int updateFriend(Friend friend);

	public int insertFriendApply(FriendApply friendApply);

	public int findCountByEmail(@Param("email") String email);

	public int findCountByFriend(@Param("url") String url);

	public int findCountByFriendApply(@Param("url") String url);
}
