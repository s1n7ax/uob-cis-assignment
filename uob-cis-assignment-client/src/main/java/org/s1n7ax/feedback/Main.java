package org.s1n7ax.feedback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main
 */
public class Main extends Application {
	final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		logger.info("starting application");

		setUserAgentStylesheet(STYLESHEET_CASPIAN);

		FXViewController.getInstance().toStage(primaryStage).withView(FXMLConfiguration.LOGIN_VIEW_PATH).show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();

		// closing the http client
		// DefaultApacheHttpClientService.getClient().close();
	}

}
