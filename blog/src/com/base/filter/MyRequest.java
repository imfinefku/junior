package com.base.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * request加强类
 * @author xd
 *
 */
public class MyRequest extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		String method = request.getMethod();
		String value = null;
		try {
			value = request.getParameter(name);
			if (value!=null && "get".equalsIgnoreCase(method)) {
				value = new String(value.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}
}
