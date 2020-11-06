package com.base.dao;

import java.util.HashMap;
import java.util.List;

import com.base.bean.Title;

/**
 *
 * @author xuduo
 */
public interface BiaoTiDao {

	public List<Title> getData(HashMap map);
	
	public int getDataTotal(HashMap map);
	
	public void delete(HashMap map);
	
	public void insertTitle(Title title);
	
	public void updateTitle(Title title);
	
	public List<Title> getTitleList();
}
