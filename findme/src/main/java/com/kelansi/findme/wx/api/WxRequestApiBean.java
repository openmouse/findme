package com.kelansi.findme.wx.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 微信接口请求
 * 
 * @author Roy
 *
 */
public class WxRequestApiBean {

	private String apiUrl;
	
	private Map<String, String> paramMap= new HashMap<String, String>();

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
	
	public String getMethodGetUrl(){
		StringBuilder sbd = null;
		if(apiUrl != null){
			sbd = new StringBuilder(apiUrl);
			List<String> list = new ArrayList<String>();
			for(Entry<String,String> entry : paramMap.entrySet()){
				list.add(entry.getKey() + "=" + entry.getValue());
			}
			sbd.append("?").append(StringUtils.join(list, "&"));
		}
		return sbd == null ? null : sbd.toString();
	}
}
