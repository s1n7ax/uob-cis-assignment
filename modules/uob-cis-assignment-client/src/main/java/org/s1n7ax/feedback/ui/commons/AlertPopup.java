package org.s1n7ax.feedback.ui.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.Views;

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
		try {
			views.getFactory().getErrorAlertView(message).show();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * display success alert
	 *
	 * @param message message to display in the screen
	 */
	public static void successAlert(String message) {
		logger.info("success alert message::" + message);
		try {
			views.getFactory().getSuccessAlertView(message).show();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
