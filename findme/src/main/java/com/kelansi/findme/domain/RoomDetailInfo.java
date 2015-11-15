package com.kelansi.findme.domain;


public class RoomDetailInfo extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5608034604878640779L;
	
	public Integer dealer;
	
	/** 枚举形式 房型*/
	public Integer roomModel;
	
	/** 枚举形式 面积 */
	public Integer size;
	/** 枚举范围 层高 */
	public Integer floorHeight;
	
	/** 功能空间 */
	public Integer functionSpace;
	
	/** 枚举形式 风格 */
	public Integer style;
	
	/** 图片地址 */
	public String picturePaths;
	
	/** 微信服务器端保存的商品的上传ID */
	public String mediaIds;

	public Integer getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(Integer floorHeight) {
		this.floorHeight = floorHeight;
	}

	public Integer getRoomModel() {
		return roomModel;
	}

	public void setRoomModel(Integer roomModel) {
		this.roomModel = roomModel;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public String getPicturePaths() {
		return picturePaths;
	}

	public void setPicturePaths(String picturePaths) {
		this.picturePaths = picturePaths;
	}

	public String getMediaIds() {
		return mediaIds;
	}

	public void setMediaIds(String mediaIds) {
		this.mediaIds = mediaIds;
	}

	public Integer getFunctionSpace() {
		return functionSpace;
	}

	public void setFunctionSpace(Integer functionSpace) {
		this.functionSpace = functionSpace;
	}

	public Integer getDealer() {
		return dealer;
	}

	public void setDealer(Integer dealer) {
		this.dealer = dealer;
	}
}
