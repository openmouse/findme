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
	
	/**
	 * 测试word和enum都存在对应
	 */
//	@Test
//	public void test1(){
//		searchService.searchRoomInfos("风格温馨,样子温暖");
//	}
	
	/**
	 * 测试只有enum都存在
	 */
	@Test
	public void test2(){
		searchService.searchRoomInfos("房子要温馨,温暖");
	}
}
