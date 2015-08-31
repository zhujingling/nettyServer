package com.netty.server;

import javax.net.ssl.SSLEngine;

import com.netty.security.SecureChatSslContextFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub

		ch.pipeline().addLast("http-codec", new HttpServerCodec());
		ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
		ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
		// 消息解码器
		ch.pipeline().addLast(new StringEncoder());
		ch.pipeline().addLast(new StringDecoder());
		ch.pipeline().addLast(new NettyServerHandler());

		//现在还搞不明白，加上这个SSL上下文安全，与客户端刚连接上就断掉。注释掉就可以正常与客户端通讯
		SSLEngine engine = SecureChatSslContextFactory.getInstance().serverContext().createSSLEngine();
		engine.setUseClientMode(false);

		ch.pipeline().addLast("ssl", new SslHandler(engine));

		System.out.println("SimpleChatClient:" + ch.remoteAddress() + "连接上");

	}

}
