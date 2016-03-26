package com.kelansi.findme.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelansi.findme.login.service.LoginService;

/**
 * 
 * @author wenliangfan
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@RequestMapping(value="/index")
	public String index(){
		return "index";
	}
}
