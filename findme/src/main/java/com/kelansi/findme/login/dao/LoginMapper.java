package com.kelansi.findme.login.dao;

import com.kelansi.findme.domain.User;

public interface LoginMapper {

	User getUserByNameAndPwd(String username, String password);

}
