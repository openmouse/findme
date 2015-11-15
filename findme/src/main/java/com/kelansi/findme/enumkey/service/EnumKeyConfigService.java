package com.kelansi.findme.enumkey.service;

import java.util.List;

import com.kelansi.findme.domain.EnumEntryBean;

public interface EnumKeyConfigService {

	List<EnumEntryBean> getEnumEntryBeanByNum(Integer enumNum);
	
}
