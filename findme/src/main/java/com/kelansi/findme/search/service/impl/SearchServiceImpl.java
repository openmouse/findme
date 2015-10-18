package com.kelansi.findme.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.common.Setting;
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
	private Setting setting;
	
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
			return serachRoomInfosByKeywords(parsedWords);
		}else{
			return Collections.emptyList();
		}
	}
	
	public List<RoomDetailInfo> serachRoomInfosByKeywords(List<String> keywords){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from findme_room_detail where 1=1");
		Iterator<String> iterator =  keywords.iterator();
		List<WordMappingBean> fields = new ArrayList<WordMappingBean>();
		Map<Integer, EnumEntryBean> enumValues = new HashMap<Integer, EnumEntryBean>();
		while(iterator.hasNext()){
			String keyword = iterator.next();
			fields.addAll(this.getFieldsByKeyword(keyword, iterator));
			enumValues.putAll(this.getEnumValuesByKeyword(keyword, iterator));
		}
		String finalSql = this.buildSql(fields, enumValues, sql);
		return searchMapper.searchWithSql(finalSql);
	}
	
	private String buildSql(List<WordMappingBean> fields, Map<Integer, EnumEntryBean> enumValues, StringBuilder sbd){
		List<String> mappingEnums = new ArrayList<String>();
		Integer size = fields.size();
		for(int i = 0 ; i < size ; i ++){
			WordMappingBean field = fields.get(i);
			Integer enumKey = field.getEnumNum();
			EnumEntryBean enumValue = enumValues.get(enumKey);
			//若在关键句分词中未发现当前字段的形容词则直接continue
			if(enumValue == null){
				continue;
			}
			String conditionSql = this.buildConditionSql(field, enumValue);
			if(!mappingEnums.contains(conditionSql)){
				mappingEnums.add(conditionSql);
			}
		}
		for(Entry<Integer, EnumEntryBean> entry : enumValues.entrySet()){
			String conditionSql = this.buildConditionSql(setting.getWordMappings().get(entry.getKey()), entry.getValue());
			if(!mappingEnums.contains(conditionSql)){
				mappingEnums.add(conditionSql);
			}
		}
		for(String mmappingEnum : mappingEnums){
			sbd.append(" and ");
			sbd.append(mmappingEnum);
		}
		return sbd.toString();
	}
	
	private String buildConditionSql(WordMappingBean field,EnumEntryBean enumValue){
		StringBuilder newStr = new StringBuilder();
		newStr.append(field.getMappingStr());
		newStr.append(" = ");
		newStr.append(enumValue.getEnumValue());
		return newStr.toString();
	}
	
	private List<WordMappingBean> getFieldsByKeyword(String keyword, Iterator<String> iterator){
		List<WordMappingBean> fields = new ArrayList<WordMappingBean>();
		if(StringUtils.isNotBlank(keyword)){
			WordMappingBean fieldName = searchMapper.getStrByMappingField(keyword);
			if(fieldName != null){
				fields.add(fieldName);
				iterator.remove();
			}
		}
		return fields;
	}
	
	private Map<Integer, EnumEntryBean> getEnumValuesByKeyword(String keyword, Iterator<String> iterator){
		Map<Integer, EnumEntryBean> enumValues  = new HashMap<Integer, EnumEntryBean>();
		if(StringUtils.isNotBlank(keyword)){
			EnumEntryBean enumValue = searchMapper.getEnumValueByWords(keyword);
			if(enumValue != null ){
				iterator.remove();
				enumValues.put(enumValue.getEnumNum(), enumValue);
			}
		}
		return enumValues;
	}
}
