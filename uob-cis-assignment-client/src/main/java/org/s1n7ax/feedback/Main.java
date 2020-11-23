package org.s1n7ax.feedback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.DefaultErrorHandler;
import org.s1n7ax.feedback.ui.Views;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main
 */
public class Main extends Application {
	private final Logger logger = LogManager.getLogger(Main.class);
	private final Views views = new Views();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		logger.info("starting application");

		setUserAgentStylesheet(STYLESHEET_CASPIAN);
		DefaultErrorHandler.runHandled(() -> {
			views.showLogin(primaryStage);
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

}