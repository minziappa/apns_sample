package io.sample.batch.bean.model;

import java.io.Serializable;
import java.util.Date;

public class ApnsHistoryModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private long historyId;
	private Date historySendTime;
	private String gameId;
	private String adminId;
	private String adminName;
	private String historyMessage;
	private String historyUuId;
	private String historyKindFlag;
	private String historyStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}
	public Date getHistorySendTime() {
		return historySendTime;
	}
	public void setHistorySendTime(Date historySendTime) {
		this.historySendTime = historySendTime;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getHistoryMessage() {
		return historyMessage;
	}
	public void setHistoryMessage(String historyMessage) {
		this.historyMessage = historyMessage;
	}
	public String getHistoryUuId() {
		return historyUuId;
	}
	public void setHistoryUuId(String historyUuId) {
		this.historyUuId = historyUuId;
	}
	public String getHistoryKindFlag() {
		return historyKindFlag;
	}
	public void setHistoryKindFlag(String historyKindFlag) {
		this.historyKindFlag = historyKindFlag;
	}
	public String getHistoryStatusFlag() {
		return historyStatusFlag;
	}
	public void setHistoryStatusFlag(String historyStatusFlag) {
		this.historyStatusFlag = historyStatusFlag;
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