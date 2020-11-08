package org.s1n7ax.feedback.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.common.BrowserImpl;
import org.s1n7ax.feedback.common.CSSClassSwitcher;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.configuration.FeedbackServiceConfig;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	private Logger logger = LogManager.getLogger(LoginController.class);

	private CSSClassSwitcher textValiditySwitcher = CSSClassSwitcher.getInstance().addClass("valid", "text-field-valid")
			.addClass("invalid", "text-field-invalid");

	@FXML
	private TextField txt_Email;

	@FXML
	private PasswordField txt_Password;

	@FXML
	private Button btn_Login;

	@FXML
	private ImageView btn_GoogleSignin;

	@FXML
	void clicked_btn_GoogleSignin(MouseEvent event) {
		logger.info("clicked google signin");

		FeedbackService service = new ApacheHttpFeedbackService();

		String sessionId;

		try {

			sessionId = service.getSession();

		} catch (Exception ex) {

			AlertPopup.errorAlert(ex.getMessage());
			return;

		}

		logger.info("open social login for session::" + sessionId);

		try {

			URI uri = new URIBuilder() //
					.setScheme(FeedbackServiceConfig.PROTOCOL) //
					.setHost(FeedbackServiceConfig.HOST) //
					.setPort(FeedbackServiceConfig.PORT) //
					.setPath(FeedbackServiceConfig.GET_SOCIAL_LOGIN_EP) //
					.setParameter(FeedbackServiceConfig.SESSION_PARAM_NAME, sessionId) //
					.build();

			boolean browserOpened = openBrowser(uri.toString());

			if (browserOpened) {
				logger.info("should try to log in to system");
			}

		} catch (URISyntaxException e) {

			logger.error("invalid uri", e);
			AlertPopup.errorAlert("Application configuration error. Please contact system administrator");

		}
	}

	@FXML
	void clicked_btn_Login(MouseEvent event) {

		logger.info("clicked basic login button");

		String email = txt_Email.getText();
		String password = txt_Password.getText();
		boolean hasLoggedIn = basicLogin(email, password);

		if (!hasLoggedIn)
			return;

		// user has successfully logged in to the style
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			FXViewController.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH).toStage(stage).show();
		} catch (Exception e) {
			logger.error(e.getMessage());
			AlertPopup.errorAlert(e.getMessage());

		}

	}

	@FXML
	void keypressed_Email(KeyEvent event) {
		if (EmailValidator.getInstance().isValid(txt_Email.getText())) {

			logger.debug("valid email address found::" + txt_Email.getText());
			textValiditySwitcher.setControl(txt_Email).changeClass("valid");

		} else {

			textValiditySwitcher.setControl(txt_Email).changeClass("invalid");

		}
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

	private boolean basicLogin(String email, String password) {

		FeedbackService service = new ApacheHttpFeedbackService();

		try {

			service.login(email, password);
			return true;

		} catch (Exception ex) {
			AlertPopup.errorAlert(ex.getMessage());
		}

		return false;
	}
}
