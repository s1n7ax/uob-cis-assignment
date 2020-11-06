package org.s1n7ax.feedback.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Config provides an interface for reading applicaition.properties file
 */
public class Config {
	private static Logger logger = LogManager.getLogger(Config.class);

	private static String PATH = "application.properties";
	private static Properties prop;

	static {
		try {

			logger.info("Reading configuration at::" + PATH);

			InputStream fileIS = ClassLoader.getSystemResourceAsStream(PATH);
			prop = new Properties();
			prop.load(fileIS);

		} catch (FileNotFoundException ex) {

			logger.error("Configuration file not found at::" + PATH, ex);

		} catch (IOException ex) {

			logger.error("Failed to load the file at::" + PATH, ex);

		}
	}

	public static String getProp(String key) {
		return prop.getProperty(key);
	}

	public static String getProp(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}

}
