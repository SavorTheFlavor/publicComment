package com.me.comment.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.me.comment.constant.PageCodeEnum;
import com.me.comment.constant.SessionKeyConst;

public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	//进入handler之后，返回之前
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws Exception {
		if(req.getSession().getAttribute(SessionKeyConst.USER_INFO)!=null){
			return true;
		}
		//判断是否是ajax请求
		if(req.getHeader("x-requested-with")!=null){
			String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
			resp.setHeader("sessionTimeoutUrl", basePath+"/login/sessionTimeout");
		}else{
			req.getRequestDispatcher("/login/sessionTimeout").forward(req, resp);
		}
		return false;
	}

}
