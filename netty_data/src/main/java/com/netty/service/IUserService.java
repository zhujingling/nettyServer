package com.netty.service;

public interface IUserService {

	String getUserInfo(int userId,String passWord);
	String getFriends(int userId);
}
