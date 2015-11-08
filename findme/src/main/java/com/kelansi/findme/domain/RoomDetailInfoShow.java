package com.kelansi.findme.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class RoomDetailInfoShow extends BaseDomain{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4771543137467544736L;

	private String dealer;
	
	/** 枚举形式 房型*/
	private String roomModel;
	
	/** 枚举形式 面积 */
	private BigDecimal size;
	/** 枚举范围 层高 */
	private Integer floorHeight;
	
	/** 功能空间 */
	private String functionSpace;
	
	/** 枚举形式 风格 */
	private String style;
	
	/** 图片地址 */
	private String picturePaths;

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getRoomModel() {
		return roomModel;
	}

	public void setRoomModel(String roomModel) {
		this.roomModel = roomModel;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public Integer getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(Integer floorHeight) {
		this.floorHeight = floorHeight;
	}

	public String getFunctionSpace() {
		return functionSpace;
	}

	public void setFunctionSpace(String functionSpace) {
		this.functionSpace = functionSpace;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPicturePaths() {
		return picturePaths;
	}

	public void setPicturePaths(String picturePaths) {
		this.picturePaths = picturePaths;
	}
	
	public List<String> getPictPathList(){
		if(StringUtils.isNotBlank(picturePaths)){
			return Arrays.asList(picturePaths.split(","));
		}else{
			return null;
		}
	}
}
