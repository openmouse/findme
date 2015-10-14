package com.findme.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kelansi.findme.search.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test.xml")
public class SearchServiceTest {

	@Autowired
	private SearchService searchService;
	
	@Test
	public void test1(){
		searchService.searchRoomInfos("温馨舒服");
	}
	
}
