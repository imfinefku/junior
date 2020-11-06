package com.base.dao;

import java.util.HashMap;
import java.util.List;
import com.base.bean.Friend;

/**
 *
 * @author xuduo
 */
public interface YouLianDao {

	public List<Friend> getData(HashMap map);
	
	public int getDataTotal(HashMap map);
	
	public void insert(Friend friend);
	
	public void delete(HashMap map);
	
	public void update(Friend friend);
	
	public List<Friend> getAllData();
}
