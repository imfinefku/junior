package com.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.bean.LiuYan;
import com.base.dao.LiuYanDao;

/**
*
* @author xuduo
*/
@Service("liuYanService")
public class LiuYanService {

   @Autowired
   public LiuYanDao liuYanDao;
   
   public void insertLy(LiuYan ly) {
	   liuYanDao.insertLy(ly);
   }
   
   public List<HashMap> getLybFy(HashMap map){
	   return liuYanDao.getLybFy(map);
   }
   
   public int getLybFyTotal(HashMap map){
	   return liuYanDao.getLybFyTotal(map);
   }
   
   public void zanPingLun(HashMap map) {
	   liuYanDao.zanPingLun(map);
   }
   
   public void caiPingLun(HashMap map) {
	   liuYanDao.caiPingLun(map);
   }
   
   public List<HashMap> getTextLyFy(HashMap map){
	   return liuYanDao.getTextLyFy(map);
   }
   
   public int getTextLyFyTotal(HashMap map){
	   return liuYanDao.getTextLyFyTotal(map);
   }
   
   public List<HashMap> getNewLy(){
	   return liuYanDao.getNewLy();
   }
}
