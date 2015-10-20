package com.kelansi.findme.upload.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kelansi.findme.db.process.MyBatisMapperProcessor;
import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.domain.RoomDetailInfoShow;
import com.kelansi.findme.excel.ExcelReader;
import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.upload.dao.UploadMapper;
import com.kelansi.findme.upload.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Resource(name = "myBatisMapperProcessor")
	private MyBatisMapperProcessor myBatisMapperProcessor;
	
	@Autowired
	private UploadMapper uploadMapper;
	
	@Override
	public void importByExcel(ExcelReader excel) throws IllegalArgumentException, IllegalAccessException {
		Sheet sheet = excel.getSheet(0);
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            throw new CommonException("请至少导入一行数据！");
        }
        Row row = null;
        DecimalFormat df = new DecimalFormat("0.############");
        short cellCount = 0;
        Row firstRow = sheet.getRow(0);
        if(firstRow != null){
        	cellCount = firstRow.getLastCellNum();
        }
        //经销商、房型、层高、功能空间、面积、风格、全景图
        if (cellCount != 7) {
        	throw new CommonException("EXCEL导入列数不正确,请确认模板及每行数据后重新导入！");
        }
       
        //room_detail
        Map<String, Object> param1 = new HashMap<String, Object>();
        RoomDetailInfo roomInfo = new RoomDetailInfo();
        //room_detail_show
        Map<String, Object> param2 = new HashMap<String, Object>();
        RoomDetailInfoShow roomInfoShows = new RoomDetailInfoShow();
        for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
        	 row = sheet.getRow(rowIndex);
             if (row == null || excel.isEmptyRowValues(row)) {
                 continue;
             }
             for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
            	 Cell cell=row.getCell(cellIndex);
                 String cellValue = null;
                 try {
                     if (null == row.getCell(cellIndex)) {
                         cellValue = "";
                     } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                         double numValue = cell.getNumericCellValue();
                         if (NumberUtils.isDigits(String.valueOf(numValue))) {
                             throw new CommonException("Is digit");
                         } else {
                             cellValue = df.format(cell.getNumericCellValue());
                         }
                     } else {
                         cellValue = cell.getStringCellValue().trim();
                     }
                 } catch (Exception e) {
                     throw new CommonException("文件内容出错: Row:" + (rowIndex + 1) + " 行 第:" + (cellIndex + 1) + "列.");
                 }
                 if (cellIndex == 0) { //经销商
                	 if(StringUtils.isNotBlank(cellValue)){
                		 List<EnumEntryBean> enumValues = uploadMapper.getEnumValueByField("经销商");
                		 EnumEntryBean enumValue = this.containDealer(cellValue, enumValues);
                		 if(enumValue != null){
                			 roomInfo.setDealer(enumValue.getEnumValue());
                			 roomInfoShows.setDealer(enumValue.getEnumKey());
                		 }else{
                			 Integer newEnumValue = uploadMapper.getMaxEnumValueByEnumNum(1) + 1;
                			 //1为经销商对应enum_num
                			 EnumEntryBean enumEntry = new EnumEntryBean(cellValue,newEnumValue,1, 0,null,null);
                			 uploadMapper.insertEnumEntry(myBatisMapperProcessor.processInsertMapper(enumEntry, EnumEntryBean.class));
                			 roomInfo.setDealer(newEnumValue);
                			 roomInfoShows.setDealer(cellValue);
                		 }
                 	}else{
                 		throw buildException(rowIndex, cellIndex, "经销商不能为空");
                 	}
                 }
             }
        }
	}
	
	private EnumEntryBean containDealer(String dealer, List<EnumEntryBean> values){
		Assert.notNull(dealer);
		for(EnumEntryBean value : values){
			if(dealer.equals(value.getEnumKey())){
				return value;
			}
		}
		return null;
	}
	
	/**
	 * @param rowIndex
	 * @param cellIndex
	 * @return
	 */
	private CommonException buildException(int rowIndex, int cellIndex, String msg) {
		return new CommonException("文件内容出错: 第:" + (rowIndex + 1) + " 行 第:" + (cellIndex + 1) + "列." + msg);
	}

}
