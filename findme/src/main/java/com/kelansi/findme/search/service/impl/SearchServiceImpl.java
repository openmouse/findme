package com.kelansi.findme.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.search.dao.SearchMapper;
import com.kelansi.findme.search.service.SearchService;
import com.kelansi.findme.word.mapping.WXWordsProcessor;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService{
	
	@Resource(name = "wxWordsProcessor")
	private WXWordsProcessor processor;
	
	@Autowired
	private SearchMapper searchMapper;

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
	
	private List<RoomDetailInfo> serachRoomInfosByKeywords(List<String> keywords){
		StringBuilder sql = new StringBuilder();
		Iterator<String> iterator =  keywords.iterator();
		sql.append("select * from findme_room_info where 1=1");
		while(iterator.hasNext()){
			List<String> fields = this.getFieldsByKeyword(keywords, iterator);
			List<Integer> enumValues = this.getEnumValuesByKeyword(keywords, iterator);
		}
		return null;
	}
	
	private List<String> getFieldsByKeyword(List<String> keywords, Iterator<String> iterator){
		List<String> fields = new ArrayList<String>();
		for(String keyword : keywords){
			if(StringUtils.isNotBlank(keyword)){
				String fieldName = searchMapper.getStrByMappingField(keyword);
				if(StringUtils.isNotBlank(fieldName)){
					fields.add(fieldName);
					iterator.remove();
				}
			}
		}
		return fields;
	}
	
	private List<Integer> getEnumValuesByKeyword(List<String> keywords, Iterator<String> iterator){
		List<Integer> enumValues  = new ArrayList<Integer>();
		for(String keyword : keywords){
			if(StringUtils.isNotBlank(keyword)){
				Integer enumValue = searchMapper.getEnumValueByWords(keyword);
				if(enumValue != null ){
					iterator.remove();
					enumValues.add(enumValue);
				}
			}
		}
		return enumValues;
	}
}
