package com.base.bean;

import java.util.HashMap;

public class XmlBean {
	private HashMap parameterMap;//节点属性
	private String content;//节点值
	
	public XmlBean() {
	}
	
	public HashMap getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(HashMap parameterMap) {
		this.parameterMap = parameterMap;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
