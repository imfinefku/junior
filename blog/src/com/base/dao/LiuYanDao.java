package com.base.dao;

import java.util.HashMap;
import java.util.List;

import com.base.bean.LiuYan;

/**
 *
 * @author xuduo
 */
public interface LiuYanDao {

	public void insertLy(LiuYan ly);
	
	public List<HashMap> getLybFy(HashMap map);
	
	public int getLybFyTotal(HashMap map);
	
	public void zanPingLun(HashMap map);
	
	public void caiPingLun(HashMap map);
	
	public List<HashMap> getTextLyFy(HashMap map);
	
	public int getTextLyFyTotal(HashMap map);
	
	public List<HashMap> getNewLy();
}
