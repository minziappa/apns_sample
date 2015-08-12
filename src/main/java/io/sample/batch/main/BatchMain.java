package io.sample.batch.main;

import io.sample.batch.service.SampleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchMain {

	@Autowired
    private static SampleService sampleService;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
		SampleService sampleService = (SampleService) context.getBean("sampleServiceImpl");

		sampleService.executeTest("test");
	}

}