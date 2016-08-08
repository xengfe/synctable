package com.yeecare.test;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4Test {

	public Log4Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		Logger logger = Logger.getLogger(Log4Test.class);
		logger.debug(" debug ");
		logger.error(" error ");
	}

}
