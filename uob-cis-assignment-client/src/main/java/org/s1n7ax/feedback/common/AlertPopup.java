package org.s1n7ax.feedback.common;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Display UI alert messages
 */
public class AlertPopup {

	/**
	 * display error alert
	 *
	 * @param message message to display in the screen
	 */
	public static void errorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}

	/**
	 * display success alert
	 *
	 * @param message message to display in the screen
	 */
	public static void successAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.showAndWait();
	}

}
