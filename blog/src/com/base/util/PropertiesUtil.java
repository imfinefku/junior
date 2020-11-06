/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * properties工具类
 *
 * @author xuduo
 */
public class PropertiesUtil {

    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    private Properties properties;//properties对象
    private FileInputStream in;//输入流，读
    private FileOutputStream out;//输出流，写
    private String path;//properties地址

    //初始化
    public PropertiesUtil(String path) {
        try {
            this.path = path;
            properties = new Properties();
            in = new FileInputStream(this.path);
            properties.load(in);
        } catch (Exception ex) {
            logger.error("properties初始化出错", ex);
        }
    }

    //获取参数
    public String getValue(String key) {
        return properties.getProperty(key)==null?"":properties.getProperty(key);
    }

    //写参数
    public void setKeyValue(String key, String value) {
        try {
            if (out == null) {
                out = new FileOutputStream(path);
            }
        } catch (FileNotFoundException ex) {
            logger.error("properties写参数出错", ex);
        }
        properties.setProperty(key, value);
        try {
            properties.store(out, "key:" + key + " value:" + value + ",path:" + path);
        } catch (Exception ex) {
            logger.error("key:" + key + " value:" + value + ",path:" + path, ex);
        }
    }

    //操作完毕，释放资源
    public void close() {
        try {
            if (in != null) {
                in.close();
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (properties != null) {
                properties = null;
            }
        } catch (IOException ex) {
            logger.error("properties释放资源出错", ex);
        }
    }
}
