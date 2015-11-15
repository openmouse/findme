package com.kelansi.findme.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kelansi.findme.domain.User;
import com.kelansi.findme.user.service.UserService;
import com.kelansi.findme.utils.EncodingUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public String changePwd(Long id, String oldPwd, String newPwd, String checkPwd, HttpServletRequest request){
		if(id == null || StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd) || StringUtils.isBlank(checkPwd)){
			return "redirect:changePwdView.htm?rtnMessage=" + EncodingUtils.encode("参数错误");
		}
		if(!checkPwd.equals(newPwd)){
			return "redirect:changePwdView.htm?rtnMessage=" + EncodingUtils.encode("新密码两次输入不一致");
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(User.USER_SESSION_ATTR);
		if(!oldPwd.equals(user.getPassword())){
			return "redirect:changePwdView.htm?rtnMessage=" + EncodingUtils.encode("当前密码输入错误");
		}
		userService.changePwd(id, newPwd);
		return "redirect:changePwdView.htm?rtnMessage=" + EncodingUtils.encode("操作成功");
	}
	
	@RequestMapping(value = "/changePwdView", method = RequestMethod.GET)
	public String view(HttpServletRequest request, String rtnMessage, ModelMap map){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(User.USER_SESSION_ATTR);
		map.put("userId", user.getId());
		map.put("username", user.getUsername());
		if(rtnMessage != null){
			rtnMessage = EncodingUtils.decode(rtnMessage);
		}else{
			rtnMessage = "";
		}
		map.put("rtnMessage", rtnMessage);
		return "/user/changePwdView";
	}
}
