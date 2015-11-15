package com.kelansi.findme.logout.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelansi.findme.domain.User;

@Controller
public class LogoutController {

	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute(User.USER_SESSION_ATTR);
		return "redirect:/login.htm";
	}
	
}
