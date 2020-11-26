package org.s1n7ax.feedback.ui.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.RunErrorHandledFunction;

import javafx.event.Event;
import javafx.stage.Stage;

public class DefaultErrorHandler {
	private static final Logger logger = LogManager.getLogger(DefaultErrorHandler.class);

	public static void runHandled(RunErrorHandledFunction func) {
		try {
			func.runHandled();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());
		}
	}

	public static void runHandledAndClose(Stage stage, RunErrorHandledFunction func) {
		try {
			func.runHandled();
			stage.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());
		}
	}

	public static void runHandledAndClose(Event event, RunErrorHandledFunction func) {
		try {
			func.runHandled();
			Common.getStage(event).close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());
		}
	}
}