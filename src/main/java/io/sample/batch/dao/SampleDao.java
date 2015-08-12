package io.sample.batch.dao;

import io.sample.batch.bean.model.ApnsGameModel;
import io.sample.batch.bean.model.ApnsUuModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SampleDao {

	public List<String> selectBtUserList(Map<String, Object> map) throws SQLException;
	public List<ApnsUuModel> selectUserList(Map<String, Object> map) throws SQLException;
	public ApnsGameModel getApnsGame(String gameId) throws SQLException;
	public int updateHistory(Map<String, Object> map) throws SQLException;
	public List<ApnsGameModel> selectGameList() throws SQLException;

}