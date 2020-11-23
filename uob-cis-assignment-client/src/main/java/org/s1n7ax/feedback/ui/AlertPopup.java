package org.s1n7ax.feedback.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Display UI alert messages
 */
public class AlertPopup {
	
	private static final Logger logger = LogManager.getLogger(AlertPopup.class);
	private static final Views views = new Views();

	/**
	 * display error alert
	 *
	 * @param message message to display in the screen
	 */
	public static void errorAlert(String message) {
		logger.info("error alert message::" + message);
		views.showErrorAlert(message);
	}

	/**
	 * display success alert
	 *
	 * @param message message to display in the screen
	 */
	public static void successAlert(String message) {
		logger.info("success alert message::" + message);
		views.showSuccessAlert(message);
	}

}
