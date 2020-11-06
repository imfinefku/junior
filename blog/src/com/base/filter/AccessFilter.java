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
import org.apache.log4j.Logger;
import com.base.bean.LoginInfo;

/**
 * 过滤器
 */
@WebFilter(filterName="/AccessFilter",urlPatterns = {"*.do","/manage/*"} )
public class AccessFilter implements Filter {

	private static Logger logger = Logger.getLogger(AccessFilter.class);
	
    
    public AccessFilter() {
        
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        httpresponse.setHeader("Access-Control-Allow-Origin", "*");//允许跨域
        httpresponse.setHeader("Access-Control-Allow-Headers", "Origin,No-Cache,X-Requested-With,Content-Type");
        LoginInfo account = (LoginInfo) httprequest.getSession().getAttribute("account");
        String urli = httprequest.getServletPath();
        if (account == null && !urli.endsWith("_sso.do")) {
            try {
                //未登录，跳到登录页面
                request.getRequestDispatcher("/login.jsp").forward(httprequest, httpresponse);
            } catch (Exception ex) {
                logger.error("过滤器出错", ex);
            }
        } else {//已登录,不过滤首页和js
            try {
                chain.doFilter(httprequest, httpresponse); //不用过滤
            } catch (Exception ex) {
                logger.error("过滤器出错", ex);
            }
        }
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
