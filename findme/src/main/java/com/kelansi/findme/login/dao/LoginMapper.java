package com.kelansi.findme.login.dao;

import java.util.Map;

import com.kelansi.findme.domain.User;

public interface LoginMapper {

	User getUserByNameAndPwd(Map<String, Object> map);

}
