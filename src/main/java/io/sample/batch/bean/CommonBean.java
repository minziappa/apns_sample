package io.sample.batch.bean;

import io.sample.batch.bean.model.ApnsGameModel;

import java.io.Serializable;
import java.util.List;

public class CommonBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ApnsGameModel> apnsGameList;

	public List<ApnsGameModel> getApnsGameList() {
		return apnsGameList;
	}

	public void setApnsGameList(List<ApnsGameModel> apnsGameList) {
		this.apnsGameList = apnsGameList;
	}

}