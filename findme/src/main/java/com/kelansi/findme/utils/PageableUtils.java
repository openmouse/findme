package com.kelansi.findme.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.kelansi.findme.common.Pageable;

public class PageableUtils {

	private static final String LIMIT_START_KEY = "start";
	private static final String LIMIT_KEY = "inval";
	
	public static Map<String, Integer> buildMapFromPageable(Pageable pageable, int count){
		if(pageable == null){
			return Collections.emptyMap();
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		map.put(LIMIT_START_KEY, start);
		map.put(LIMIT_KEY, pageable.getPageSize());
		int totalPages = (int) Math.ceil((double) count / (double) pageable.getPageSize());
		if(pageable.getPageNumber() <= 1){
			pageable.setPageNumber(1);
		}else if(pageable.getPageNumber()  >= totalPages){
			pageable.setPageNumber(totalPages);
		}else{
			pageable.setPageNumber(pageable.getPageNumber() + 1);
		}
		return map;
	}
	
}
