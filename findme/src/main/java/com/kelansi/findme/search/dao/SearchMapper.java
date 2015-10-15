package com.kelansi.findme.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.domain.WordMappingBean;


public interface SearchMapper {

	WordMappingBean getStrByMappingField(String keyword);
	
	EnumEntryBean getEnumValueByWords(String keyword);

	List<RoomDetailInfo> searchWithSql(@Param("sql")String sql);
}
