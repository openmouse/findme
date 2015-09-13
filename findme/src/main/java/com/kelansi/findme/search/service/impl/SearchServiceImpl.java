package com.kelansi.findme.search.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.search.service.SearchService;
import com.kelansi.findme.word.mapping.WXWordsProcessor;

public class SearchServiceImpl implements SearchService{
	
	@Resource(name = "wxWordsProcessor")
	private WXWordsProcessor processor;

	public List<RoomDetailInfo> searchRoomInfos(String words){
		List<String> parsedWords = null;
		try {
			parsedWords = processor.parse(words);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(CollectionUtils.isNotEmpty(parsedWords)){
			
			return null;
		}else{
			return Collections.emptyList();
		}
	}
	
}
