package com.base.dao;

import java.util.HashMap;
import java.util.List;

import com.base.bean.BiaoQian;

/**
 *
 * @author xuduo
 */
public interface BiaoQianDao {

	public List<BiaoQian> getData(HashMap map);
	
	public int getDataTotal(HashMap map);
	
	public void insert(BiaoQian bq);
	
	public void delete(HashMap map);
	
	public void update(BiaoQian bq);
	
	public List<HashMap> getLabelCount();
}
