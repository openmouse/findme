package com.kelansi.findme.search.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.search.service.SearchService;
import com.kelansi.findme.utils.FindmeStringUtils;
import com.kelansi.findme.utils.WxMsgConverter;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	/**
	 * 
	 * @param keywords 关键词
	 * @param toUserName 接收方帐号（收到的OpenID）
	 * @param fromUserName 开发者微信号
	 */
	@RequestMapping
	public void doSearch(String keywords, String toUserName, String fromUserName, HttpServletRequest request, HttpServletResponse respnose){
		Writer writer = null;
		try {
			writer = respnose.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//存在任何参数为空则返回空
		if(StringUtils.isBlank(keywords) || StringUtils.isBlank(toUserName) || StringUtils.isBlank(fromUserName)){
			try {
				writer.write("");
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		String filterdWords = FindmeStringUtils.StringFilter(keywords);
		List<RoomDetailInfo> infos = searchService.searchRoomInfos(filterdWords);
		try {
			writer.write(WxMsgConverter.convertToXmlStr(infos, toUserName, fromUserName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
