package com.base.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.base.bean.LoginInfo;
import com.base.bean.ResultMsg;
import com.base.bean.Title;
import com.base.init.Constant;
import com.base.service.BiaoTiService;
import com.base.service.LoginService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/login_sso.do")
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);
	
    @Autowired
    LoginService loginService;
	@Autowired
	BiaoTiService biaoTiService;

    /**	登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=login")
    public @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response) {
    	try {
            String username = request.getParameter("username") == null ? "" : request.getParameter("username");
            String password = request.getParameter("password") == null ? "" : request.getParameter("password");
            HashMap map = new HashMap();
            map.put("username", username);
            map.put("password", password);
            List<LoginInfo> list = loginService.login(map);
            if (list.size() > 0) {//登录成功
            	request.getSession().setAttribute("account", list.get(0));
                return "";
            }
    	}catch(Exception ex) {
    		logger.error(ex);
    	}
    	return "false";
    }
    
    /**	退出系统
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=logout")
    public @ResponseBody
    String logout(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		request.getSession().setAttribute("account", null);
		} catch (Exception ex) {
			logger.error(ex);
		}
    	return "";
    }
    
    /**修改密码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=updatePassword")
    public @ResponseBody
    String updatePassword(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String oldPassword=request.getParameter("oldPassword")==null?"":request.getParameter("oldPassword").toString();
    		String newPassword=request.getParameter("newPassword")==null?"":request.getParameter("newPassword").toString();
    		String renewPassword=request.getParameter("renewPassword")==null?"":request.getParameter("renewPassword").toString();
    		LoginInfo loginInfo=(LoginInfo)request.getSession().getAttribute("account");
    		if (!oldPassword.equals(loginInfo.getPassword())) {
    			return new ResultMsg().error("当前密码错误！");
    		}
    		if (!newPassword.equals(renewPassword)) {
    			return new ResultMsg().error("两次新密码不一致！");
    		}
    		loginInfo.setPassword(newPassword);
    		//修改密码
    		int i=loginService.updatePassword(loginInfo);
    		if (i>0) {//修改成功
    			//修改session
    			request.getSession().setAttribute("account", loginInfo);
        		return new ResultMsg().success();
    		}else {//修改失败
    			return new ResultMsg().error("修改失败！");
    		}
		} catch (Exception ex) {
			logger.error(ex);
		}
    	return new ResultMsg().error("修改失败！");
    }
    
	
	/**
	 *获取标题列表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getTitleList")
	public @ResponseBody 
	String getTitleList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Title> list=biaoTiService.getTitleList();
		    JsonConfig jsonConfig = new JsonConfig();
		    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		    String jsonArray = JSONArray.fromObject(list, jsonConfig).toString();
			Constant.titleList=jsonArray;
			return new ResultMsg(list).success();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResultMsg().error("操作失败");
	}
}
