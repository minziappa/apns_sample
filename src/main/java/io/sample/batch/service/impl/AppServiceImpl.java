package io.sample.batch.service.impl;

import io.sample.batch.bean.model.ApnsGameModel;
import io.sample.batch.bean.model.ApnsUuModel;
import io.sample.batch.dao.SampleDao;
import io.sample.batch.service.ApnsService;
import io.sample.batch.service.AppService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Notification Sample
 * 
 * @author Woong-joon Kim
 */
public class AppServiceImpl implements AppService {

	private Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
	final Logger messageBatch = LoggerFactory.getLogger("messageBatch");
	final Logger messageError = LoggerFactory.getLogger("messageError");

	private static final int LIMIT_CNT = 500;

	@Autowired
	private SampleDao sampleDao;
	@Autowired
    private Configuration configuration;
	@Autowired
	private ApnsService apnsService;

	private List<ApnsUuModel> selectUserList(int startRecord, int limitCnt, String gameId) throws Exception {

		List<String> uuIdList = null;

		// Get a user device token from DB.
		Map<String, Object> mapSelectBtUserList = new HashMap<String, Object>();
		mapSelectBtUserList.put("startRecord", startRecord);
		mapSelectBtUserList.put("limitCnt", limitCnt);

		try {
			uuIdList = sampleDao.selectBtUserList(mapSelectBtUserList);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if (uuIdList.size() < 1) {
			logger.info("uuIdList size >>>>>> 0");
			return null;
		}

		logger.info("uuIdList size >>>>>>" + uuIdList.size());

		List<ApnsUuModel> apnsUuList = null;

		// Get a user device token from DB.
		Map<String, Object> mapSelectUserList = new HashMap<String, Object>();
		mapSelectUserList.put("uuIdList", uuIdList);
		mapSelectUserList.put("gameDb", gameId);
		try {
			apnsUuList = sampleDao.selectUserList(mapSelectUserList);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return apnsUuList;
	}

	private boolean sendMessage(String[] deviceToken, String message, Map<String, Object> mapOption, int threads, ApnsGameModel apnsGame, String historyId, boolean blnSandBox) {

		try {

			List<PushedNotification> notifications = apnsService.sendMessage(deviceToken, message, mapOption, threads, apnsGame, blnSandBox);

			if(notifications != null) {

				for(PushedNotification push : notifications) {

	                if (push.isSuccessful()) {
	                    /* Apple accepted the notification and should deliver it */  
	                	messageBatch.info("In success deviceToken:" + push.getDevice().getToken());
	                } else {
	                	messageError.error(apnsGame.getGameId() + "_" + historyId + "_" + push.getDevice().getToken());
	                    /* Add code here to remove invalidToken from your database */  
	                	messageBatch.error("In failure deviceToken:" + push.getDevice().getToken());
	                    /* Find out more about what the problem was */  
	                    Exception theProblem = push.getException(); 
	                    logger.error("Reponse error in Exception:", theProblem);
	                    /* If the problem was an error-response packet returned by Apple, get it */  
	                    ResponsePacket theErrorResponse = push.getResponse();
	                    if (theErrorResponse != null) {
	                    	logger.error("Response error from Apple. " + theErrorResponse.getMessage());
	                    }
	                }

	            	logger.info("Token ID >>> " + push.getDevice().getToken());
		        }

			} else {
				for (int i=0; i < deviceToken.length; i++) {
					messageBatch.error("In connection failure deviceToken : " + deviceToken[i]);
				}
			}

		} catch (InterruptedException ie) {
			// A critical error occurred while stopping the batch.
			logger.error("InterruptedException error >>> ", ie);
		} catch (IllegalMonitorStateException ile) {
			// A critical error occurred while stopping the batch.
            logger.error("IllegalMonitorStateException error >>> ", ile);
        } catch (KeystoreException ke) {
            // A critical problem occurred while trying to use your keystore.
            logger.error("KeystoreException error >>> ", ke);
	    } catch (CommunicationException ce) {
	        // A critical communication error occurred while trying to contact Apple servers.  
	    	logger.error("CommunicationException error >>> ", ce);
	    } catch (Exception e) {
	    	logger.error("Exception error >>> ", e);
	    }

        return true;
	}

	@Override
	public boolean sendMessageAsList(String gameId, String message, Map<String, Object> mapOption, int threads, String historyId, boolean blnSandBox) {

		ApnsGameModel apnsGame = null;
		try {
		// Get a gameId from Master in the common cache.
			apnsGame = sampleDao.getApnsGame(gameId);
		} catch (Exception e) {
			logger.error("DB Exceiption Error in the GetApnsGame.", e);
		}

		// if there is none, return to false.
		if(apnsGame == null) {
			logger.error("there has no a GameId. a GameId is {}", gameId);
			return false;
		}

		int intStartRecord = 0;
		List<ApnsUuModel> apnsUuList = null;

		do {

			try {
				apnsUuList = this.selectUserList(intStartRecord, LIMIT_CNT, gameId);
				if(apnsUuList != null && apnsUuList.size() > 0) {
					// Using for parameters
					String[] deviceTokens = new String[apnsUuList.size()];
					for(int n=0; n<apnsUuList.size(); n++) {
						deviceTokens[n] = apnsUuList.get(n).getUuDeviceToken();
					}
					this.sendMessage(deviceTokens, message, mapOption, threads, apnsGame, historyId, blnSandBox);
					Thread.sleep(1000);
					intStartRecord = intStartRecord + LIMIT_CNT;
				} else {
					logger.info("apnsUuList.size >>>> 0 ");
				}
			} catch (Exception e) {
				logger.error("DB Exceiption Error in the SelectUserList.", e);
			}

		} while (apnsUuList != null && apnsUuList.size() > 0);

		return true;
	}

	public boolean updateHistoryList(String lngMax, String statusFlag) throws Exception {

		int intResult = 0;

		Map<String, Object> mapHistory = new HashMap<String, Object>();
		mapHistory.put("historyId", new Long(lngMax));
		mapHistory.put("historyStatusFlag", statusFlag);

		try {
			intResult = sampleDao.updateHistory(mapHistory);
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}

		if(intResult < 1) {
			logger.error("updateHistoryList error, lngMax={}", lngMax);
			return false;
		}

		return true;
	}

}