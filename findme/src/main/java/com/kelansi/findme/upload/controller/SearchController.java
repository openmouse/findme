package com.kelansi.findme.upload.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.search.service.SearchService;
import com.kelansi.findme.word.mapping.WXWordsProcessor;

@Controller
@RequestMapping(value="/search")
public class SearchController {

	@Resource(name = "wxWordsProcessor")
	private WXWordsProcessor processor;
	
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;
	
	/**
	 * <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>
	 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[image]]></MsgType>
	 * <Image> <MediaId><![CDATA[media_id]]></MediaId> </Image> </xml>
	 * 
	 * @param keywords
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public String search(String keywords){
		if(StringUtils.isBlank(keywords)){
			return "";
		}
		List<String> mediaIds = new ArrayList<String>();
		try {
			List<String> words = processor.parse(keywords);
			List<RoomDetailInfo> infos = searchService.serachRoomInfosByKeywords(words);
			for(RoomDetailInfo info : infos){
				if(StringUtils.isNotBlank(info.getMediaId())){
					mediaIds.add(info.getMediaId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  StringUtils.join(mediaIds, ",");
	}
}
