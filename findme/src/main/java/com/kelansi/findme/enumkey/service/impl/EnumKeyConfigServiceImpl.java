package com.kelansi.findme.enumkey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.enumkey.dao.EnumKeyConfigMapper;
import com.kelansi.findme.enumkey.service.EnumKeyConfigService;


@Service("enumKeyConfigServiceImpl")
public class EnumKeyConfigServiceImpl implements EnumKeyConfigService {

	@Autowired
	private EnumKeyConfigMapper enumKeyConfigMapper;
	
	@Override
	public List<EnumEntryBean> getEnumEntryBeanByNum(Integer enumNum) {
		return enumKeyConfigMapper.getEnumEntryBeanByNum(enumNum);
	}

	
	
}
