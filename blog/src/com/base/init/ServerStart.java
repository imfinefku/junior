package com.base.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

import com.base.util.AutoSql;
import com.base.util.DataBaseCopy;

/**
 * 服务初始化
 *
 * @author xuduo
 */
public class ServerStart implements ServletContextListener {

    private static Logger logger = Logger.getLogger(ServerStart.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("----------服务启动----------");
        //初始化web服务路径
        Constant.init(sce.getServletContext().getRealPath("/"), sce.getServletContext().getRealPath("/config.properties"));//初始化全局变量，获取项目路径
        //更新数据库
        updateSql();
//        new DataBaseCopy().dataBaseCopy(Constant.dataBaseInstall, Constant.dataBaseCopy, Constant.dbUsername, 
//        		Constant.dbPassword, Constant.dataBaseInstallIp, Constant.dbName);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("----------服务关闭----------");
    }

    //更新数据库
    private void updateSql() {
    	AutoSql as=new AutoSql();
    	as.searchVersion();
    	as.sqlUpdate();
    	as.close();
    }
}
