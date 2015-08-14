package com.kelansi.findme.upload.service;

import com.kelansi.findme.utils.excel.ExcelReader;

public interface UploadService {

	void importByExcel(ExcelReader excel);
}
