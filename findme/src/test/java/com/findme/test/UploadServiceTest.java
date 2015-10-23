package com.findme.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kelansi.findme.excel.ExcelReader;
import com.kelansi.findme.excel.ExcelReaderFactory;
import com.kelansi.findme.upload.service.UploadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:test.xml")
public class UploadServiceTest {

	@Resource(name = "uploadServiceImpl")
	private UploadService uploadService;
	
	@Test
	public void test1() throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException{
		ExcelReader excel = ExcelReaderFactory.getExcelReader("/Users/Roy/Documents/roomDetail导入模本.xlsx",new FileInputStream(new File("/Users/Roy/Documents/roomDetail导入模本.xlsx")));
		uploadService.importByExcel(excel);
	}
}
