package com.kelansi.findme.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.WordMappingBean;
import com.kelansi.findme.search.dao.SearchMapper;

@Component
@Lazy(false)
public class Setting implements InitializingBean{

	public static final String cookiePath="/";  
	public static final String cookieDomain="";
	
	private Map<Integer, WordMappingBean> wordMappings = new HashMap<Integer, WordMappingBean>();
	private Map<String, EnumEntryBean> stringEnumEntries = new HashMap<String, EnumEntryBean>();
	private List<EnumEntryBean> intEnumEntries = new ArrayList<EnumEntryBean>();
	
	@Autowired
	private SearchMapper searchMapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//构建KEY WORD
		List<WordMappingBean> wordMappings = searchMapper.findAllWordMappings();
		Map<Integer, WordMappingBean> map1 = new HashMap<Integer, WordMappingBean>();
		for(WordMappingBean wm : wordMappings){
			map1.put(wm.getEnumNum(), wm);
		}
		this.wordMappings = map1;
		// 构建string型枚举类
		List<EnumEntryBean> enumEntries = searchMapper.findAllEnumEntries();
		Map<String, EnumEntryBean> map2 = new HashMap<String, EnumEntryBean>();
		for(EnumEntryBean enumEntry : enumEntries){
			if(enumEntry.getType() == 0){
				map2.put(enumEntry.getEnumKey(), enumEntry);
			}
		}
		this.stringEnumEntries = map2;
		//构建数字型枚举类
		List<EnumEntryBean> list3 = new ArrayList<EnumEntryBean>();
		for(EnumEntryBean enumEntry : enumEntries){
			if(enumEntry.getType() == 1){
				list3.add(enumEntry);
			}
		}
		this.intEnumEntries = list3;
	}

	public Map<Integer, WordMappingBean> getWordMappings() {
		return wordMappings;
	}

	public void setWordMappings(Map<Integer, WordMappingBean> wordMappings) {
		this.wordMappings = wordMappings;
	}

	public Map<String, EnumEntryBean> getStringEnumEntries() {
		return stringEnumEntries;
	}

	public void setStringEnumEntries(Map<String, EnumEntryBean> stringEnumEntries) {
		this.stringEnumEntries = stringEnumEntries;
	}

	public List<EnumEntryBean> getIntEnumEntries() {
		return intEnumEntries;
	}

	public void setIntEnumEntries(List<EnumEntryBean> intEnumEntries) {
		this.intEnumEntries = intEnumEntries;
	}
}
