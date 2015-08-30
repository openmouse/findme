package com.kelansi.findme.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.domain.User;
import com.kelansi.findme.login.service.LoginService;
import com.kelansi.findme.rsa.service.RSAService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private RSAService rsaService;

	@RequestMapping(value="/login")
	public String login(){
		return "/login/login";
	}
	
	@RequestMapping(value="/submit")
	@ResponseBody
	public Message submit(String username, String password, HttpServletRequest request, HttpServletResponse response){
		
		User user = null;
		if((user = loginService.auth(username, password)) == null){
			return Message.error("login.auth.failure");
		}
		
		HttpSession session = request.getSession();
		
		session.setAttribute(User.USER_SESSION_ATTR, user);
		logger.info(user.getUsername() + "login success");
		return Message.SUCCESS_MESSAGE;
	}
	
	@RequestMapping(value="/index")
	public String index(){
		return "/index";
	}
}
