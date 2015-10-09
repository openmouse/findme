package com.kelansi.findme.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.domain.WordMappingBean;
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
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate template;
	
	public List<RoomDetailInfo> serachRoomInfosByKeywords(List<String> keywords){
		StringBuilder sql = new StringBuilder();
		Iterator<String> iterator =  keywords.iterator();
		sql.append("select * from findme_room_info where 1=1");
		List<WordMappingBean> fields = this.getFieldsByKeyword(keywords, iterator);
		Map<Integer, EnumEntryBean> enumValues = this.getEnumValuesByKeyword(keywords, iterator);
		
		String finalSql = this.buildSql(fields, enumValues, sql);
		
		return template.<RoomDetailInfo>selectList(finalSql);
	}
	
	private String buildSql(List<WordMappingBean> fields, Map<Integer, EnumEntryBean> enumValues, StringBuilder sbd){
		Integer size = fields.size();
		for(int i = 0 ; i < size ; i ++){
			WordMappingBean field = fields.get(i);
			Integer enumKey = field.getEnumNum();
			EnumEntryBean enumValue = enumValues.get(enumKey);
			sbd.append(" and ");
			sbd.append(field.getMappingStr());
			sbd.append(" = ");
			sbd.append(enumValue.getEnumValue());
		}
		return sbd.toString();
	}
	
	private List<WordMappingBean> getFieldsByKeyword(List<String> keywords, Iterator<String> iterator){
		List<WordMappingBean> fields = new ArrayList<WordMappingBean>();
		for(String keyword : keywords){
			if(StringUtils.isNotBlank(keyword)){
				WordMappingBean fieldName = searchMapper.getStrByMappingField(keyword);
				if(fieldName != null){
					fields.add(fieldName);
					iterator.remove();
				}
			}
		}
		return fields;
	}
	
	private Map<Integer, EnumEntryBean> getEnumValuesByKeyword(List<String> keywords, Iterator<String> iterator){
		Map<Integer, EnumEntryBean> enumValues  = new HashMap<Integer, EnumEntryBean>();
		for(String keyword : keywords){
			if(StringUtils.isNotBlank(keyword)){
				EnumEntryBean enumValue = searchMapper.getEnumValueByWords(keyword);
				if(enumValue != null ){
					iterator.remove();
					enumValues.put(enumValue.getEnumNum(), enumValue);
				}
			}
		}
		return enumValues;
	}
}
