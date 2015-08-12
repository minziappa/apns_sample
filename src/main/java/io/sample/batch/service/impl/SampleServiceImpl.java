package io.sample.batch.service.impl;

import io.sample.batch.service.SampleService;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {

	final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Autowired
    private Configuration configuration;

	@Override
	public String executeTest(String test) throws Exception {

		logger.info(" >>> " + test);

		return null;
	}

}