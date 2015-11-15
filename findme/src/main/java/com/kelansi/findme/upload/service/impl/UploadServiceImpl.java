package com.kelansi.findme.upload.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.common.Setting;
import com.kelansi.findme.db.process.MyBatisMapperProcessor;
import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.domain.RoomDetailInfo;
import com.kelansi.findme.domain.RoomDetailInfoShow;
import com.kelansi.findme.excel.ExcelReader;
import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.upload.dao.UploadMapper;
import com.kelansi.findme.upload.service.UploadService;

@Service("uploadServiceImpl")
public class UploadServiceImpl implements UploadService {

	@Resource(name = "myBatisMapperProcessor")
	private MyBatisMapperProcessor myBatisMapperProcessor;
	
	@Autowired
	private UploadMapper uploadMapper;
	
	@Autowired
	private Setting setting;
	
	@Override
	@Transactional
	public Message importByExcel(ExcelReader excel) throws IllegalArgumentException, IllegalAccessException {
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
        List<RoomDetailInfo> info1 = new ArrayList<RoomDetailInfo>();
        List<RoomDetailInfoShow> info2 = new ArrayList<RoomDetailInfoShow>();
        //未导入条数
        int failureCount = 0;
        for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
        	//room_detail
            RoomDetailInfo roomInfo = new RoomDetailInfo();
            //room_detail_show
            RoomDetailInfoShow roomInfoShow = new RoomDetailInfoShow();
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
                			 roomInfoShow.setDealer(enumValue.getEnumKey());
                		 }else{
                			 Integer newEnumValue = uploadMapper.getMaxEnumValueByEnumNum(1) + 1;
                			 //1为经销商对应enum_num
                			 EnumEntryBean enumEntry = new EnumEntryBean(cellValue,newEnumValue,1, 0,null,null);
                			 Map<String, Object> map = myBatisMapperProcessor.processInsertMapper(enumEntry, EnumEntryBean.class);
                			 if(!map.isEmpty()){
                				 uploadMapper.insertEnumEntry(map);
                			 }
                			 roomInfo.setDealer(newEnumValue);
                			 roomInfoShow.setDealer(cellValue);
                		 }
                 	}else{
                 		throw buildException(rowIndex, cellIndex, "经销商不能为空");
                 	}
                 }else if(cellIndex == 1){//房型
                	 if(StringUtils.isNotBlank(cellValue)){
                		 EnumEntryBean bean = setting.getStringEnumEntries().get(cellValue);
                		 if(bean == null){
                			 throw buildException(rowIndex, cellIndex, "房型参数未在规定参数中");
                		 }else{
                			 roomInfo.setRoomModel(bean.getEnumValue());
                			 roomInfoShow.setRoomModel(cellValue);
                		 }
                	 }
                 }else if(cellIndex == 2){//层高
                	 if(StringUtils.isNotBlank(cellValue)){
                		 Integer floorHeight = Integer.parseInt(cellValue);
                		 EnumEntryBean bean = this.selectEnumEntryBeanFromList(new BigDecimal(floorHeight), 3);
                		 if(bean == null){
                			 throw buildException(rowIndex, cellIndex, "层高范围未在规定参数中");
                		 }else{
                			 roomInfo.setFloorHeight(bean.getEnumValue());
                			 roomInfoShow.setFloorHeight(floorHeight);
                		 }
                	 }
                 }else if(cellIndex == 3){//功能空间
                	 if(StringUtils.isNotBlank(cellValue)){
                		 EnumEntryBean bean = setting.getStringEnumEntries().get(cellValue);
                		 if(bean == null){
                			 throw buildException(rowIndex, cellIndex, "功能空间参数未在规定参数中");
                		 }else{
                			 roomInfo.setFunctionSpace(bean.getEnumValue());
                			 roomInfoShow.setFunctionSpace(cellValue);
                		 }
                	 }
                 }else if(cellIndex == 4){//面积
                	 if(StringUtils.isNotBlank(cellValue)){
                		 BigDecimal size = new BigDecimal(cellValue);
                		 EnumEntryBean bean = this.selectEnumEntryBeanFromList(size, 5);
                		 if(bean == null){
                			 throw buildException(rowIndex, cellIndex, "层高范围未在规定参数中");
                		 }else{
                			 roomInfo.setSize(bean.getEnumValue());
                			 roomInfoShow.setSize(size);
                		 }
                	 }
                 }else if(cellIndex == 5){//风格
                	 if(StringUtils.isNotBlank(cellValue)){
                		 EnumEntryBean bean = setting.getStringEnumEntries().get(cellValue);
                		 if(bean == null){
                			 throw buildException(rowIndex, cellIndex, "风格参数未在规定参数中");
                		 }else{
                			 roomInfo.setStyle(bean.getEnumValue());
                			 roomInfoShow.setStyle(cellValue);
                		 }
                	 }
                 }else if(cellIndex == 6){//全景图
                	 if(StringUtils.isNotBlank(cellValue)){
                		 List<String> paths = new ArrayList<String>();
                		 String srcPath = Setting.imageUploadPath;
                		 String path = srcPath.substring(1, srcPath.length());
                		 for(String filePath : cellValue.split(",")){
                			 paths.add(path + filePath);
                		 }
            			 roomInfoShow.setPicturePaths(StringUtils.join(paths, ","));
            			 roomInfo.setPicturePaths(StringUtils.join(paths, ","));
                	 }
                 }
             }
             //当前库中不存在时才添加
             if(!uploadMapper.findSameRoomDetail(myBatisMapperProcessor.processInsertMapper(roomInfo, RoomDetailInfo.class))){
            	 info1.add(roomInfo);
                 info2.add(roomInfoShow);
             }else{
            	 failureCount ++;
             }
        }
        if(info1.size() == 0 || info2.size() == 0){
        	return Message.warn("导入成功 0 条 ,重复记录 " + failureCount + " 条");
        }
        uploadMapper.insertRoomDetail(info1);
        uploadMapper.insertRoomDetailShow(info2);
        return Message.success("message.upload.success", info1.size(), failureCount);
	}
	
	private EnumEntryBean containDealer(String dealer, List<EnumEntryBean> values){
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
	
	private EnumEntryBean selectEnumEntryBeanFromList(
			final BigDecimal delValue, final Integer enumNum) {
		Assert.notNull(delValue);
		List<EnumEntryBean> list = setting.getIntEnumEntries();
		@SuppressWarnings("unchecked")
		List<EnumEntryBean> target = (List<EnumEntryBean>) CollectionUtils
				.select(list, new Predicate() {

					@Override
					public boolean evaluate(Object object) {
						EnumEntryBean enumObj = (EnumEntryBean) object;
						return enumNum.equals(enumObj.getEnumNum())
								&& delValue.compareTo(enumObj.getNumBegin()) >= 0
								&& delValue.compareTo(enumObj.getNumEnd()) <= 0;
					}
				});
		return target.size() == 0 ? null : target.get(0);
	}

}
