package com.base.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.base.bean.ResultMsg;
import com.base.bean.Title;
import com.base.init.Constant;
import com.base.service.BiaoTiService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/biaoTiController.do")
public class BiaoTiController {

	private static Logger logger = Logger.getLogger(BiaoTiController.class);
	@Autowired
	BiaoTiService biaoTiService;

	/**
	 * 分页获取标题数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getData")
	public @ResponseBody 
	String getData(HttpServletRequest request, HttpServletResponse response) {
		try {
	        int page = Integer.parseInt(request.getParameter("page"));
	        int limit = Integer.parseInt(request.getParameter("limit"));
	        int start = limit * page - limit;
	        HashMap dataMap=new HashMap();
	        dataMap.put("start", start);
	        dataMap.put("limit", limit);
	        List<Title> rtnList=biaoTiService.getData(dataMap);
	        int count=biaoTiService.getDataTotal(dataMap);
	        HashMap rtnMap=new HashMap();
	        rtnMap.put("data", rtnList);
	        rtnMap.put("count", count);
	        return new ResultMsg(rtnMap).success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("获取数据失败!");
	}
	
	/**
	 * 删除标题数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=delete")
	public @ResponseBody 
	String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
			HashMap map=new HashMap();
			map.put("id", id);
			biaoTiService.delete(map);
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("删除数据失败!");
	}

	/**
	 *添加标题数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=insertTitle")
	public @ResponseBody 
	String insertTitle(HttpServletRequest request, HttpServletResponse response) {
		try {
			Title title=new Title();
			title.setId(UUID.randomUUID().toString());
			title.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			title.setIntroduce(request.getParameter("introduce")==null?"":request.getParameter("introduce").toString());
			title.setPath(request.getParameter("path")==null?"":request.getParameter("path").toString());
			title.setSort(request.getParameter("sort")==null?0:Integer.parseInt(request.getParameter("sort").toString()));
			title.setInserttime(new Date().getTime());
			biaoTiService.insertTitle(title);
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("操作失败");
	}
	
	/**
	 *修改标题数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=updateTitle")
	public @ResponseBody 
	String updateTitle(HttpServletRequest request, HttpServletResponse response) {
		try {
			Title title=new Title();
			title.setId(request.getParameter("id")==null?"":request.getParameter("id").toString());
			title.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			title.setIntroduce(request.getParameter("introduce")==null?"":request.getParameter("introduce").toString());
			title.setPath(request.getParameter("path")==null?"":request.getParameter("path").toString());
			title.setSort(request.getParameter("sort")==null?0:Integer.parseInt(request.getParameter("sort").toString()));
			title.setInserttime(new Date().getTime());
			biaoTiService.updateTitle(title);
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("操作失败");
	}
	
	/**
	 *清理标题缓存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=clearTitle")
	public @ResponseBody 
	String clearTitle(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Title> list=biaoTiService.getTitleList();
		    JsonConfig jsonConfig = new JsonConfig();
		    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		    String jsonArray = JSONArray.fromObject(list, jsonConfig).toString();
			Constant.titleList=jsonArray;
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("操作失败");
	}
}
