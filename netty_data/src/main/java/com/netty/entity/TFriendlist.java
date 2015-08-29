package com.netty.entity;

// Generated 2015-8-27 11:17:02 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TFriendlist generated by hbm2java
 */
@Entity(name = "t_friendlist")
public class TFriendlist implements java.io.Serializable {

	private String id;
	private TUsers TUsersByFid;
	private TUsers TUsersByUserId;

	public TFriendlist() {
	}

	public TFriendlist(String id) {
		this.id = id;
	}

	public TFriendlist(String id, TUsers TUsersByFid, TUsers TUsersByUserId) {
		this.id = id;
		this.TUsersByFid = TUsersByFid;
		this.TUsersByUserId = TUsersByUserId;
	}

	@Id
	@Column(name = "Id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FId")
	public TUsers getTUsersByFid() {
		return this.TUsersByFid;
	}

	public void setTUsersByFid(TUsers TUsersByFid) {
		this.TUsersByFid = TUsersByFid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId")
	public TUsers getTUsersByUserId() {
		return this.TUsersByUserId;
	}

	public void setTUsersByUserId(TUsers TUsersByUserId) {
		this.TUsersByUserId = TUsersByUserId;
	}

}
