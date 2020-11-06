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
import com.base.bean.Friend;
import com.base.bean.ResultMsg;
import com.base.service.YouLianService;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/youLianController.do")
public class YouLianController {

	private static Logger logger = Logger.getLogger(YouLianController.class);
	@Autowired
	YouLianService youLianService;

	/**
	 * 分页获取友链数据
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
	        List<Friend> rtnList=youLianService.getData(dataMap);
	        int count=youLianService.getDataTotal(dataMap);
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
	 *添加友链数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=insert")
	public @ResponseBody 
	String insert(HttpServletRequest request, HttpServletResponse response) {
		try {
			Friend friend=new Friend();
			friend.setId(UUID.randomUUID().toString());
			friend.setInserttime(new Date().getTime());
			friend.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			friend.setUrl(request.getParameter("url")==null?"":request.getParameter("url").toString());
			friend.setRemarks(request.getParameter("remarks")==null?"":request.getParameter("remarks").toString());
			friend.setSort(request.getParameter("sort")==null?0:Integer.parseInt(request.getParameter("sort").toString()));
			youLianService.insert(friend);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("添加数据失败!");
	}
	
	/**
	 *删除友链数据
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
			youLianService.delete(map);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("删除数据失败!");
	}
	
	/**
	 *修改友链数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=update")
	public @ResponseBody 
	String update(HttpServletRequest request, HttpServletResponse response) {
		try {
			Friend friend=new Friend();
			friend.setId(request.getParameter("id")==null?"":request.getParameter("id").toString());
			friend.setName(request.getParameter("name")==null?"":request.getParameter("name").toString());
			friend.setUrl(request.getParameter("url")==null?"":request.getParameter("url").toString());
			friend.setRemarks(request.getParameter("remarks")==null?"":request.getParameter("remarks").toString());
			friend.setSort(request.getParameter("sort")==null?0:Integer.parseInt(request.getParameter("sort").toString()));
			youLianService.update(friend);
	        return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("修改数据失败!");
	}
	
}
