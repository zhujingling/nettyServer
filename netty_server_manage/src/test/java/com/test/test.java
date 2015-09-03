package com.test;

import org.junit.Test;

import com.netty.service.IUserService;
import com.netty.utils.CommonUtil;
import com.util.ApplicationContextUtil;



public class test {
	 private IUserService userService = ApplicationContextUtil
	 .getBean("userServiceImpl");
	@Test
	public void getUserInfo() {
		try {
			Thread th = new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					System.out.println("1");
					System.out.println(1);

				}
			});
			th.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	@Test
	public void UUID()	{
		System.out.println(CommonUtil.token());
	}

	@Test
	public void outSpace() {
		System.out.println(userService.checkUser(3));
		System.out.println(userService.getFriends(1));
		System.out.println(userService.getUserInfo(1,"1"));
	}
}
