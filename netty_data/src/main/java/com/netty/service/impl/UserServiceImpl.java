package com.netty.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netty.dao.IUserDao;
import com.netty.entity.TFriendlist;
import com.netty.entity.TUsers;
import com.netty.service.IUserService;

@Service(value = "userServiceImpl")
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public String getUserInfo(int userId, String passWord) {
		// TODO Auto-generated method stub
		if (!checkUser(userId)) {
			return "-1";
		}else if (checkPwd(userId, passWord)) {
			return "0";
		}
		TUsers user = userDao.getUserInfo(userId, passWord);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if (user != null) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("id", user.getId());
			jsonUser.put("idUser", user.getUserId());
			jsonUser.put("userName", user.getUserName());
			jsonUser.put("userNick", user.getUserNick());
			jsonUser.put("passWord", user.getPassWord());
			jsonUser.put("email", user.getEmail());
			jsonUser.put("Tel", user.getTel());
			jsonUser.put("birthday", user.getBirthday());

			jsonArray.put(jsonUser);
		}

		jsonObject.put("userInfo", jsonArray);
		return jsonObject.toString();

	}

	public String getFriends(int userId) {
		// TODO Auto-generated method stub
		List<TFriendlist> list = userDao.getFriends(userId);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (TFriendlist tFriendlist : list) {
			TUsers user = tFriendlist.getTUsersByFid();
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("id", user.getId());
			jsonUser.put("idUser", user.getUserId());
			jsonUser.put("userName", user.getUserName());
			jsonUser.put("userNick", user.getUserNick());
			jsonUser.put("passWord", user.getPassWord());
			jsonUser.put("email", user.getEmail());
			jsonUser.put("Tel", user.getTel());
			jsonUser.put("birthday", user.getBirthday());

			jsonArray.put(jsonUser);
		}

		jsonObject.put("userInfo", jsonArray);
		return jsonObject.toString();
	}

	public boolean checkUser(int userId) {
		// TODO Auto-generated method stub
		return userDao.checkUser(userId);
	}

	public boolean checkPwd(int userId, String passWord) {
		// TODO Auto-generated method stub
		return userDao.checkPwd(userId, passWord);
	}

}
