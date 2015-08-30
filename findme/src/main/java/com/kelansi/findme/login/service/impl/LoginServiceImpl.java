package com.kelansi.findme.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.domain.User;
import com.kelansi.findme.login.dao.LoginMapper;
import com.kelansi.findme.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public User auth(String username, String password) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return null;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("password", password);
		return loginMapper.getUserByNameAndPwd(map);
	}

}
