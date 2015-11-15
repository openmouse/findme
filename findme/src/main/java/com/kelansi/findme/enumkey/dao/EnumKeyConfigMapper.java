package com.kelansi.findme.enumkey.dao;

import java.util.List;

import com.kelansi.findme.domain.EnumEntryBean;

public interface EnumKeyConfigMapper {

	List<EnumEntryBean> getEnumEntryBeanByNum(Integer enumNum);

	void save(EnumEntryBean enumEntry);

	Integer getMaxEnumValueByNum(int dealer);
	
	Integer getEnumNumById(Long id);

	void delete(Long id);
}
