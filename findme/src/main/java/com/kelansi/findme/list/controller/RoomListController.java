package com.kelansi.findme.list.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kelansi.findme.common.Page;
import com.kelansi.findme.common.Pageable;
import com.kelansi.findme.domain.RoomDetailInfoShow;
import com.kelansi.findme.list.service.RoomListService;

@Controller
@RequestMapping(value = "/roomList")
public class RoomListController {

	@Resource(name = "roomListServiceImpl")
	private RoomListService roomListService;
	
	/** 默认每页记录数 */
	private static final long DEFAULT_PAGE_SIZE = 20;
	
	@RequestMapping(method = RequestMethod.GET)
	public String roomList(ModelMap map, Pageable pageable){
		Page<RoomDetailInfoShow> page = new Page<>(roomListService.getRoomDetailInfos(pageable), DEFAULT_PAGE_SIZE, pageable);
		map.addAttribute("page", page);
		return "rooms/list";
	}
	
}
