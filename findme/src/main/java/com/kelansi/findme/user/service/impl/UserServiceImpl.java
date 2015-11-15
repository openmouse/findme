package com.kelansi.findme.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kelansi.findme.user.dao.UserMapper;
import com.kelansi.findme.user.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public void changePwd(Long id, String newPwd){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("password", newPwd);
		userMapper.changePwd(params);
	}
}
