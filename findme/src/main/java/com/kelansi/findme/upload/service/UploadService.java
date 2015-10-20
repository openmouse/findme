package com.kelansi.findme.upload.service;

import com.kelansi.findme.excel.ExcelReader;


public interface UploadService {

	void importByExcel(ExcelReader excel) throws IllegalArgumentException, IllegalAccessException;
}
