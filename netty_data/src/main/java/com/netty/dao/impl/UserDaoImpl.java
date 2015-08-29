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

	public TUsers getUserInfo(int userId, String passWord) {
		// TODO Auto-generated method stub
		String hqlString = "From t_users where UserId=? and PassWord=?";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);
		query.setString(1, passWord);
		
		TUsers user=(TUsers) query.uniqueResult();
		return user;
	}

	public List<TFriendlist> getFriends(int userId) {
		// TODO Auto-generated method stub
		String hqlString = "From t_friendlist where UserId=? ";
		Query query = getSession().createQuery(hqlString);
		query.setInteger(0, userId);
		
		List<TFriendlist> list=query.list();
		return list;
	}


}
