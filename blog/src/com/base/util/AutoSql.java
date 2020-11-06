package com.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.base.bean.XmlBean;
import com.base.filter.AccessFilter;
import com.base.init.Constant;
/**
 * 自动更新数据库类
 * @author xd
 *
 */
public class AutoSql {

	private static Logger logger = Logger.getLogger(AccessFilter.class);
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String version = "CREATE TABLE `version` (`version` varchar(10) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	public AutoSql() {
		// 注册 jdbc驱动，连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constant.dbUrl, Constant.dbUsername, Constant.dbPassword);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			logger.error("未发现驱动链接jar包！");
		} catch (SQLException e) {
			logger.error("数据库连接失败！");
		}
	}

	// 查看数据库版本表是否存在,不存在就建表
	public void searchVersion() {
		try {
			rs = stmt.executeQuery("select count(*) num from information_schema.TABLES t " + "where t.TABLE_SCHEMA ='"
					+ Constant.dbName + "' and t.TABLE_NAME ='version'");
			while (rs.next()) {
				String num = rs.getString("num");
				if (Integer.parseInt(num) == 0) {// 没有版本表，建表
					logger.info(Constant.dbName + "数据库中不存在version表，创建！");
					int i = stmt.executeUpdate(version);
					if (i == 0) {
						stmt.executeUpdate("insert into version (version) values('0')");
						logger.info("version表已经创建成功，当前版本号为0！");
					}
				}
				break;
			}
		} catch (SQLException e) {
			logger.error("查看数据库版本表是否存在失败！");
		}
	}

	// 对比版本号，更新数据库
	public void sqlUpdate() {
		List<XmlBean> beanList = new ArrayList<XmlBean>();
		String curVersion = "0";
		try {
			JdomUtil ju = new JdomUtil(Constant.webPath + "sqlupdate.xml");
			// 获取所有数据库建表语句
			beanList = ju.read("update");
			if (beanList.size() == 0) {
				logger.info("无sql语句需要更新！");
				return;
			}
			// 获取当前版本号
			rs = stmt.executeQuery("select version from version");
			rs.next();
			curVersion = rs.getString("version");
		} catch (Exception ex) {
			logger.error(ex);
		}
		// 获取建表语句中最大的版本号,找出需要更新的语句并排序
		List<XmlBean> updateList = new ArrayList<XmlBean>();
		int maxVersion = 0;
		for (XmlBean xml : beanList) {
			int ver = Integer.parseInt(xml.getParameterMap().get("version").toString());
			if (ver > Integer.parseInt(curVersion)) {
				updateList.add(xml);
			}
			if (ver > maxVersion) {
				maxVersion = ver;
			}
		}
		if (updateList.size() == 0) {// 当前版本无需更新
			logger.info("当前数据库版本号为：" + curVersion + ",当前需要更新的sql最大版本号为：" + maxVersion + ",无需更新！");
		} else {
			// 将需要更新的sql按版本号排序，更新至数据库。
			sortSql(updateList);
			for (XmlBean b : updateList) {
				try {
					stmt.executeUpdate(b.getContent());
					logger.info(b.getContent());
					stmt.executeUpdate("delete from version");
					stmt.executeUpdate("insert into version (version) values('" + maxVersion + "')");
					logger.info("数据库版本从" + curVersion + "升级到" + maxVersion + ",当前版本号为" + maxVersion);
				} catch (Exception ex) {
					logger.error(b.getContent() + ",创建失败！" + ex);
				}
			}
		}

	}

	private void sortSql(List<XmlBean> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - 1 - i; j++) {
				int curVer = Integer.parseInt(list.get(j).getParameterMap().get("version").toString());
				int nexVer = Integer.parseInt(list.get(j + 1).getParameterMap().get("version").toString());
				if (curVer > nexVer) {
					XmlBean temp = list.get(j);
					list.add(j, list.get(j + 1));
					list.add(j + 1, temp);
				}
			}
		}
	}

	// 完成后关闭
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("关闭数据库连接失败！");
		}

	}
}
