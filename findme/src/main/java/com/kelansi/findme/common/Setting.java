package com.kelansi.findme.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kelansi.findme.domain.WordMappingBean;
import com.kelansi.findme.search.dao.SearchMapper;

@Component
@Lazy(false)
public class Setting implements InitializingBean{

	public static final String cookiePath="/";  
	public static final String cookieDomain="";
	
	private Map<Integer, WordMappingBean> wordMappings = new HashMap<Integer, WordMappingBean>();
	
	@Autowired
	private SearchMapper searchMapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<WordMappingBean> wordMappings = searchMapper.findAllWordMappings();
		Map<Integer, WordMappingBean> map = new HashMap<Integer, WordMappingBean>();
		for(WordMappingBean wm : wordMappings){
			map.put(wm.getEnumNum(), wm);
		}
		this.wordMappings = map;
	}

	public Map<Integer, WordMappingBean> getWordMappings() {
		return wordMappings;
	}

	public void setWordMappings(Map<Integer, WordMappingBean> wordMappings) {
		this.wordMappings = wordMappings;
	}
}
