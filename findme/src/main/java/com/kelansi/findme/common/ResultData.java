package com.kelansi.findme.common;

import java.io.Serializable;

/**
 * ResultData
 */
@SuppressWarnings("serial")
public class ResultData implements Serializable {

	private Message message;
	private Object data;

	public ResultData() {

	}

	public ResultData(Message message, Object data) {
		this.message = message;
		this.data = data;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
