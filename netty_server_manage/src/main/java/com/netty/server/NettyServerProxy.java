package com.netty.server;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class NettyServerProxy {

	private static WebSocketServerHandshaker handshaker;
	private static final String WEBSOCKET_PATH = "/websocket";
	private static final String NEWLINE = "\r\n";
	protected Logger log = Logger.getLogger(this.getClass().getName());

	public static void httpHandleRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		  // Handle a bad request.
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.getMethod() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Send the demo page and favicon.ico
        if ("/".equals(req.getUri())) {
        	
//            ByteBuf content = getContent(getWebSocketLocation(req));
        	ByteBuf content = Unpooled.copiedBuffer(getWebSocketLocation(req), CharsetUtil.US_ASCII);
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
            return;
        }
        if ("/favicon.ico".equals(req.getUri())) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
	}

	public static void handleWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame) {

		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(),
					(CloseWebSocketFrame) frame.retain());
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
		}

		// Send the uppercase string back.
		String request = ((TextWebSocketFrame) frame).text();
	
		ctx.channel().write(new TextWebSocketFrame(request.toUpperCase()));
	}

	public static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, FullHttpResponse res) {
		// Generate an error page if response getStatus code is not OK (200).
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			setContentLength(res, res.content().readableBytes());
		}

		// Send the response and close the connection if necessary.
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	

    public static ByteBuf getContent(String webSocketLocation) {
        return Unpooled.copiedBuffer(
                "<html><head><title>Web Socket Test</title></head>" + NEWLINE +
                "<body>" + NEWLINE +
                "<script type=\"text/javascript\">" + NEWLINE +
                "var socket;" + NEWLINE +
                "if (!window.WebSocket) {" + NEWLINE +
                "  window.WebSocket = window.MozWebSocket;" + NEWLINE +
                '}' + NEWLINE +
                "if (window.WebSocket) {" + NEWLINE +
                "  socket = new WebSocket(\"" + webSocketLocation + "\");" + NEWLINE +
                "  socket.onmessage = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');" + NEWLINE +
                "    ta.value = ta.value + '\\n' + event.data" + NEWLINE +
                "  };" + NEWLINE +
                "  socket.onopen = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');" + NEWLINE +
                "    ta.value = \"Web Socket opened!\";" + NEWLINE +
                "  };" + NEWLINE +
                "  socket.onclose = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');" + NEWLINE +
                "    ta.value = ta.value + \"Web Socket closed\"; " + NEWLINE +
                "  };" + NEWLINE +
                "} else {" + NEWLINE +
                "  alert(\"Your browser does not support Web Socket.\");" + NEWLINE +
                '}' + NEWLINE +
                NEWLINE +
                "function send(message) {" + NEWLINE +
                "  if (!window.WebSocket) { return; }" + NEWLINE +
                "  if (socket.readyState == WebSocket.OPEN) {" + NEWLINE +
                "    socket.send(message);" + NEWLINE +
                "  } else {" + NEWLINE +
                "    alert(\"The socket is not open.\");" + NEWLINE +
                "  }" + NEWLINE +
                '}' + NEWLINE +
                "</script>" + NEWLINE +
                "<form onsubmit=\"return false;\">" + NEWLINE +
                "<input type=\"text\" name=\"message\" value=\"Hello, World!\"/>" +
                "<input type=\"button\" value=\"Send Web Socket Data\"" + NEWLINE +
                "       onclick=\"send(this.form.message.value)\" />" + NEWLINE +
                "<h3>Output</h3>" + NEWLINE +
                "<textarea id=\"responseText\" style=\"width:500px;height:300px;\"></textarea>" + NEWLINE +
                "</form>" + NEWLINE +
                "</body>" + NEWLINE +
                "</html>" + NEWLINE, CharsetUtil.US_ASCII);
    }
    
    

    private static String getWebSocketLocation(FullHttpRequest req) {
        return "ws://" + req.headers().get(HOST) + WEBSOCKET_PATH;
    }

}
