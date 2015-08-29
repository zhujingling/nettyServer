package com.netty.command;

import java.nio.ByteOrder;

public class Command {
	 /*
     * 统一的字节流：网络字节流-大字节流
     */
    public final static ByteOrder BYTE_ORDER        = ByteOrder.BIG_ENDIAN;

	 /*
     * 统一通讯协议格式 帧头 帧尾
     */
    public final static byte[]    HEAD              = { (byte) 0xFA, (byte) 0xFB };

    public final static byte[]    TAIL              = { (byte) 0xBF, (byte) 0xAF };

	    // 帧头长
	    public final static int       LENGTH_HEAD       = HEAD.length;

	    // 帧尾长
	    public final static int       LENGTH_TAIL       = TAIL.length;

	    // <LENGTH>长
	    public final static int       LENGTH_LENGTH     = 4;

	    // 命令长
	    public final static int       LENGTH_CMD        = 4;

	    // ROLE
	    public final static int       LENGTH_ROLE       = 1;

	    // FLAG
	    public final static int       LENGTH_FLAG       = 2;

	    // SRC ID
	    public final static int       LENGTH_SRC_ID     = 4;

	    // DST ID
	    public final static int       LENGTH_DST_ID     = 4;

	    // 数据域长
	    public final static int       LENGTH_DATA_FIELD = 2;


	public static enum CMD {
		UC_USER_INFO(0x00A40001);

		private int val;

		private CMD(int val) {
			this.val = val;

		}

		public int val() {
			return this.val;
		}

	}

	public final static class USER {
		// 用户中心-用户信息
		public final static int UC_USER_INFO = 0x00A40001;
	}

}
