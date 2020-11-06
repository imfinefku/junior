/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.init;

import com.base.util.PropertiesUtil;

/**
 *
 * @author xuduo
 */
public class Constant {

    public static String webPath = "";//web服务路径
    public static String configPath = "";//配置文件路径
    public static String dbUrl = "";//数据库url
    public static String dbName = "";//数据库名
    public static String dbUsername = "";//数据库用户名
    public static String dbPassword = "";//数据库密码
    public static String uploadUrl = "";//图片上传路径
    public static String name = "脱北者博客";//系统名称
    public static String dataBaseInstall = "";//数据库安装位置
    public static String dataBaseInstallIp = "";//数据库安装ip
    public static String dataBaseCopy = "";//数据库备份位置
    public static String titleList = "";//title列表
    public static String os = "";//操作系统类型

    //初始化全局变量
    public static void init(String webpath, String path) {
        webPath = webpath;//初始化web服务路径
        configPath = path;
        PropertiesUtil pu = new PropertiesUtil(configPath);
        dbUrl = pu.getValue("dbUrl");
        dbName = pu.getValue("dbName");
        dbUsername = pu.getValue("dbUsername");
        dbPassword = pu.getValue("dbPassword");
        uploadUrl = pu.getValue("uploadImgUrl");
        dataBaseInstall = pu.getValue("dataBaseInstall");
        dataBaseInstallIp = pu.getValue("dataBaseInstallIp");
        dataBaseCopy = pu.getValue("dataBaseCopy");
        os = pu.getValue("os");
        pu.close();
    }
}
