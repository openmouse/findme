package com.kelansi.findme.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelansi.findme.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@RequestMapping(value="/login")
	public String login(){
		return "/login/login";
	}
	
	@RequestMapping(value="/index")
	public String index(){
		return "index";
	}
}
