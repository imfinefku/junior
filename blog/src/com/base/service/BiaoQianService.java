package com.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.bean.BiaoQian;
import com.base.dao.BiaoQianDao;

/**
*
* @author xuduo
*/
@Service("biaoQianService")
public class BiaoQianService {

   @Autowired
   public BiaoQianDao biaoQianDao;
   
   public List<BiaoQian> getData(HashMap map){
	   return biaoQianDao.getData(map);
   }
   
   public int getDataTotal(HashMap map) {
	   return biaoQianDao.getDataTotal(map);
   }
   
   public void insert(BiaoQian bq) {
	   biaoQianDao.insert(bq);
   }
   
   public void delete(HashMap map) {
	   biaoQianDao.delete(map);
   }
   
   public void update(BiaoQian bq) {
	   biaoQianDao.update(bq);
   }
   
   public List<HashMap> getLabelCount(){
	   return biaoQianDao.getLabelCount();
   }
}
