package org.s1n7ax.feedback.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.browser.BrowserFactory;
import org.s1n7ax.feedback.browser.impl.DefaultBrowserFactory;
import org.s1n7ax.feedback.configuration.FeedbackServiceConfig;
import org.s1n7ax.feedback.service.AuthenticationValidatorService;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.Views;
import org.s1n7ax.feedback.ui.commons.DefaultErrorHandler;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controls loading screen
 */
public class SocialLoginLoadingScreenController {
	private final Logger logger = LogManager.getLogger(SocialLoginLoadingScreenController.class);
	private final FeedbackService service = new ApacheHttpFeedbackService();
	private final Views views = new Views();

	private Thread authTask;

	@FXML
	private Button btnCancel;

	/**
	 * Cancels the social login on click Shows login view
	 */
	@FXML
	void btnCancelClicked(MouseEvent event) {
		logger.info("Social login canceled");

		// social login thread is the background process that validates the user is
		// logged in or not
		// authorization code is sent directly from browser to service
		// so following update client if the user is authenticated or not
		if (authTask != null && authTask.isAlive())
			authTask.interrupt();

		DefaultErrorHandler.runHandledAndClose(event, () -> {
			service.endSession();
			views.getFactory().getLoginView().show();
		});
	}

	@FXML
	void initialize() {
		DefaultErrorHandler.runHandled(() -> {
			String sessionId = service.getSession();
			URI uri = getGoogleSocialLoginURI(sessionId);
			openBrowser(uri.toString());
		});

		Task<Boolean> task = new AuthenticationValidatorService(FeedbackServiceConfig.SOCIAL_LOGIN_TIMEOUT,
				FeedbackServiceConfig.SOCIAL_LOGIN_SLEEP);
		task.setOnSucceeded((event) -> {
			boolean isAuthenticated = (Boolean) event.getSource().getValue();
			Stage stage = (Stage) btnCancel.getScene().getWindow();

			if (isAuthenticated) {
				DefaultErrorHandler.runHandledAndClose(stage, () -> {
					views.getFactory().getPurchaseHistoryView().show();
				});
				return;
			}

			DefaultErrorHandler.runHandledAndClose(stage, () -> {
				views.getFactory().getLoginView().show();
				views.getFactory().getErrorAlertView("Google Signin timeout").show();
			});
		});

		task.setOnFailed((event) -> {
			Stage stage = (Stage) btnCancel.getScene().getWindow();
			Exception ex = (Exception) event.getSource().getException();

			if (ex instanceof InterruptedException)
				return;

			DefaultErrorHandler.runHandledAndClose(stage, () -> {
				views.getFactory().getLoginView().show();
				views.getFactory().getErrorAlertView("Unknown error. Please contact system admin").show();
			});
		});

		// start user authentication validation service
		authTask = new Thread(task);
		authTask.start();
	}

	/**
	 * Opens new browser
	 */
	private void openBrowser(String uri) throws Exception {
		BrowserFactory factory = new DefaultBrowserFactory();
		factory.get().launch(uri);
	}

	/**
	 * Construct social login URL
	 *
	 * @param sessionId current session id
	 */
	private URI getGoogleSocialLoginURI(String sessionId) throws Exception {
		try {
			return new URIBuilder() //
					.setScheme(FeedbackServiceConfig.PROTOCOL) //
					.setHost(FeedbackServiceConfig.HOST) //
					.setPort(FeedbackServiceConfig.PORT) //
					.setPath(FeedbackServiceConfig.GET_SOCIAL_LOGIN_EP) //
					.setParameter(FeedbackServiceConfig.SESSION_PARAM_NAME, sessionId) //
					.build();
		} catch (URISyntaxException e) {
			throw new Exception("Application configuration error. Please contact system admin");
		}
	}
}
