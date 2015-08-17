package com.kelansi.findme.login.service;

import com.kelansi.findme.domain.User;

public interface LoginService {
	
	User auth(String username, String password);

}
