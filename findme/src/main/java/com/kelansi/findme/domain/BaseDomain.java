package com.kelansi.findme.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8682273308213177531L;

	private Long id;
	
	private Date createDate;
	
	private Date modifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	

}
