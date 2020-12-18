package com.junior.blog.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.junior.blog.common.CommonResult;
import com.junior.blog.domain.Blog;
import com.junior.blog.domain.Tag;
import com.junior.blog.service.BlogManageService;
import com.junior.blog.service.UserDetailsServiceImpl;

/**
 * 博客管理controller
 * 
 * @author xuduo
 *
 */
@RestController
@RequestMapping("blogManage")
public class BlogManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BlogManageController.class);

	public static final String IMG_PREFIX = "/showImage/";

	@Value("${com.junior.blog.imageSavePath:}")
	private String imageSavePath;

	@Autowired
	private BlogManageService service;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * 分页获取标签数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getDataPage")
	public CommonResult getDataPage(HttpServletRequest request, HttpServletResponse response, @RequestParam int page,
			@RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Tag> rtnList = service.getDataPage(dataMap);
		int count = service.getDataPageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 添加标签
	 * 
	 * @param request
	 * @param response
	 * @param tag
	 * @return
	 */
	@PostMapping("/insertTag")
	public CommonResult insertTag(HttpServletRequest request, HttpServletResponse response, @RequestBody Tag tag) {
		if (tag.getName() == null || "".equals(tag.getName())) {
			return CommonResult.failed("标签名称不能为空");
		}
		tag.setId(UUID.randomUUID().toString());
		tag.setAddtime(new Date().getTime());
		int result = service.insertTag(tag);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 修改标签
	 * 
	 * @param request
	 * @param response
	 * @param tag
	 * @return
	 */
	@PostMapping("/updateTag")
	public CommonResult updateTag(HttpServletRequest request, HttpServletResponse response, @RequestBody Tag tag) {
		if (tag.getName() == null || "".equals(tag.getName())) {
			return CommonResult.failed("标签名称不能为空");
		}
		int result = service.updateTag(tag);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 删除标签
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteTag")
	public CommonResult deleteTag(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
		int result = service.deleteTag(id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 分页获取博客信息
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/getBlogPage")
	public CommonResult getBlogPage(HttpServletRequest request, HttpServletResponse response, @RequestParam int page,
			@RequestParam int limit) {
		int start = limit * page - limit;
		Map dataMap = new HashMap();
		dataMap.put("start", start);
		dataMap.put("limit", limit);
		List<Blog> rtnList = service.getBlogPage(dataMap);
		int count = service.getBlogPageCount(dataMap);
		Map rtnMap = new HashMap();
		rtnMap.put("data", rtnList);
		rtnMap.put("count", count);
		rtnMap.put("code", 0);
		return CommonResult.success(rtnMap);
	}

	/**
	 * 发布博客
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@PostMapping("/publishBlog")
	public CommonResult publishBlog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) {
		int result = service.updateBlogStatus(id, Blog.YFB);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 取消发布博客
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@PostMapping("/unpublishBlog")
	public CommonResult unpublishBlog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) {
		int result = service.updateBlogStatus(id, Blog.WFB);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 删除博客
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteBlog")
	public CommonResult deleteBlog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) {
		int result = service.deleteBlog(id);
		if (result > 0) {
			return CommonResult.success();
		}
		return CommonResult.failed();
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadImage")
	public Map uploadImage(HttpServletRequest request, @RequestParam MultipartFile file) {
		String oriname = file.getOriginalFilename();
		String suffixName = oriname.substring(oriname.lastIndexOf("."));
		String fileName = UUID.randomUUID().toString() + suffixName;
		String filePath = imageSavePath + "\\" + fileName;
		File dest = new File(filePath);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		Map rtnMap = new HashMap();
		Map dataMap = new HashMap();
		dataMap.put("title", fileName);
		dataMap.put("src", IMG_PREFIX + fileName);
		rtnMap.put("msg", "上传成功");
		rtnMap.put("code", 0);
		rtnMap.put("data", dataMap);
		return rtnMap;
	}

	/**
	 * 插入博客
	 * 
	 * @param request
	 * @param blog
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/insertBlog")
	public CommonResult insertBlog(HttpServletRequest request, @Validated @RequestBody Blog blog,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		blog.setId(UUID.randomUUID().toString());
		blog.setAuthor(userDetailsServiceImpl.getCurrentUser().getId());
		Date curDate = new Date();
		blog.setAddtime(curDate.getTime());
		blog.setUpdatetime(curDate.getTime());
		int result = service.insertBlog(blog);
		if (result > 0) {
			CommonResult.failed();
		}
		return CommonResult.success(blog.getId());
	}

	/**
	 * 修改博客
	 * 
	 * @param request
	 * @param blog
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/updateBlog")
	public CommonResult updateBlog(HttpServletRequest request, @Validated @RequestBody Blog blog,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return CommonResult.checkError(bindingResult);
		}
		Date curDate = new Date();
		blog.setUpdatetime(curDate.getTime());
		int result = service.updateBlog(blog);
		if (result > 0) {
			CommonResult.failed();
		}
		return CommonResult.success();
	}
	
	/**
	 * 获取所有的标签
	 * @param request
	 * @return
	 */
	@GetMapping("/getAllTag")
	public CommonResult getAllTag(HttpServletRequest request) {
		List<Tag> rtnList = service.getAllTag();
		return CommonResult.success(rtnList);
	}
}
