package com.netty.pkg;

import java.io.Serializable;

import com.netty.command.Command;
import com.netty.command.Role;

public class MessagePkg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 帧头
	 */
	public byte[] head;

	/*
	 * 命令
	 */
	public int cmd;

	/*
	 * 角色
	 */
	public byte role;

	/*
	 * 源ID
	 */
	public int fromUser;

	/*
	 * 目标ID
	 */
	public int toUser;
	
	/*
	 * 内容
	 */
	public String message;


	/*
	 * 时间
	 */
	public long time;



	/*
	 * 帧尾
	 */
	public byte[] tail;

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public byte getRole() {
		return role;
	}

	public void setRole(byte role) {
		this.role = role;
	}

	

	public int getFromUser() {
		return fromUser;
	}

	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	public int getToUser() {
		return toUser;
	}

	public void setToUser(int toUser) {
		this.toUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	
	public byte[] getTail() {
		return tail;
	}

	public void setTail(byte[] tail) {
		this.tail = tail;
	}

	

	public void reset() {
		this.head = Command.HEAD;
		this.cmd = 0;
		this.role = Role.ROLE_UNKNOW;
		this.tail = Command.TAIL;
	}


	public static MessagePkg rawPkg() {
		MessagePkg pkg = new MessagePkg();
		pkg.head = Command.HEAD;
		pkg.cmd = 0;
		pkg.role = Role.ROLE_UNKNOW;
		pkg.tail = Command.TAIL;

		return pkg;
	}

}
