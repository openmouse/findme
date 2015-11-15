package com.findme.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kelansi.findme.wx.api.WxRequestApiBean;
import com.kelansi.findme.wx.api.token.TokenAccessApiProcessor;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test.xml")
public class ApiTest {

	@Autowired
	private TokenAccessApiProcessor processor;
	
	@Value("${wx.appid}")
	private String appid;
	
	@Value("${wx.appsecret}")
	private String appSecret;
	
	@Test
	public void test1(){
		WxRequestApiBean bean = new WxRequestApiBean();
		bean.setApiUrl("https://api.weixin.qq.com/cgi-bin/token");
		bean.getParamMap().put("grant_type", "client_credential");
		bean.getParamMap().put("appid", "wxf901cfdd2b867dc7");
		bean.getParamMap().put("secret", "5a2b6e63fac90173be04755654138da6");
		String token = processor.getAccessToken(bean);
		System.out.println(token);
	}
}
