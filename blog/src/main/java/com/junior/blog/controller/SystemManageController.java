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
}
