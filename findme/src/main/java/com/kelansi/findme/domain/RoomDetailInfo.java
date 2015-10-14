package com.kelansi.findme.domain;


public class RoomDetailInfo extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5608034604878640779L;
	
	private Long dealerId;
	
	/** 枚举形式 房型*/
	private Integer roomModel;
	
	/** 枚举形式 面积 */
	private Integer size;
	
	/** 枚举形式 风格 */
	private Integer style;
	
	/** 图片地址 */
	private String picturePaths;
	
	/** 微信服务器端保存的商品的上传ID */
	private String mediaIds;

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
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
}
