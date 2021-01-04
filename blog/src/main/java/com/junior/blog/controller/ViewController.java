package com.junior.blog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.junior.blog.common.CommonResult;
import com.junior.blog.domain.Blog;
import com.junior.blog.domain.Friend;
import com.junior.blog.domain.FriendApply;
import com.junior.blog.domain.Subscribe;
import com.junior.blog.domain.Tag;
import com.junior.blog.service.BlogManageService;
import com.junior.blog.service.SystemManageService;

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

	@Autowired
	private SystemManageService systemManageService;

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
		List<Blog> rtnList = blogManageService.getViewBlogPage(dataMap);
		int count = blogManageService.getViewBlogPageCount(dataMap);
		return CommonResult.success(rtnList, count);
	}

	/**
	 * 根据id获取博客信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getBlogById")
	public CommonResult getBlogById(@RequestParam String id) {
		boolean nextFlag = false;
		Blog blog = blogManageService.getBlogById(id);
		// 获取博客的上一篇和一下篇
		List<Blog> list = blogManageService.getLastAndNext(id);
		for (Blog b : list) {
			if (b.getId().equals(blog.getId())) {
				nextFlag = true;
				continue;
			}
			if (!nextFlag) {
				blog.setLast(b.getId());
				blog.setLastTitle(b.getTitle());
			} else {
				blog.setNext(b.getId());
				blog.setNextTitle(b.getTitle());
			}
		}
		return CommonResult.success(blog);
	}

	/**
	 * 增加点击量
	 * 
	 * @param id
	 */
	@PostMapping("/addHits")
	public void addHits(@RequestParam String id) {
		blogManageService.addHits(id);
	}

	/**
	 * 为博客点赞
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/addLikeNum")
	public CommonResult addLikeNum(@RequestParam("id") String id) {
		int result = blogManageService.addLikeNum(id);
		if (result > 0) {
			CommonResult.failed();
		}
		return CommonResult.success();
	}

	/**
	 * 获取标签
	 * 
	 * @return
	 */
	@GetMapping("/getViewTags")
	public CommonResult getViewTags() {
		List<Tag> rtnList = blogManageService.getViewTags();
		return CommonResult.success(rtnList);
	}

	/**
	 * 获取首页友情连接
	 * 
	 * @return
	 */
	@GetMapping("/getViewFriends")
	public CommonResult getViewFriends() {
		List<Friend> rtnList = blogManageService.getViewFriends();
		return CommonResult.success(rtnList);
	}

	/**
	 * 首页订阅
	 * 
	 * @param request
	 * @param subscribe
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/viewSubscribe")
	public CommonResult viewSubscribe(HttpServletRequest request, @Validated @RequestBody Subscribe subscribe,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		subscribe.setId(UUID.randomUUID().toString());
		Date curDate = new Date();
		subscribe.setAddtime(curDate.getTime());
		int result = systemManageService.insertSubscribe(subscribe);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 添加友链申请
	 * 
	 * @param request
	 * @param friend
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/addFriendAapply")
	public CommonResult addFriendAapply(HttpServletRequest request, @Validated @RequestBody FriendApply friendApply,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		friendApply.setId(UUID.randomUUID().toString());
		friendApply.setApplytime(new Date().getTime());
		int result = systemManageService.insertFriendApply(friendApply);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}
}
