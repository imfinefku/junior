package com.base.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.bean.Friend;
import com.base.dao.YouLianDao;

/**
*
* @author xuduo
*/
@Service("youLianService")
public class YouLianService {

   @Autowired
   public YouLianDao youLianDao;
   
   public List<Friend> getData(HashMap map){
	   return youLianDao.getData(map);
   }
   
   public int getDataTotal(HashMap map) {
	   return youLianDao.getDataTotal(map);
   }
   
   public void insert(Friend friend) {
	   youLianDao.insert(friend);
   }
   
   public void delete(HashMap map) {
	   youLianDao.delete(map);
   }
   
   public void update(Friend friend) {
	   youLianDao.update(friend);
   }
   
   public List<Friend> getAllData(){
	   return youLianDao.getAllData();
   }
}
