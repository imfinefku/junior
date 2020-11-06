package com.base.bean;


import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 接口返回类
 * @author xd
 *
 */
public class ResultMsg{

	private int errorCode=0;//0：操作成功，1：操作失败
	private String errorMsg="操作成功";
	public Object result;
	
	public ResultMsg() {
	}
	
	public ResultMsg(Object result) {
		this.result=result;
	}
	
	public String success() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject json =JSONObject.fromObject(this, jsonConfig);
		return json.toString();
	}
	
	public String error(String errorMsg) {
		this.errorCode=1;
		this.errorMsg=errorMsg;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject json =JSONObject.fromObject(this, jsonConfig);
		return json.toString();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
