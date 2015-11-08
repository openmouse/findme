package com.kelansi.findme.list.service;

import java.util.List;

import com.kelansi.findme.common.Pageable;
import com.kelansi.findme.domain.RoomDetailInfoShow;

public interface RoomListService {

	/**
	 * 
	 * 
	 * @return
	 */
	List<RoomDetailInfoShow> getRoomDetailInfos(Pageable pageable);
	
}
