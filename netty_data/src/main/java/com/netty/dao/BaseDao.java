package com.netty.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactoryMysql() {
		return sessionFactory;
	}

	public void setSessionFactoryMysql(SessionFactory sessionFactoryMysql) {
		this.sessionFactory = sessionFactoryMysql;
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

}
