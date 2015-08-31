package com.netty.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.netty.command.Command;
import com.netty.pkg.MessagePkg;
import com.netty.service.IUserService;
import com.netty.utils.ApplicationContextUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServerHandler extends ChannelHandlerAdapter {
	public static ChannelGroup channels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);
	// map用于channel和具体的用户名绑定起来，可以根据具体业务实现认证信息和channel绑定
	static final Map<Channel, String> channelMap = Collections
			.synchronizedMap(new HashMap<Channel, String>());
	// userInfo保存登陆的用户信息
	static final List<String> userInfoList = new ArrayList<String>();
	private IUserService userService = ApplicationContextUtil
			.getBean("userServiceImpl");
	protected Logger log = Logger.getLogger(this.getClass().getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		 if (msg instanceof FullHttpRequest) {
			 NettyServerProxy.httpHandleRequest(ctx, (FullHttpRequest) msg);
	        } else if (msg instanceof WebSocketFrame) {
	        	NettyServerProxy.handleWebSocketFrame(ctx, (WebSocketFrame) msg);
	        }
		
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress()
					+ " 加入\n");
		}
		channels.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress()
					+ " 离开\n");
		}
		userInfoList.remove(channelMap.get(ctx.channel()));
		channelMap.remove(ctx.channel());
		channels.remove(ctx.channel());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress()
				+ "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		userInfoList.remove(channelMap.get(ctx.channel()));
		channelMap.remove(ctx.channel());
		System.out.println("SimpleChatClient:" + incoming.remoteAddress()
				+ "掉线");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Channel incoming = ctx.channel();
		log.error("SimpleChatClient:" + incoming.remoteAddress() + "异常");
		userInfoList.remove(channelMap.get(ctx.channel()));
		channelMap.remove(ctx.channel());
		// 当出现异常就关闭连接
		ctx.close();
	}
	
	//通过map的value值去获取相对应得key，这里即用户所建立的channel。
		public Channel getKey(Map<Channel, String> map, String value) {
			Channel key = null;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				if (entry.getValue().equals(value)) {
					key = (Channel) entry.getKey();
				}
			}
			return key;
		}

}
