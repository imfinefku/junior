/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.util;

import com.base.init.Constant;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * HTTP相关操作
 *
 * @author xuduo
 */
public class HttpUtils {

    /**
     * 方法说明：从request中获取文件
     *
     * @param request
     * @param baseFileName 需要保存的文件路径
     * @param hz 是否需要修改后缀名，true：改成原文件的后缀名。false：不改。
     * @return 大于0表示成功，小于0表示失败
     */
    public static int getFileFromRequest(HttpServletRequest request, String baseFileName) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 4); // 设置缓冲区大小，这里是4kb
        factory.setRepository(new File(Constant.webPath));// 设置缓冲区目录
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 100); // 设置最大文件尺寸，这里是10MB
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);// 得到所有的文件
        } catch (Exception ex) {
            request.setAttribute("chongfu", "上传失败，请减少上传文件大小!");
        }
        Iterator<FileItem> i = items.iterator();
        if (i.hasNext()) {
            FileItem fi = (FileItem) i.next();
            File tempFile = new File(baseFileName);
            try {
                fi.write(tempFile);
            } catch (Exception ex) {
                return -2;
            }
            return 1;
        } else {
            return -1;
        }
    }
    
    public static int downLoad(String path,String fileName,HttpServletResponse response) {
    	try {
    		//设置响应头
        	String contentType = new MimetypesFileTypeMap().getContentType(new File(path));
    		response.setContentType(contentType);
    		//设置该头，以附件形式打开下载，并防止中文文件名乱码
    		response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
    		//获取文件的输入流
    		FileInputStream in=new FileInputStream(new File(path));
    		OutputStream out= response.getOutputStream();
    		byte[] b=new byte[1024];
    		int len=0;
    		while((len=in.read(b))!=-1){
    			out.write(b,0,len);
    		}
    		in.close();
    		out.close();
    		return 1;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return -1;
    }
}
