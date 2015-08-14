package com.kelansi.findme.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kelansi.findme.domain.Admin;

public class LoginInteceptor extends HandlerInterceptorAdapter {

	@Value("${url.logout.to}")
	private String logoutTo;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO 
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute(Admin.ADMIN_SESSION_ATTR) != null){
			return true;
		}else{
			return redirect(request, response);
		}
	}

	private boolean redirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		} else {
			response.sendRedirect(request.getContextPath() + logoutTo);
			return false;
		}
	}
}
