package com.base.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.bean.Friend;
import com.base.bean.LiuYan;
import com.base.bean.LoginInfo;
import com.base.bean.ResultMsg;
import com.base.bean.Text;
import com.base.bean.Title;
import com.base.init.Constant;
import com.base.service.BiaoQianService;
import com.base.service.BiaoTiService;
import com.base.service.LiuYanService;
import com.base.service.LoginService;
import com.base.service.TextService;
import com.base.service.YouLianService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/view_sso.do")
public class ViewController {

	private static Logger logger = Logger.getLogger(ViewController.class);
	
	@Autowired
	TextService textService;
	@Autowired
	BiaoQianService biaoQianService;
	@Autowired
	YouLianService youLianService;
	@Autowired
	LiuYanService liuYanService;

    /**
     * 获取轮播数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getCarousel")
    public @ResponseBody
    String getCarousel(HttpServletRequest request) {
        String max=request.getParameter("max")==null?"3":request.getParameter("max").toString();
        HashMap map=new HashMap();
        map.put("max", max);
        List<Text> rtnList=textService.getCarousel(map);
        return new ResultMsg(rtnList).success();
    }
    
    /**
     * 获取顶置数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getTop")
    public @ResponseBody
    String getTop(HttpServletRequest request) {
        String max=request.getParameter("max")==null?"3":request.getParameter("max").toString();
        HashMap map=new HashMap();
        map.put("max", max);
        List<Text> rtnList=textService.getTop(map);
        return new ResultMsg(rtnList).success();
    }
    
    /**
     * 分页获取最新数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getNew")
    public @ResponseBody
    String getNew(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("cur"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = limit * page - limit;
        HashMap dataMap=new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<HashMap> rtnList=textService.getNew(dataMap);
        int total=textService.getNewTotal(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("total",total);
        return new ResultMsg(rtnMap).success();
    }
    
    /**
     * 获取标签数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getLabelCount")
    public @ResponseBody
    String getLabelCount(HttpServletRequest request) {
    	List<HashMap> rtnList=biaoQianService.getLabelCount();
    	return new ResultMsg(rtnList).success();
    }
    
    /**
     * 获取友情链接数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getYlData")
    public @ResponseBody
    String getYlData(HttpServletRequest request) {
    	List<Friend> rtnList=youLianService.getAllData();
    	return new ResultMsg(rtnList).success();
    }
    
    /**
     * 分页获取各分栏数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getDataByType")
    public @ResponseBody
    String getDataByType(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("cur"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String textType=request.getParameter("textType")==null?"":request.getParameter("textType").toString();
        int start = limit * page - limit;
        HashMap dataMap=new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        dataMap.put("textType", textType);
        List<HashMap> rtnList=textService.getDataByType(dataMap);
        int total=textService.getDataByTypeTotal(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("total",total);
        return new ResultMsg(rtnMap).success();
    }
    
    /**
     * 根据文章id获取文章信息
     *
     * @param request
     * @param response
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(params = "method=getTextById")
    public @ResponseBody
    String getTextById(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap dataMap = new HashMap();
        dataMap.put("id", id);
        HashMap text = textService.getTextById(dataMap);
        //获取上一篇和下一篇文章
        HashMap lastAndNext=textService.getLastAndNextById(text);
        //修改点击量
        textService.updateTextDjl(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("text", text);
        rtnMap.put("lastAndNext", lastAndNext);
        return new ResultMsg(rtnMap).success();
    }
    
    /**
     * 获取点击排行数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getDjphData")
    public @ResponseBody
    String getDjphData(HttpServletRequest request) {
    	List<Text> rtnList=textService.getDjphData();
    	return new ResultMsg(rtnList).success();
    }
    
    /**
     * 保存留言
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=insertLy",method = { RequestMethod.POST })
    public @ResponseBody
    String insertLy(HttpServletRequest request) {
    	String name=request.getParameter("name")==null?"":request.getParameter("name").toString();
    	String content=request.getParameter("content")==null?"":request.getParameter("content").toString();
    	String chunwenben=request.getParameter("chunwenben")==null?"":request.getParameter("chunwenben").toString();
    	String nameTest=name.replaceAll(" ", "");
    	String contentTest=content.replaceAll(" ", "");
    	contentTest=contentTest.replaceAll("&nbsp;", "");
    	contentTest=contentTest.replaceAll("\t", "");
    	String regex="^[a-zA-Z0-9\u4E00-\u9FA5]+$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher match=pattern.matcher(name);
    	boolean b=match.matches();
    	if (nameTest.equals("")) {
    		return new ResultMsg().error("名称不能为空！");
    	}
    	if (!b) {
    		return new ResultMsg().error("名称只能包含中文英文和数字！");
    	}
    	if (name.length()>10) {
    		return new ResultMsg().error("名称过长！");
    	}
    	if (chunwenben.length()>100) {
    		return new ResultMsg().error("文本内容过长！");
    	}
    	if (content.length()>1000) {
    		return new ResultMsg().error("内容过长！");
    	}
    	if (contentTest.equals("")) {
    		return new ResultMsg().error("内容不能为空！");
    	}
    	String sstext=request.getParameter("sstext")==null?"":request.getParameter("sstext").toString();
    	String ssly=request.getParameter("ssly")==null?"":request.getParameter("ssly").toString();
    	String lytype=request.getParameter("lytype")==null?"":request.getParameter("lytype").toString();
    	if ("".equals(lytype)) {
    		return new ResultMsg().error("留言失败！");
    	}
    	String type=request.getParameter("type")==null?"":request.getParameter("type").toString();
    	if ("".equals(type)) {
    		return new ResultMsg().error("留言失败！");
    	}
    	LiuYan ly=new LiuYan();
    	ly.setId(UUID.randomUUID().toString());
    	ly.setName(name);
    	ly.setContent(content);
    	ly.setLytype(Integer.parseInt(lytype));
    	ly.setType(Integer.parseInt(type));
    	ly.setSsly(ssly);
    	ly.setSstext(sstext);
    	ly.setTime(new Date().getTime());
    	ly.setIp(request.getRemoteAddr());
    	liuYanService.insertLy(ly);
    	return new ResultMsg().success();
    }
    
    /**
     * 分页获取留言板数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getLybFy")
    public @ResponseBody
    String getLybFy(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("cur"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = limit * page - limit;
        HashMap dataMap=new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<HashMap> rtnList=liuYanService.getLybFy(dataMap);
        int total=liuYanService.getLybFyTotal(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("total",total);
        return new ResultMsg(rtnMap).success();
    }
    
    /**
     * 赞评论
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=zanPingLun")
    public @ResponseBody
    String zanPingLun(HttpServletRequest request) {
    	String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
    	HashMap map=new HashMap();
    	map.put("id", id);
    	liuYanService.zanPingLun(map);
    	return new ResultMsg().success();
    }
    
    /**
     * 踩评论
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=caiPingLun")
    public @ResponseBody
    String caiPingLun(HttpServletRequest request) {
    	String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
    	HashMap map=new HashMap();
    	map.put("id", id);
    	liuYanService.caiPingLun(map);
    	return new ResultMsg().success();
    }
    
    /**
     * 分页获取文章留言数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getTextLyFy")
    public @ResponseBody
    String getTextLyFy(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("cur"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String id=request.getParameter("id");
        int start = limit * page - limit;
        HashMap dataMap=new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        dataMap.put("id", id);
        List<HashMap> rtnList=liuYanService.getTextLyFy(dataMap);
        int total=liuYanService.getTextLyFyTotal(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("total",total);
        return new ResultMsg(rtnMap).success();
    }
    
    /**
     * 获取首页最新留言数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getNewLy")
    public @ResponseBody
    String getNewLy(HttpServletRequest request) {
    	List<HashMap> rtnList=liuYanService.getNewLy();
    	return new ResultMsg(rtnList).success();
    }
    
    /**
     * 根据文章分类或搜索信息分页获取文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getFyTextByFlOrSearch")
    public @ResponseBody
    String getFyTextByFlOrSearch(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("cur"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = limit * page - limit;
    	String content=request.getParameter("content")==null?"":request.getParameter("content").toString();
    	String tag=request.getParameter("tag")==null?"":request.getParameter("tag").toString();
    	HashMap map=new HashMap();
    	map.put("content", "'%"+content+"%'");
    	map.put("tag", tag);
    	map.put("start", start);
    	map.put("limit", limit);
        List<HashMap> rtnList=textService.getFyTextByFlOrSearch(map);
        int total=textService.getFyTextByFlOrSearchTotal(map);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("total",total);
        return new ResultMsg(rtnMap).success();
    }
}
