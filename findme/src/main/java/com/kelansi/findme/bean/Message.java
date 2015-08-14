package com.kelansi.findme.bean;

import java.io.Serializable;
import java.util.Map;

import com.kelansi.findme.utils.SpringUtils;

@SuppressWarnings("rawtypes")
public class Message implements Serializable{

	private static final long	serialVersionUID	= -7932537842656418088L;

	public static Message SUCCESS_MESSAGE = Message.success("shop.message.success");
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;
	
	/** 
	 * 返回数据对象,方便前台在获取消息的同时获取返回数据 
	 * 如放入map{"name" : "test" }, 前台使用callbackData.data.name获取test
	 * */
	private Map data;

	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public Message() {

	}

	/**
	 * 初始化一个新创建的 Message 对象
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 */
	public Message(Type type, String content, Object... args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static Message success(String content, Object... args) {
		return new Message(Type.success, content, args);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 警告消息
	 */
	public static Message warn(String content, Object... args) {
		return new Message(Type.warn, content, args);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 错误消息
	 */
	public static Message error(String content, Object... args) {
		return new Message(Type.error, content, args);
	}
	
	/**
	 * 带返回数据的消息构造
	 * 
	 * @param type
	 * @param content
	 * @param data
	 * @param args
	 */
	public Message(Type type, String content, Map data, Object... args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);;
		this.data = data;
	}
	
	/**
	 * 带返回数据的正确消息
	 * 
	 * @param type
	 * @param content
	 * @param data
	 * @param args
	 */
	public static Message successData(String content, Map data, Object... args) {
		return new Message(Type.success, content, data, args);
	}
	
	/**
	 * 带返回数据的警告消息
	 * 
	 * @param type
	 * @param content
	 * @param data
	 * @param args
	 */
	public static Message warnData(String content, Map data, Object... args) {
		return new Message(Type.warn, content, data, args);
	}
	
	/**
	 * 带返回数据的错误消息
	 * 
	 * @param type
	 * @param content
	 * @param data
	 * @param args
	 */
	public static Message errorData(String content, Map data, Object... args) {
		return new Message(Type.error, content, data, args);
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取数据
	 * @return
	 */
	public Map getData() {
		return data;
	}

	/**
	 * 设置数据
	 * @param data
	 */
	public void setData(Map data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return SpringUtils.getMessage(content);
	}

}

