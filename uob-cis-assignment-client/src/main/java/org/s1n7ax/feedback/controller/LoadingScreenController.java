package org.s1n7ax.feedback.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.browser.Browser;
import org.s1n7ax.feedback.browser.impl.DefaultBrowserFactory;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.configuration.FeedbackServiceConfig;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.FXViewController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controls loading screen
 */
public class LoadingScreenController {
	private Logger logger = LogManager.getLogger(LoadingScreenController.class);

	private final Stage stage;
	private Thread socialLoginThread;

	@FXML
	private Button btn_Cancel;

	public LoadingScreenController(Stage stage) {
		this.stage = stage;
	}

	/**
	 * cancels the social login
	 */
	@FXML
	void clicked_btn_Cancel(MouseEvent event) {
		logger.info("Social login canceled");

		// social login thread is the background process that validates the user is
		// logged in or not
		// authorization code is sent directly from browser to service
		// so following update client if the user is authenticated or not
		if (socialLoginThread != null && socialLoginThread.isAlive())
			socialLoginThread.interrupt();

		showLoginView();
	}

	@FXML
	void initialize() {
		FeedbackService service = new ApacheHttpFeedbackService();
		String sessionId = null;
		URI uri = null;

		// get session
		try {
			sessionId = service.getSession();
		} catch (Exception e) {
			handleUIError(e);
			return;
		}

		// build url
		try {
			uri = new URIBuilder() //
					.setScheme(FeedbackServiceConfig.PROTOCOL) //
					.setHost(FeedbackServiceConfig.HOST) //
					.setPort(FeedbackServiceConfig.PORT) //
					.setPath(FeedbackServiceConfig.GET_SOCIAL_LOGIN_EP) //
					.setParameter(FeedbackServiceConfig.SESSION_PARAM_NAME, sessionId) //
					.build();
		} catch (URISyntaxException e) {
			handleUIError(e, "Failed to open browser application issue. Please contact system administrator");
		}

		try {
			openBrowser(uri.toString());
		} catch (Exception e) {
			handleUIError(e);
		}

		// start user authentication validation service
		socialLoginThread = new Thread(new AuthenticationValidator(stage));
		socialLoginThread.start();
	}

	/**
	 * opens new browser
	 */
	private void openBrowser(String uri) throws Exception {
		Browser browser = new DefaultBrowserFactory().get();
		browser.launch(uri);
	}

	/**
	 * log error message, opens alert popup and returns user back to login view
	 */
	private void handleUIError(Exception e) {
		handleUIError(e, e.getMessage());
	}

	/**
	 * log error message, opens alert popup and returns user back to login view
	 */
	private void handleUIError(Exception e, String errorMsg) {
		logger.error(e.getMessage(), e);
		Platform.runLater(() -> {
			AlertPopup.errorAlert(errorMsg);
			showLoginView();
		});
	}

	/**
	 * shows the login view
	 */
	private void showLoginView() {
		FXViewController.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).toStage(stage).show();
	}
}

/**
 * Authentication validator service
 */
class AuthenticationValidator implements Runnable {
	private Logger logger = LogManager.getLogger(AuthenticationValidator.class);
	private Stage stage;

	public AuthenticationValidator(Stage stage) {
		this.stage = stage;
	}

	/**
	 * validate the user is logged in or not
	 *
	 * IF the user is not logged in, user will be returned back to login page IF
	 * user is logged in before the given period of time, user will be navigated to
	 * purchase history view
	 */
	@Override
	public void run() {
		FeedbackService service = new ApacheHttpFeedbackService();
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
					FXViewController.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH).toStage(stage)
							.withController(ctrl).show();
				});

				return;
			}

			Platform.runLater(() -> {
				AlertPopup.errorAlert("Social login timedout");
				FXViewController.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).toStage(stage).show();
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
}
