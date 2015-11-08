package com.kelansi.findme.list.dao;

import java.util.List;
import java.util.Map;

import com.kelansi.findme.domain.RoomDetailInfoShow;

public interface ListMapper {

	List<RoomDetailInfoShow> getRoomDetailInfos(Map<String, Integer> map);

	int count();
}
