package com.junior.blog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.junior.blog.domain.Friend;
import com.junior.blog.domain.FriendApply;
import com.junior.blog.domain.Subscribe;
import com.junior.blog.service.SystemManageService;

/**
 * 系统管理controller
 * 
 * @author xuduo
 *
 */
@RestController
@RequestMapping("system")
public class SystemManageController {

	@Autowired
	private SystemManageService service;

	/**
	 * 分页获取订阅信息
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getSubscribePage")
	public CommonResult getSubscribePage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int page, @RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Subscribe> rtnList = service.getSubscribePage(dataMap);
		int count = service.getSubscribePageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 删除订阅信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteSubscribe")
	public CommonResult deleteSubscribe(HttpServletRequest request, @RequestParam("id") String id) {
		int result = service.deleteSubscribe(id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 插入订阅信息
	 * 
	 * @param request
	 * @param subscribe
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/insertSubscribe")
	public CommonResult insertSubscribe(HttpServletRequest request, @Validated @RequestBody Subscribe subscribe,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		subscribe.setId(UUID.randomUUID().toString());
		Date curDate = new Date();
		subscribe.setAddtime(curDate.getTime());
		int result = service.insertSubscribe(subscribe);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 修改订阅信息
	 * 
	 * @param request
	 * @param subscribe
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/updateSubscribe")
	public CommonResult updateSubscribe(HttpServletRequest request, @Validated @RequestBody Subscribe subscribe,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		int result = service.updateSubscribe(subscribe);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 分页获取友链申请数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getFriendApplyPage")
	public CommonResult getFriendApplyPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int page, @RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<FriendApply> rtnList = service.getFriendApplyPage(dataMap);
		int count = service.getFriendApplyPageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 通过友链申请
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/passFriendApply")
	public CommonResult passFriendApply(HttpServletRequest request, @RequestParam("id") String id) {
		if (service.passFriendApply(id)) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 拒绝友链申请
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/refuseFriendApply")
	public CommonResult refuseFriendApply(HttpServletRequest request, @RequestParam("id") String id) {
		int result = service.updateFriendApplyStatus(FriendApply.REFUSE, id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 分页获取友情链接数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getFriendPage")
	public CommonResult getFriendPage(HttpServletRequest request, HttpServletResponse response, @RequestParam int page,
			@RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Friend> rtnList = service.getFriendPage(dataMap);
		int count = service.getFriendPageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 删除友链
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteFriend")
	public CommonResult deleteFriend(HttpServletRequest request, @RequestParam("id") String id) {
		int result = service.deleteFriend(id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 启用友链
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/startUseFriend")
	public CommonResult startUseFriend(HttpServletRequest request, @RequestParam("id") String id) {
		int result = service.updateFriendStatus(Friend.USE, id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 禁用友链
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping("/unUseFriend")
	public CommonResult unUseFriend(HttpServletRequest request, @RequestParam("id") String id) {
		int result = service.updateFriendStatus(Friend.UNUSE, id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 添加友链
	 * 
	 * @param request
	 * @param friend
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/insertFriend")
	public CommonResult insertFriend(HttpServletRequest request, @Validated @RequestBody Friend friend,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		friend.setId(UUID.randomUUID().toString());
		friend.setAddtime(new Date().getTime());
		friend.setOrders(service.findFriendMaxOrders());
		int result = service.insertFriend(friend);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 修改友链
	 * 
	 * @param request
	 * @param friend
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/updateFriend")
	public CommonResult updateFriend(HttpServletRequest request, @Validated @RequestBody Friend friend,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		int result = service.updateFriend(friend);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 获取友链最大排序
	 * 
	 * @return
	 */
	@GetMapping("/findFriendMaxOrders")
	public CommonResult findFriendMaxOrders() {
		return CommonResult.success(service.findFriendMaxOrders());
	}
}
