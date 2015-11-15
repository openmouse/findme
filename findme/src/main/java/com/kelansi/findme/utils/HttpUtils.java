/**
 * Project Name: xinyunlian-service
 * File Name: HttpUtils.java
 * Class Name: HttpUtils
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft - http://www.hengtiansoft.com
 *
 */
package com.kelansi.findme.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Roy
 * 
 */
public class HttpUtils {
	private static final Logger		logger					= LoggerFactory.getLogger(HttpUtils.class);
	private ThreadLocal<HttpClient>	httpClientThreadLocal	= new ThreadLocal<HttpClient>();

	private String hostURL;

	private HttpClient getHttpClient(){
		if (httpClientThreadLocal.get() != null){
			return httpClientThreadLocal.get();
		}else {
			return new DefaultHttpClient();
		}
	}
	
	public void upload(String filePath, File file) throws IOException {
		HttpPost httpPost = new HttpPost(hostURL);
		httpPost.addHeader("filePath", filePath);
		MultipartEntity entity = new MultipartEntity();
		FileInputStream fileInputStream = new FileInputStream(file);
		ByteArrayBody fileBody = new ByteArrayBody(
				IOUtils.toByteArray(fileInputStream), file.getName());
		entity.addPart("file", fileBody);
		httpPost.setEntity(entity);
		HttpResponse response = getHttpClient().execute(httpPost);
		if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
			logger.error("File upload failed caused by internal server error.");
			throw new IOException(
					"File upload failed caused by internal server error.");
		}
		HttpEntity entityEntity = response.getEntity();
		EntityUtils.consume(entityEntity);
	}

	public void download(String localPath, String remoteFilepath) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = hostURL + remoteFilepath;
		HttpGet httpGet = new HttpGet(url);
		logger.info(url);
		HttpResponse response = httpClient.execute(httpGet);
		if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
			logger.error("File upload failed caused by internal server error.");
			throw new IOException(
					"File upload failed caused by internal server error.");
		}
		HttpEntity entity = response.getEntity();
		long length = entity.getContentLength();
		if (length <= 0) {
			logger.error("The file is not in remote server.");
			return;
		}
		try (OutputStream out = new FileOutputStream(localPath + remoteFilepath); InputStream in = entity.getContent()) {
			byte[] data = new byte[1024 * 1024];
			int index = 0;
			while ((index = in.read(data)) != -1) {
				out.write(data, 0, index);
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, String> postMultipart(String path, Map<String, String> params){
		Map<String, String> map = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost("http://file.api.weixin.qq.com/cgi-bin/media/upload");
			File file = new File(path);

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg");
			mpEntity.addPart("file", cbFile);
			for (Entry<String, String> entry : params.entrySet()) {

				mpEntity.addPart(entry.getKey(),
						new StringBody(entry.getValue()));

			}

			httppost.setEntity(mpEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			String jsonStr = EntityUtils.toString(resEntity);
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(jsonStr, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map == null ? Collections.EMPTY_MAP : map;
	}

	public String getHostURL() {
		return hostURL;
	}

	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}
}
