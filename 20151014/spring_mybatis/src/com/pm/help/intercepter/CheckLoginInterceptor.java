package com.pm.help.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pm.system.entity.User;


/**
 * 用于登录拦截
 * @author admin
 *
 */
public class CheckLoginInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		//登录拦截
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			//还没有进行登录时
//			response.sendRedirect(request.getContextPath()+"/commons/login.jsp");
			response.sendRedirect("http://localhost:8080/spring_mybatis");
			return false;
		}else{
			return true;
		}
	}

}
