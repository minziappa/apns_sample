package io.sample.batch.bean.model;

import java.io.Serializable;
import java.util.Date;

public class ApnsUuModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuId;
	private String uuNickname;
	private String gameId;
	private String uuDeviceToken;
	private String uuDeny;
	private String uuStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public String getUuNickname() {
		return uuNickname;
	}
	public void setUuNickname(String uuNickname) {
		this.uuNickname = uuNickname;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getUuDeviceToken() {
		return uuDeviceToken;
	}
	public void setUuDeviceToken(String uuDeviceToken) {
		this.uuDeviceToken = uuDeviceToken;
	}
	public String getUuDeny() {
		return uuDeny;
	}
	public void setUuDeny(String uuDeny) {
		this.uuDeny = uuDeny;
	}
	public String getUuStatusFlag() {
		return uuStatusFlag;
	}
	public void setUuStatusFlag(String uuStatusFlag) {
		this.uuStatusFlag = uuStatusFlag;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}