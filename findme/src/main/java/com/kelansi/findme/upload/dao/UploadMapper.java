package com.kelansi.findme.upload.dao;

import java.util.List;
import java.util.Map;

import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.domain.RoomDetailInfoShow;



public interface UploadMapper {

	/**
	 * 通过字段名称获得该字段下对应枚举中文参数
	 * 
	 * @param field
	 * @return
	 */
	List<EnumEntryBean> getEnumValueByField(String field);
	
	void insertEnumEntry(Map<String,Object> map);
	
	Integer getMaxEnumValueByEnumNum(Integer enumNum);
	
	void insertRoomDetail(List<RoomDetailInfo> list);
	
	void insertRoomDetailShow(List<RoomDetailInfoShow> list);
}
