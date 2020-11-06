package com.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

import com.base.init.Constant;

/**
 * 数据库备份类
 * 
 * @author znxd
 *
 */
public class DataBaseCopy {

	public DataBaseCopy() {
	}

	/**
	 * 数据库备份
	 */
	public static HashMap dataBaseCopy(String dataBasePath, String copyPath, String username, String password,
			String ip, String dataBaseName) {
		HashMap rtnMap = new HashMap();
		File dataFile = new File(dataBasePath);
		if (!dataFile.exists()) {// 如果数据库目录不存在
			rtnMap.put("errorCode", "1");
			rtnMap.put("errorMsg", "数据库目录不存在,备份失败！");
			return rtnMap;
		}
		File saveFile = new File(copyPath);
		if (!saveFile.exists()) {// 如果保存目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		try {
			String cmd = "";
			if (Constant.os.toLowerCase().equals("windows")) {// windows操作系统下进行备份
				Runtime rt = Runtime.getRuntime();
				// 调用 调用mysql的安装目录的命令
				cmd = "cmd /c " + dataBasePath + "bin/mysqldump -h " + ip + " -u" + username + " -p" + password + " "
						+ dataBaseName + " > " + copyPath + dataBaseName + "_" + new Date().getTime() + ".sql";
				Process child = rt.exec(cmd);
				// 等待指令完成后返回
				child.waitFor();
				System.out.println("执行" + Constant.os + "命令[ " + cmd + "]");
				rtnMap.put("errorCode", "0");
				rtnMap.put("errorMsg", "操作成功");
			} else if (Constant.os.toLowerCase().equals("linux")) {// linux操作系统下进行备份
				String[] licmd = new String[] { "/usr/bin/sh", "-c", "/usr/bin/mysqldump -u" + username + " -p" + password
						+ "  " + dataBaseName + " > " + copyPath + dataBaseName + "_" + new Date().getTime() + ".sql" };
				executeLinuxCmd(licmd);
				rtnMap.put("errorCode", "0");
				rtnMap.put("errorMsg", "操作成功");
			} else {
				rtnMap.put("errorCode", "1");
				rtnMap.put("errorMsg", "未配置操作系统类型或配置错误！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			rtnMap.put("errorCode", "1");
			rtnMap.put("errorMsg", "数据库备份失败！");
		}
		return rtnMap;
	}

	/**
	 * 执行linux命令
	 * 
	 * @param cmd
	 * @return
	 */
	public static String executeLinuxCmd(String[] cmd) {
		try {
			System.out.println("执行" + Constant.os + "命令[ " + cmd[cmd.length - 1] + "]");
			Process process = Runtime.getRuntime().exec(cmd);
//			String line;
//			BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			StringBuffer out = new StringBuffer();
//			while ((line = stdoutReader.readLine()) != null) {
//				out.append(line);
//			}
//			try {
				process.waitFor();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			process.destroy();
//			return out.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
