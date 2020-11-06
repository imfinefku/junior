package com.base.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 字符编码转换过滤器
 */
public class MyCharacterEncodingFilter implements Filter {

	// 定义编码方式 默认为空
	private String encoding = null;

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		// 将web.xml中的过滤器的配置编码取出来赋值给 encoding
		encoding = config.getInitParameter("encoding");
	}

	// 自动调用过滤方法 doFilter
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		// 如果编码不为空就过滤请求中的数据
		if (encoding != null) {
			// 设置request字符编码
			httprequest.setCharacterEncoding(encoding);
			// 设置response字符编码
			httpresponse.setContentType("text/html;charset=" + encoding);
		}
		// 传递给下一个过滤器或者下一个servlet/jsp
		// 将request变成增强类传递下去，防止get参数中文乱码
		MyRequest myRequest = new MyRequest(httprequest);
		chain.doFilter(myRequest, response);
	}

}
