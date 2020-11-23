package org.s1n7ax.feedback.controller;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.CSSClassSwitcher;
import org.s1n7ax.feedback.ui.Common;
import org.s1n7ax.feedback.ui.DefaultErrorHandler;
import org.s1n7ax.feedback.ui.Views;

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
	 * on social login click event, control will be passed to the loading screen
	 * view
	 */
	@FXML
	void btnGoogleSigninClicked(MouseEvent event) {
		logger.info("clicked google signin");
		DefaultErrorHandler.runHandledAndClose(event, () -> {
			new Views().showSocialLogin();
		});
	}

	/**
	 * on login click event handler IF user login was a success, user will be
	 * navigated to purchase history view
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
			new Views().showPurchaseHistory();
		});
	}

	/**
	 * changes the border color to indicate the validity of email entered
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
