package com.netty.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NettyEntry implements ServletContextListener {
	private NettyServer _nettyServer;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		System.err.println("Start to destroy the netty entry...");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		startNettyServer();
	}

	private void startNettyServer() {
		try {
			if (null == _nettyServer) {
				_nettyServer = new NettyServer();

			}
			_nettyServer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
