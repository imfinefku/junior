package com.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.bean.BiaoQian;
import com.base.bean.Text;
import com.base.bean.Title;
import com.base.dao.TextDao;

/**
*
* @author xuduo
*/
@Service("textService")
public class TextService {

   @Autowired
   public TextDao textDao;
   
   public List<HashMap> getTextFy(HashMap map){
	   return textDao.getTextFy(map);
   }
   
   public int getTextFyTotal(HashMap map){
	   return textDao.getTextFyTotal(map);
   }
   
   public List<Title> getTextType() {
       return textDao.getTextType();
   }
   
   public void insertText(Text text) {
       textDao.insertText(text);
   }

   public void updateText(Text text) {
       textDao.updateText(text);
   }
   
   public HashMap getTextById(HashMap map) {
       return textDao.getTextById(map);
   }
   
   public void deleteText(HashMap map) {
       textDao.deleteText(map);
   }
   
   public List<BiaoQian> getTag(){
	   return textDao.getTag();
   }
   
   public List<Text> getCarousel(HashMap map){
	   return textDao.getCarousel(map);
   }
   
   public List<Text> getTop(HashMap map){
	   return textDao.getTop(map);
   }
   
   public List<HashMap> getNew(HashMap map){
	   return textDao.getNew(map);
   }
   
   public int getNewTotal(HashMap map){
	   return textDao.getNewTotal(map);
   }
   
   public List<HashMap> getDataByType(HashMap map){
	   return textDao.getDataByType(map);
   }
   
   public int getDataByTypeTotal(HashMap map){
	   return textDao.getDataByTypeTotal(map);
   }
   
   public void updateTextDjl(HashMap map) {
	   textDao.updateTextDjl(map);
   }
   
   public HashMap getLastAndNextById(HashMap map) {
	   HashMap rtnMap=new HashMap();
	   rtnMap.put("last", textDao.getLastById(map));
	   rtnMap.put("next", textDao.getNextById(map));
	   return rtnMap;
   }
   
   public List<Text> getDjphData(){
	   return textDao.getDjphData();
   }
   
   public List<HashMap> getFyTextByFlOrSearch(HashMap map){
	   return textDao.getFyTextByFlOrSearch(map);
   }
   
   public int getFyTextByFlOrSearchTotal(HashMap map){
	   return textDao.getFyTextByFlOrSearchTotal(map);
   }
}
