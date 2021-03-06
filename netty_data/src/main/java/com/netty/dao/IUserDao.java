package com.netty.dao;

import java.util.List;

import com.netty.entity.TFriendlist;
import com.netty.entity.TUsers;

public interface IUserDao {
	
	/*
	 * 用户是否存在
	 */
	boolean checkUser(int userId);	
	/*
	 *检验密码
	 */
	
	boolean checkPwd(int userId ,String passWord);
	/*
	 * 验证通过之后，得到用户信息
	 */
	TUsers getUserInfo(int userId,String passWord);
	/*
	 * 通过用户id得到好友
	 */
	List<TFriendlist> getFriends(int userId);
	
	/*
	 * 登录验证
	 */
	
}
