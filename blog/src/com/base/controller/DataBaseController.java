package com.base.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.bean.ResultMsg;
import com.base.init.Constant;
import com.base.util.DataBaseCopy;
import com.base.util.HttpUtils;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/dataBaseController.do")
public class DataBaseController {

	private static Logger logger = Logger.getLogger(DataBaseController.class);

	/**
	 * 获取数据库备份数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getDataCopy")
	public @ResponseBody String getDataCopy(HttpServletRequest request, HttpServletResponse response) {
		try {
			File file = new File(Constant.dataBaseCopy);
			if (file.exists()) {
				File[] files = file.listFiles();
				List<Map> rtnList = new ArrayList<Map>();
				for (File f : files) {
					HashMap m = new HashMap();
					m.put("name", f.getName());
					m.put("path", f.getPath());
					rtnList.add(m);
				}
				return new ResultMsg(rtnList).success();
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("获取数据失败!");
	}

	/**
	 * 数据库备份
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=dataBaseCopy")
	public @ResponseBody String dataBaseCopy(HttpServletRequest request, HttpServletResponse response) {
		try {
			DataBaseCopy.dataBaseCopy(Constant.dataBaseInstall, Constant.dataBaseCopy, Constant.dbUsername,
					Constant.dbPassword, Constant.dataBaseInstallIp, Constant.dbName);
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("数据库备份失败!");
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=delete")
	public @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getParameter("path") == null ? "" : request.getParameter("path").toString();
			File file = new File(path);
			file.delete();
			return new ResultMsg().success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("删除失败!");
	}

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=downLoad")
	public @ResponseBody String downLoad(HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileName = request.getParameter("fileName") == null ? ""
					: request.getParameter("fileName").toString();
			int i = HttpUtils.downLoad(Constant.dataBaseCopy + fileName, fileName, response);
			if (i > 0) {// 下载成功
				return new ResultMsg().success();
			} else {
				return new ResultMsg().error("下载失败!");
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("下载失败!");
	}
}
