package com.base.controller;

import java.io.File;
import java.text.SimpleDateFormat;
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
import com.base.bean.LoginInfo;
import com.base.bean.ResultMsg;
import com.base.bean.Text;
import com.base.bean.Title;
import com.base.init.Constant;
import com.base.service.TextService;
import com.base.util.HttpUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/textController.do")
public class TextController {

	private static Logger logger = Logger.getLogger(TextController.class);
	@Autowired
	TextService textService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 分页获取文章数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=getTextFy")
	public @ResponseBody 
	String getTextFy(HttpServletRequest request, HttpServletResponse response) {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        int start = limit * page - limit;
        HashMap dataMap=new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<HashMap> rtnList=textService.getTextFy(dataMap);
        int count=textService.getTextFyTotal(dataMap);
        HashMap rtnMap=new HashMap();
        rtnMap.put("data", rtnList);
        rtnMap.put("count", count);
        return new ResultMsg(rtnMap).success();
	}
	
    /**
     * 上传图片
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=uploadImg")
    public @ResponseBody
    String uploadImg(HttpServletRequest request) {
        String catalog = sdf.format(new Date());
        String fileName = UUID.randomUUID().toString() + ".png";
        File root = new File(Constant.uploadUrl + catalog);
        if (!root.exists()) {//目录不存在，创建
            root.mkdirs();//全部创建
        }
        String baseFileName = Constant.uploadUrl + catalog + "/" + fileName;
        int result = HttpUtils.getFileFromRequest(request, baseFileName);
        HashMap rtnMap = new HashMap();
        if (result > 0) {//上传成功
            rtnMap.put("code", 0);
        } else {//上传失败
            rtnMap.put("code", result);
        }
        rtnMap.put("msg", "");
        HashMap dataMap = new HashMap();
        dataMap.put("src", "/Image/" + catalog + "/" + fileName);
        rtnMap.put("data", dataMap);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonobject = JSONObject.fromObject(rtnMap, jsonConfig).toString();
        return jsonobject;
    }
    
    /**
     * 获取文章类型，title数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getTextType")
    public @ResponseBody
    String getTextType(HttpServletRequest request) {
        List<Title> rtnList = textService.getTextType();
        return new ResultMsg(rtnList).success();
    }
    
    /**
     * 插入文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=insertText")
    public @ResponseBody
    String insertText(HttpServletRequest request) {
        LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("account");
        String title = request.getParameter("title") == null ? "" : request.getParameter("title");
        String introduce = request.getParameter("introduce") == null ? "" : request.getParameter("introduce");
        String image = request.getParameter("image") == null ? "" : request.getParameter("image");
        String textType = request.getParameter("textType") == null ? "" : request.getParameter("textType");
        String content = request.getParameter("content") == null ? "" : request.getParameter("content");
        int iftop = Integer.parseInt(request.getParameter("iftop"));
        int ifcarousel = Integer.parseInt(request.getParameter("ifcarousel"));
        String tag = request.getParameter("tag") == null ? "" : request.getParameter("tag");
        String author = "";
        if (loginInfo != null) {
            author = loginInfo.getId();
        } else {
            return new ResultMsg().error("操作失败");
        }
        Text text = new Text();
        text.setId(UUID.randomUUID().toString());
        text.setTitle(title);
        text.setImage(image);
        text.setIntroduce(introduce);
        text.setContent(content);
        text.setTextType(textType);
        text.setIftop(iftop);
        text.setIfcarousel(ifcarousel);
        text.setInsertTime(new Date().getTime());
        text.setDianji(0);
        text.setDianzan(0);
        text.setAuthor(author);
        text.setTag(tag);
        textService.insertText(text);
        return new ResultMsg(text).success();
    }

    /**
     * 修改文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=updateText")
    public @ResponseBody
    String updateText(HttpServletRequest request) {
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String title = request.getParameter("title") == null ? "" : request.getParameter("title");
        String introduce = request.getParameter("introduce") == null ? "" : request.getParameter("introduce");
        String image = request.getParameter("image") == null ? "" : request.getParameter("image");
        String textType = request.getParameter("textType") == null ? "" : request.getParameter("textType");
        String content = request.getParameter("content") == null ? "" : request.getParameter("content");
        int iftop = Integer.parseInt(request.getParameter("iftop"));
        int ifcarousel = Integer.parseInt(request.getParameter("ifcarousel"));
        String tag = request.getParameter("tag") == null ? "" : request.getParameter("tag");
        Text text = new Text();
        text.setId(id);
        text.setTitle(title);
        text.setImage(image);
        text.setIntroduce(introduce);
        text.setContent(content);
        text.setTextType(textType);
        text.setIftop(iftop);
        text.setIfcarousel(ifcarousel);
        text.setTag(tag);
        textService.updateText(text);
        return new ResultMsg().success();
    }
    
    /**
     * 根据id获取文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getTextById")
    public @ResponseBody
    String getTextById(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap dataMap = new HashMap();
        dataMap.put("id", id);
        HashMap text = textService.getTextById(dataMap);
        return new ResultMsg(text).success();
    }
    
    /**
     * 根据id删除文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=deleteText")
    public @ResponseBody
    String deleteText(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap dataMap = new HashMap();
        dataMap.put("id", id);
        textService.deleteText(dataMap);
        return new ResultMsg().success();
    }

    /**
     * 根据id删除文章
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=getTag")
    public @ResponseBody
    String getTag(HttpServletRequest request) {
        List<BiaoQian> rtnList=textService.getTag();
        return new ResultMsg(rtnList).success();
    }
}
