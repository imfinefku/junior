package com.base.dao;

import java.util.HashMap;
import java.util.List;
import com.base.bean.BiaoQian;
import com.base.bean.Text;
import com.base.bean.Title;

/**
 *
 * @author xuduo
 */
public interface TextDao {
	
	public List<HashMap> getTextFy(HashMap map);
	
	public int getTextFyTotal(HashMap map);
	
	public List<Title> getTextType();
	
    public void insertText(Text text);

    public void updateText(Text text);
    
    public HashMap getTextById(HashMap map);
    
    public void deleteText(HashMap map);
    
    public List<BiaoQian> getTag();
    
    public List<Text> getCarousel(HashMap map);
    
    public List<Text> getTop(HashMap map);
    
    public List<HashMap> getNew(HashMap map);
    
    public int getNewTotal(HashMap map);
    
    public List<HashMap> getDataByType(HashMap map);
    
    public int getDataByTypeTotal(HashMap map);
    
    public void updateTextDjl(HashMap map);
    
    public Text getLastById(HashMap map);
    
    public Text getNextById(HashMap map);
    
    public List<Text> getDjphData();
    
    public List<HashMap> getFyTextByFlOrSearch(HashMap map);
    
    public int getFyTextByFlOrSearchTotal(HashMap map);
}
