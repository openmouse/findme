package com.kelansi.findme.wx.api;

import java.util.Map;

/**
 * 微信接口请求
 * 
 * @author Roy
 *
 */
public class WxRequestApiBean {

	private String apiUrl;
	
	private Map<String, String> paramMap;

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}
