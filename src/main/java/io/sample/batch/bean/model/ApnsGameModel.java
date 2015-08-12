package io.sample.batch.bean.model;

import java.io.Serializable;
import java.util.Date;

public class ApnsGameModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gameId;
	private String gameDomain;
	private String gameTitle;
	private String gameExplain;
	private String gameSecret;
	private String gameTokenPassword;
	private String gameCertificateName;
	private byte[] gameCertificateFile;
	private String gameStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameDomain() {
		return gameDomain;
	}
	public void setGameDomain(String gameDomain) {
		this.gameDomain = gameDomain;
	}
	public String getGameTitle() {
		return gameTitle;
	}
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	public String getGameExplain() {
		return gameExplain;
	}
	public void setGameExplain(String gameExplain) {
		this.gameExplain = gameExplain;
	}
	public String getGameSecret() {
		return gameSecret;
	}
	public void setGameSecret(String gameSecret) {
		this.gameSecret = gameSecret;
	}
	public String getGameTokenPassword() {
		return gameTokenPassword;
	}
	public void setGameTokenPassword(String gameTokenPassword) {
		this.gameTokenPassword = gameTokenPassword;
	}
	public String getGameCertificateName() {
		return gameCertificateName;
	}
	public void setGameCertificateName(String gameCertificateName) {
		this.gameCertificateName = gameCertificateName;
	}
	public byte[] getGameCertificateFile() {
		return gameCertificateFile;
	}
	public void setGameCertificateFile(byte[] gameCertificateFile) {
		this.gameCertificateFile = gameCertificateFile;
	}
	public String getGameStatusFlag() {
		return gameStatusFlag;
	}
	public void setGameStatusFlag(String gameStatusFlag) {
		this.gameStatusFlag = gameStatusFlag;
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