package com.kelansi.findme.wx.api.token;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelansi.findme.wx.api.WxRequestApiBean;

/**
 * 获取access token
 * 
 * @author Roy
 *
 */
@Component
public class TokenAccessApiProcessor {

	@Value("${wx.appid}")
	private String appId;
	
	@Value("${wx.appsecret}")
	private String appSecret;
	
	private static final String ACCESS_TOKEN = "access_token";
	
	public String getAccessToken(WxRequestApiBean bean){
		bean.setApiUrl("https://api.weixin.qq.com/cgi-bin/token");
		bean.getParamMap().put("grant_type", "client_credential");
		bean.getParamMap().put("appid", appId);
		bean.getParamMap().put("secret", appSecret);
		String requestUrl = bean.getMethodGetUrl();
		if(StringUtils.isBlank(requestUrl)){
			return null;
		}
		RestTemplate template = new RestTemplate();
		String accessToken = template.getForObject(requestUrl, String.class, bean.getParamMap());
		ObjectMapper mapper = new ObjectMapper();
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> map = mapper.readValue(accessToken, Map.class);
			return map.get(ACCESS_TOKEN);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
