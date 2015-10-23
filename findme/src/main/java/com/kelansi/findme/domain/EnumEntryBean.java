package com.kelansi.findme.domain;

import java.math.BigDecimal;

public class EnumEntryBean {

	public String enumKey;
	
	public Integer enumValue;
	
	public Integer enumNum;
	
	public Integer type;
	
	public BigDecimal numBegin;
	
	public BigDecimal numEnd;

	public EnumEntryBean(){}
	
	public EnumEntryBean(String enumKey, Integer enumValue, Integer enumNum,
			Integer type, BigDecimal numBegin, BigDecimal numEnd) {
		super();
		this.enumKey = enumKey;
		this.enumValue = enumValue;
		this.enumNum = enumNum;
		this.type = type;
		this.numBegin = numBegin;
		this.numEnd = numEnd;
	}

	public String getEnumKey() {
		return enumKey;
	}

	public void setEnumKey(String enumKey) {
		this.enumKey = enumKey;
	}

	public Integer getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}

	public Integer getEnumNum() {
		return enumNum;
	}

	public void setEnumNum(Integer enumNum) {
		this.enumNum = enumNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getNumBegin() {
		return numBegin;
	}

	public void setNumBegin(BigDecimal numBegin) {
		this.numBegin = numBegin;
	}

	public BigDecimal getNumEnd() {
		return numEnd;
	}

	public void setNumEnd(BigDecimal numEnd) {
		this.numEnd = numEnd;
	}
}
