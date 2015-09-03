package com.netty.utils;

import java.util.UUID;

public class CommonUtil {
	//生成一个token
	public static String token() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
