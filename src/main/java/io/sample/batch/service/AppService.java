package io.sample.batch.service;

import java.util.Map;

/**
 * @author Woong joon Kim
 * @lastModified $Date: 2010/04/21 01:01:20 $ By $Author: kim_woongjoon $
 * @version $Revision: 1.2 $
 * Copyright: CYBER AGENT. INC
 */
public interface AppService {

//	public boolean sendMessageAll(String gameId, String message) throws Exception;
	public boolean sendMessageAsList(String gameId, String message, Map<String, Object> mapOption, int threads, String historyId, boolean blnSandBox) throws Exception;
	public boolean updateHistoryList(String lngMax, String statusFlag) throws Exception;

}