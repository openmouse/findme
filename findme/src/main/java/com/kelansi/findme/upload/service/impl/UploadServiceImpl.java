package com.kelansi.findme.upload.service.impl;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.upload.service.UploadService;
import com.kelansi.findme.utils.excel.ExcelReader;

@Service
public class UploadServiceImpl implements UploadService {

	@Override
	public void importByExcel(ExcelReader excel) {
		Sheet sheet = excel.getSheet(0);
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            throw new CommonException("请至少导入一行数据！");
        }
        Row row = null;
        DecimalFormat df = new DecimalFormat("0.###");
        
        short cellCount = 0;
        Row firstRow = sheet.getRow(0);
        if(firstRow != null){
        	cellCount = firstRow.getLastCellNum();
        }
        if (cellCount < 6) {
        	throw new CommonException("EXCEL导入列数不正确,请确认模板及每行数据后重新导入！");
        }
        
        for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
        	 row = sheet.getRow(rowIndex);
             if (row == null || excel.isEmptyRowValues(row)) {
                 continue;
             }
             for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
            	 
             }
        }
	}

}
