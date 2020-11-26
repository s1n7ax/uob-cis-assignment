package org.s1n7ax.feedback.controller;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.Views;
import org.s1n7ax.feedback.ui.commons.CSSClassSwitcher;
import org.s1n7ax.feedback.ui.commons.Common;
import org.s1n7ax.feedback.ui.commons.DefaultErrorHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller of the login page
 */
public class LoginController {
	private Logger logger = LogManager.getLogger(LoginController.class);
	private Views views = new Views();

	private CSSClassSwitcher textValiditySwitcher = CSSClassSwitcher.getInstance().addClass("valid", "text-field-valid")
			.addClass("invalid", "text-field-invalid");

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private ImageView btnGoogleSignin;

	/**
	 * Opens social login loading screen
	 */
	@FXML
	void btnGoogleSigninClicked(MouseEvent event) {
		logger.info("clicked google signin");
		DefaultErrorHandler.runHandledAndClose(event, () -> {
			views.getFactory().getSocialLoginView().show();
		});
	}

	/**
	 * Sends login request to service
	 *
	 * IF login credentials are correct, purchase history view will be opened IF
	 * login credentials are incorrect, user will be alerted
	 */
	@FXML
	void btnLoginClicked(MouseEvent event) {
		logger.info("clicked basic login button");

		FeedbackService service = new ApacheHttpFeedbackService();
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		Stage stage = Common.getStage(event);
		DefaultErrorHandler.runHandledAndClose(stage, () -> {
			service.login(email, password);
			views.getFactory().getPurchaseHistoryView().show();
			;
		});
	}

	/**
	 * Changes the border color to on email entered
	 *
	 * IF correct email syntax, border color will be green IF incorrect email
	 * syntax, border color will be red
	 */
	@FXML
	void emailKeypressed(KeyEvent event) {
		if (EmailValidator.getInstance().isValid(txtEmail.getText())) {
			logger.debug("valid email address found::" + txtEmail.getText());
			textValiditySwitcher.setControl(txtEmail).changeClass("valid");
		} else {
			textValiditySwitcher.setControl(txtEmail).changeClass("invalid");
		}
	}
}
