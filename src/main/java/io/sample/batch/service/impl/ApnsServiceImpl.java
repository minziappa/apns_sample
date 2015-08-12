package io.sample.batch.service.impl;

import io.sample.batch.bean.CommonBean;
import io.sample.batch.bean.model.ApnsGameModel;
import io.sample.batch.dao.SampleDao;
import io.sample.batch.service.ApnsService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ApnsServiceImpl implements ApnsService {

	final Logger logger = LoggerFactory.getLogger(ApnsServiceImpl.class);

	@Autowired
	private CommonBean commonbean;
	@Autowired
	private SampleDao sampleDao;

	/**
     * Get a Payload instance.
     * 
     * @param  Map<String, Object>
     *         mapOption
     * 
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.7
     */
	private PushNotificationPayload getPayload(Map<String, Object> mapOption) throws Exception {

		// Prepare a simple payload to push
        PushNotificationPayload payload = PushNotificationPayload.complex();
        if(mapOption == null) {
        	payload.addSound("default");
        } else {
        	if(mapOption.get("badge") != null) {
                payload.addBadge(Integer.parseInt((String)mapOption.get("badge")));        		
        	}
        	if(mapOption.get("sound") != null) {
                payload.addSound((String)mapOption.get("sound"));
        	} else {
        		payload.addSound("default");
        	}
        }

		return payload;
	}

	/**
     * Get the game list when the Tomcat start.
     * 
     * 
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.7
     */
	public void init() throws SQLException {

		List<ApnsGameModel> apnsGameList = null;

		try {
			apnsGameList = sampleDao.selectGameList();
			if(apnsGameList != null) {

				for(ApnsGameModel apnsGame : apnsGameList) {
					logger.info("GameCertificateName >>>> " + apnsGame.getGameCertificateName());
					logger.info("GameDomain >>>> " + apnsGame.getGameDomain());
					logger.info("GameExplain >>>> " + apnsGame.getGameExplain());
					logger.info("GameId >>>> " + apnsGame.getGameId());
					logger.info("GameSecret >>>> " + apnsGame.getGameSecret());
					logger.info("GameStatusFlag >>>> " + apnsGame.getGameStatusFlag());
					logger.info("GameTitle >>>> " + apnsGame.getGameTitle());
					logger.info("GameTokenPassword >>>> " + apnsGame.getGameTokenPassword());
				}
				
			}
			commonbean.setApnsGameList(apnsGameList);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

	}

	/**
     * Check a GameId.
     * 
     * @param  String
     *         gameId
     * 
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.7
     */
	@Override
	public boolean checkGameId(String gameId) throws Exception {

		List<ApnsGameModel> apnsGameList = commonbean.getApnsGameList();
		for(ApnsGameModel apnsGameModel : apnsGameList) {
			if (apnsGameModel.getGameId().equals(gameId)) {
				return true;
			}
		}

		return false;
	}

	/**
     * Get the game property
     * 
     * @param  String
     *         gameId
     * 
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.7
     */
	@Override
	public ApnsGameModel getApnsGame(String gameId) throws Exception {

		List<ApnsGameModel> apnsGameList = commonbean.getApnsGameList();
		ApnsGameModel apnsGame = null;
		for(ApnsGameModel apnsGameModel : apnsGameList) {
			if (apnsGameModel.getGameId().equals(gameId)) {
				// Get a GameId as the user.
				apnsGame = apnsGameModel;
				break;
			}
		}

		return apnsGame;
	}

	/**
     * Send a message
     * 
     * @param  String
     *         deviceToken
     * @param  String
     *         message
     * @param  ApnsGameModel
     *         apnsGame
     * @param  boolean
     *         blnSandBox
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.6
     */
	@Override
	public List<PushedNotification> sendMessage(String deviceToken, String message, 
			ApnsGameModel apnsGame, boolean blnSandBox) throws Exception {

		List<PushedNotification> notifications = null;

		String tokenPassword = apnsGame.getGameTokenPassword();
		byte[] sslCertificate = apnsGame.getGameCertificateFile();

		// Prepare a simple payload to push
        PushNotificationPayload payload = PushNotificationPayload.complex();
        payload.addAlert(message);
        payload.addSound("default");
        String[] devices = {deviceToken};
        InputStream isP12 = new ByteArrayInputStream(sslCertificate);
        InputStream keystore = new BufferedInputStream(isP12);

        try {
            // Push your custom payload
            notifications = Push.payload(payload, keystore, tokenPassword, blnSandBox, devices);
		} catch (Exception e) {
			logger.info("Exception error", e);
			return null;
		}

        return notifications;
	}

	/**
     * Send a message
     * 
     * @param  String[]
     *         devices
     * @param  String
     *         message
     * @param  ApnsGameModel
     *         apnsGame
     * @param  boolean
     *         blnSandBox
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.6
     */
	@Override
	public List<PushedNotification> sendMessage(String[] devices, String message, 
			ApnsGameModel apnsGame, boolean blnSandBox) throws Exception {
        return sendMessage(devices, message, 1, apnsGame, blnSandBox);
	}

	/**
     * Send a message
     * 
     * @param  String[]
     *         devices
     * @param  String
     *         message
     * @param  int
     *         threads
     * @param  ApnsGameModel
     *         apnsGame
     * @param  boolean
     *         blnSandBox
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.6
     */
	@Override
	public List<PushedNotification> sendMessage(String[] devices, String message, int threads, 
			ApnsGameModel apnsGame, boolean blnSandBox) throws Exception {

		List<PushedNotification> notifications = null;

		String tokenPassword = apnsGame.getGameTokenPassword();
		byte[] sslCertificate = apnsGame.getGameCertificateFile();

		// Prepare a simple payload to push
        PushNotificationPayload payload = getPayload(null);
        payload.addAlert(message);

        InputStream isP12 = new ByteArrayInputStream(sslCertificate);
        InputStream keystore = new BufferedInputStream(isP12);

        try {
            // Push your custom payload
            notifications = Push.payload(payload, keystore, tokenPassword, blnSandBox, threads, devices);
		} catch (Exception e) {
			logger.info("sendMessage Exception >> ", e);
			return null;
		}

        return notifications;
	}

	/**
     * Send a message
     * 
     * @param  String[]
     *         devices
     * @param  String
     *         message
     * @param  Map<String, Object>
     *         mapOption
     * @param  int
     *         threads
     * @param  ApnsGameModel
     *         apnsGame
     * @param  boolean
     *         blnSandBox
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.6
     */
	@Override
	public List<PushedNotification> sendMessage(String[] devices, String message, Map<String, Object> mapOption, 
			int threads, ApnsGameModel apnsGame, boolean blnSendBox) throws Exception {

		List<PushedNotification> notifications = null;

		String tokenPassword = apnsGame.getGameTokenPassword();
		byte[] sslCertificate = apnsGame.getGameCertificateFile();

		// Prepare a simple Payload to push
        PushNotificationPayload payload = getPayload(mapOption);
        payload.addAlert(message);

        InputStream isP12 = new ByteArrayInputStream(sslCertificate);
        InputStream keystore = new BufferedInputStream(isP12);

        try {
            // Push your custom payload
            notifications = Push.payload(payload, keystore, tokenPassword, blnSendBox, threads, devices);
		} catch (Exception e) {
			logger.error("sendMessage Exception >> ", e);
			return null;
		}

        return notifications;
	}

}