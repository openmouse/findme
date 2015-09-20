package com.kelansi.findme.search.dao;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.WordMappingBean;


public interface SearchMapper {

	WordMappingBean getStrByMappingField(String keyword);
	
	EnumEntryBean getEnumValueByWords(String keyword);
}
