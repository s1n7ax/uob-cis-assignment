package org.s1n7ax.feedback.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.common.BrowserImpl;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.configuration.FeedbackServiceConfig;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoadingScreenController {
	private Logger logger = LogManager.getLogger(LoadingScreenController.class);

	private final Stage stage;
	private Thread socialLoginThread;

	@FXML
	private Button btn_Cancel;

	public LoadingScreenController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	void clicked_btn_Cancel(MouseEvent event) {
		if (socialLoginThread.isAlive())
			socialLoginThread.interrupt();

		FXViewController.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).toStage(stage).show();

	}

	@FXML
	void initialize() {

		FeedbackService service = new ApacheHttpFeedbackService();
		String sessionId = null;
		try {
			sessionId = service.getSession();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());
		}

		URI uri = null;
		try {
			uri = new URIBuilder() //
					.setScheme(FeedbackServiceConfig.PROTOCOL) //
					.setHost(FeedbackServiceConfig.HOST) //
					.setPort(FeedbackServiceConfig.PORT) //
					.setPath(FeedbackServiceConfig.GET_SOCIAL_LOGIN_EP) //
					.setParameter(FeedbackServiceConfig.SESSION_PARAM_NAME, sessionId) //
					.build();
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert("Failed to open browser. Please contact system administrator");
		}

		boolean browserOpened = openBrowser(uri.toString());

		if (!browserOpened) {
			logger.error("faied to open the browser");
			AlertPopup.errorAlert("Failed to open browser. Please contact system administrator");
			return;
		}

		socialLoginThread = new Thread(new Runnable() {

			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long timeout = 30000;
				long sleep = 1000;

				try {
					while (service.isAuthenticated() == null && (System.currentTimeMillis() - start) < timeout) {
						Thread.sleep(sleep);
					}

					String email = service.isAuthenticated();

					if (email != null) {
						Platform.runLater(() -> {
							PurchaseHistoryController ctrl = new PurchaseHistoryController();
							FXViewController.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH)
									.toStage(stage).withController(ctrl).show();
						});

						return;
					}

					Platform.runLater(() -> {
						AlertPopup.errorAlert("Social login failed");
					});

					Platform.runLater(() -> {
						FXViewController.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).toStage(stage)
								.show();

					});

				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					Platform.runLater(() -> {
						AlertPopup.errorAlert(e.getMessage());
					});
				}

			}
		});

		socialLoginThread.start();
	}

	private boolean openBrowser(String uri) {

		try {

			BrowserImpl.getInstance().launchBrowser(uri);
			return true;

		} catch (IOException ex) {

			logger.error("failed to open the browser", ex);
			AlertPopup.errorAlert("Failed to open the browser");

		} catch (URISyntaxException ex) {

			logger.error("invalid uri", ex);
			AlertPopup.errorAlert("Application configuration error. Please contact system administrator");

		}

		return false;
	}

}
