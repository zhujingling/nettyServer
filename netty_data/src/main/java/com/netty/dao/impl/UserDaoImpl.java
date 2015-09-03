package com.netty.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.netty.dao.BaseDao;
import com.netty.dao.IUserDao;
import com.netty.entity.TFriendlist;
import com.netty.entity.TUsers;

@Repository
public class UserDaoImpl extends BaseDao implements IUserDao {
	// 用户信息
	public TUsers getUserInfo(int userId, String passWord) {
		// TODO Auto-generated method stub
		String hqlString = "From t_users where UserId=? and PassWord=?";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);
		query.setString(1, passWord);
		TUsers user = (TUsers) query.uniqueResult();
		return user;
	}

	// 用户好友
	public List<TFriendlist> getFriends(int userId) {
		// TODO Auto-generated method stub
		String hqlString = "From t_friendlist where UserId=? ";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);

		List<TFriendlist> list = query.list();
		return list;
	}

	// 检验用户
	public boolean checkUser(int userId) {
		// TODO Auto-generated method stub
		String hqlString = "From t_users where UserId=?";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);
		TUsers user = (TUsers) query.uniqueResult();
		if (user != null) {
			return true;
		}
		return false;
	}

	// 校验密码
	public boolean checkPwd(int userId, String passWord) {
		// TODO Auto-generated method stub
		String hqlString = "From t_users where UserId=?";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);
		TUsers user = (TUsers) query.uniqueResult();
		if (user!=null&&passWord.equals(user.getPassWord())) {
			return true;
		}
		return false;
	}

}
