package com.pm.help.intercepter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * @ author pengsong
 * @ 功能：动作拦截器
 * @ 时间 2015年8月13日
 * @ 版权：北京网景信息技术有限公司
 */
public class ActionFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest  servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String uri = request.getRequestURI().trim();
		request.getSession().setAttribute("path", uri);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
