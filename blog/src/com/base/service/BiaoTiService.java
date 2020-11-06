package com.base.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.bean.Title;
import com.base.dao.BiaoTiDao;

/**
*
* @author xuduo
*/
@Service("biaoTiService")
public class BiaoTiService {

   @Autowired
   public BiaoTiDao biaoTiDao;

   public List<Title> getData(HashMap map){
	   return biaoTiDao.getData(map);
   }
   
   public int getDataTotal(HashMap map){
	   return biaoTiDao.getDataTotal(map);
   }
   
   public void delete(HashMap map) {
	   biaoTiDao.delete(map);
   }
   
   public void insertTitle(Title title) {
	   biaoTiDao.insertTitle(title);
   }
   
   public void updateTitle(Title title) {
	   biaoTiDao.updateTitle(title);
   }
   
   public List<Title> getTitleList(){
	   return biaoTiDao.getTitleList();
   }

}
