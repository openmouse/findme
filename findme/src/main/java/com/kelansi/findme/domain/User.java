package com.kelansi.findme.domain;

public class User extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5920244069943427903L;
	
	public static final String USER_SESSION_ATTR = "USER.PRINCIPAL";
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
