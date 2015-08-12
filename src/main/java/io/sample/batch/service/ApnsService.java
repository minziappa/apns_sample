package io.sample.batch.service;

import io.sample.batch.bean.model.ApnsGameModel;

import java.util.List;
import java.util.Map;

import javapns.notification.PushedNotification;

public interface ApnsService {

	public void init() throws Exception;
	public boolean checkGameId(String gameId) throws Exception;
	public ApnsGameModel getApnsGame(String gameId) throws Exception;
	public List<PushedNotification> sendMessage(String deviceToken, String message, ApnsGameModel apnsGame, boolean blnSandBox) throws Exception;
	public List<PushedNotification> sendMessage(String[] devices, String message, ApnsGameModel apnsGame, boolean blnSandBox) throws Exception;
	public List<PushedNotification> sendMessage(String[] devices, String message, int threads, ApnsGameModel apnsGame, boolean blnSandBox) throws Exception;
	public List<PushedNotification> sendMessage(String[] devices, String message, Map<String, Object> mapOption, int threads, ApnsGameModel apnsGame, boolean blnSandBox) throws Exception;

}