package com.kelansi.findme.list.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelansi.findme.common.Pageable;
import com.kelansi.findme.domain.RoomDetailInfoShow;
import com.kelansi.findme.list.dao.ListMapper;
import com.kelansi.findme.list.service.RoomListService;
import com.kelansi.findme.utils.PageableUtils;

@Service("roomListServiceImpl")
public class RoomListServiceImpl implements RoomListService {

	@Autowired
	private ListMapper listMapper;
	
	@Override
	public List<RoomDetailInfoShow> getRoomDetailInfos(Pageable pageable) {
		int count = listMapper.count();
		if(count == 0){
			return Collections.emptyList();
		}
		return listMapper.getRoomDetailInfos(PageableUtils.buildMapFromPageable(pageable, count));
	}
	
}
