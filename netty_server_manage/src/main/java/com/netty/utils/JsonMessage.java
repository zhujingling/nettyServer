package com.netty.utils;

import org.json.JSONObject;

public class JsonMessage {
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";



	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {
		JSONObject jsonMap = new JSONObject();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		return jsonMap.toString();
	}

	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(Object message) {
		JSONObject jsonMap = new JSONObject();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		return jsonMap.toString();
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		JSONObject jsonMap = new JSONObject();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		return jsonMap.toString();
	}
	
	


}
