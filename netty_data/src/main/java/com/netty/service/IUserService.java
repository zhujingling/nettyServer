package com.netty.service;

public interface IUserService {

	/*
	 * 得到用户信息
	 */
	String getUserInfo(int userId,String passWord);
	/*
	 * 通过用户id得到好友
	 */
	String getFriends(int userId);
	/*
	 *校验用户
	 */
	boolean checkUser(int userId);
	/*
	 * 校验密码
	 */
	boolean checkPwd(int userId,String passWord);
}
