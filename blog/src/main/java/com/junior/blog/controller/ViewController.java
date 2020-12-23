package com.junior.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.junior.blog.common.CommonResult;
import com.junior.blog.domain.Blog;
import com.junior.blog.service.BlogManageService;

/**
 * 前端显示接口
 * 
 * @author xuduo
 *
 */
@RestController
@RequestMapping("view")
public class ViewController {

	@Autowired
	private BlogManageService blogManageService;

	/**
	 * 分页获取首页博客信息
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getIndexBlogPage")
	public CommonResult getIndexBlogPage(@RequestParam int page, @RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Blog> rtnList = blogManageService.getBlogPage(dataMap);
		int count = blogManageService.getBlogPageCount(dataMap);
		return CommonResult.success(rtnList, count);
	}

	/**
	 * 根据id获取博客信息
	 * @param id
	 * @return
	 */
	@GetMapping("/getBlogById")
	public CommonResult getBlogById(@RequestParam String id) {
		Blog blog = blogManageService.getBlogById(id);
		return CommonResult.success(blog);
	}
}
