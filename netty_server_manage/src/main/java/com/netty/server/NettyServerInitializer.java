package com.netty.server;

import javax.net.ssl.SSLEngine;

import com.netty.security.SecureChatSslContextFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub

		ch.pipeline().addLast("codec-http", new HttpServerCodec());
		ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
		// 消息解码器
		ch.pipeline().addLast(
				new ObjectDecoder(1024, ClassResolvers
						.weakCachingConcurrentResolver(this.getClass()
								.getClassLoader())));
		// 消息编码器
		ch.pipeline().addLast(new ObjectEncoder());
		ch.pipeline().addLast(new NettyServerHandler());

		SSLEngine engine = SecureChatSslContextFactory.getServerContext()
				.createSSLEngine();
		engine.setUseClientMode(false);

		ch.pipeline().addLast("ssl", new SslHandler(engine));

		System.out.println("SimpleChatClient:" + ch.remoteAddress() + "连接上");

	}

}
