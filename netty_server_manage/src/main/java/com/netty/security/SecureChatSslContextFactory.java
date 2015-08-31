package com.netty.security;

import io.netty.util.internal.SystemPropertyUtil;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Security;

public class SecureChatSslContextFactory {
	  private static final String PROTOCOL = "TLS";
	    private final SSLContext _serverContext;

	    /**
	     * Returns the singleton instance for this class
	     */
	    public static SecureChatSslContextFactory getInstance() {
	        return SingletonHolder.INSTANCE;
	    }

	    /**
	     * SingletonHolder is loaded on the first execution of Singleton.getInstance() or the first access to
	     * SingletonHolder.INSTANCE, not before.
	     *
	     * See http://en.wikipedia.org/wiki/Singleton_pattern
	     */
	    private interface SingletonHolder {
	    	SecureChatSslContextFactory INSTANCE = new SecureChatSslContextFactory();
	    }

	    /**
	     * Constructor for singleton
	     */
	    private SecureChatSslContextFactory() {
	        SSLContext serverContext = null;
	        try {
	            // Key store (Server side certificate)
	            String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
	            if (algorithm == null) {
	                algorithm = "SunX509";
	            }

	            try {
	                String keyStoreFilePath = SystemPropertyUtil.get("keystore.file.path");
	                String keyStoreFilePassword = SystemPropertyUtil.get("keystore.file.password");

	                KeyStore ks = KeyStore.getInstance("JKS");
	                FileInputStream fin = new FileInputStream(keyStoreFilePath);
	                ks.load(fin, keyStoreFilePassword.toCharArray());

	                // Set up key manager factory to use our key store
	                // Assume key password is the same as the key store file
	                // password
	                KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
	                kmf.init(ks, keyStoreFilePassword.toCharArray());

	                // Initialise the SSLContext to work with our key managers.
	                serverContext = SSLContext.getInstance(PROTOCOL);
	                serverContext.init(kmf.getKeyManagers(), null, null);
	            } catch (Exception e) {
	                throw new Error("Failed to initialize the server-side SSLContext", e);
	            }
	        } catch (Exception ex) {
	            System.exit(1);
	        } finally {
	            _serverContext = serverContext;
	        }
	    }

	    /**
	     * Returns the server context with server side key store
	     */
	    public SSLContext serverContext() {
	        return _serverContext;
	    }
}
