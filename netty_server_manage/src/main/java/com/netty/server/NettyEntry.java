package com.netty.server;

import io.netty.util.internal.SystemPropertyUtil;

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
		String keyStoreFilePath = SystemPropertyUtil.get("keystore.file.path");
		if (keyStoreFilePath == null || keyStoreFilePath.isEmpty()) {
			System.out
					.println("ERROR: System property keystore.file.path not set. Exiting now!");
			System.exit(1);
		}

		String keyStoreFilePassword = SystemPropertyUtil
				.get("keystore.file.password");
		if (keyStoreFilePassword == null || keyStoreFilePassword.isEmpty()) {
			System.out
					.println("ERROR: System property keystore.file.password not set. Exiting now!");
			System.exit(1);
		}
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
