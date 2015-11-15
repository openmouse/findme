/**
 * Project Name: xinyunlian-admin
 * File Name: HttpPlugin.java
 * Class Name: HttpPlugin
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft - http://www.hengtiansoft.com
 *
 */
package com.kelansi.findme.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kelansi.findme.utils.HttpUtils;

/**
 * @author qwei
 * 
 */
@Component("httpPlugin")
public class HttpPlugin{

	private static final Logger	LOGGER	= LoggerFactory.getLogger(HttpPlugin.class);
	
	@Value("${http.host}")
	private String host;

	public void upload(String path, File file, String contentType) {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.setHostURL(host);
		try {
			httpUtils.upload(path, file);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public void download(String localPath, String remoteFilePath){
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.setHostURL(host);
		try {
			httpUtils.download(localPath, remoteFilePath);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}		
	}	
	
}
