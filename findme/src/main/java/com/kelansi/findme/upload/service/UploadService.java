package com.kelansi.findme.upload.service;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.excel.ExcelReader;


public interface UploadService {

	Message importByExcel(ExcelReader excel) throws IllegalArgumentException, IllegalAccessException;
}
