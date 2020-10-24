package com.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 返回数据类对象
 * 
 * @author xd
 * @date 2020-3-1
 * @version 1.0.0
 * @since jdk1.8
 */
public class ResultMsg {

	/** 成功代码 */
	private final static int SUCCESS = 0;
	/** 失败代码 */
	private final static int ERROR = -1;
	/** 字段检查失败代码 */
	private final static int FIELDERROR = -2;
	/** 错误代码 */
	private int errcode;
	/** 错误消息 */
	private String errmsg;
	/** 跳转地址 */
	private String location;

	/** 具体返回结果，可能是成功的数据，也可能是错误的具体内容 **/
	private Object result;
	
	public ResultMsg() {};

	public ResultMsg(Object result) {
		this.result = result;
	}

	public ResultMsg success() {
		this.errcode = SUCCESS;
		return this;
	}
	
	public ResultMsg success(String location) {
		this.errcode = SUCCESS;
		this.location=location;
		return this;
	}

	public ResultMsg error(String errmsg) {
		this.errcode = ERROR;
		this.errmsg = errmsg;
		return this;
	}

	/**
	 * 检查字段返回
	 * 
	 * @param bindingResult
	 * @return
	 */
	public ResultMsg checkMsg(BindingResult bindingResult) {
		this.errcode = FIELDERROR;
		if (bindingResult != null && bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
			List<String> errormsg = new ArrayList<String>();
			Map<String, String> msg = new HashMap<String, String>();
			for (FieldError error : list) {
				errormsg.add(error.getField() + ":" + error.getDefaultMessage());
				msg.put(error.getField(), error.getDefaultMessage());
			}
			this.result = msg;

		} else {
			this.result = "";
		}
		return this;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
