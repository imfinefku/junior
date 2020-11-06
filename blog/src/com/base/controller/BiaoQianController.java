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

import com.base.bean.BiaoQian;
import com.base.bean.ResultMsg;
import com.base.bean.Title;
import com.base.service.BiaoQianService;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/biaoQianController.do")
public class BiaoQianController {

	private static Logger logger = Logger.getLogger(BiaoQianController.class);
	@Autowired
	BiaoQianService biaoQianService;

	/**
	 * 分页获取标签数据
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
	        List<BiaoQian> rtnList=biaoQianService.getData(dataMap);
	        int count=biaoQianService.getDataTotal(dataMap);
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
	 *添加标签数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=insert")
	public @ResponseBody 
	String insert(HttpServletRequest request, HttpServletResponse response) {
		try {
			BiaoQian bq=new BiaoQian();
			bq.setId(UUID.randomUUID().toString());
			bq.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			bq.setInserttime(new Date().getTime());
			biaoQianService.insert(bq);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("添加数据失败!");
	}
	
	/**
	 *删除标签数据
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
			biaoQianService.delete(map);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("删除数据失败!");
	}
	
	/**
	 *修改标签数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=update")
	public @ResponseBody 
	String update(HttpServletRequest request, HttpServletResponse response) {
		try {
			BiaoQian bq=new BiaoQian();
			bq.setId(request.getParameter("id")==null?"":request.getParameter("id").toString());
			bq.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			biaoQianService.update(bq);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("修改数据失败!");
	}
}
