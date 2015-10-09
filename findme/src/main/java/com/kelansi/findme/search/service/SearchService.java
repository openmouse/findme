package com.kelansi.findme.search.service;

import java.util.List;

import com.kelansi.findme.domain.RoomDetailInfo;

public interface SearchService {

	/**
	 * 根据关键词返回样板信息
	 * 
	 * @param keywords
	 * @return
	 */
	List<RoomDetailInfo> serachRoomInfosByKeywords(List<String> keywords);
	
}
