package com.kelansi.findme.search.dao;

public interface SearchMapper {

	String getStrByMappingField(String keyword);
	
	Integer getEnumValueByWords(String keyword);
}
