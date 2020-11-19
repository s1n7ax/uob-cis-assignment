package org.s1n7ax.feedback.controller;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.common.CSSClassSwitcher;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.ViewBuilder;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
	private TextField txt_Email;

	@FXML
	private PasswordField txt_Password;

	@FXML
	private Button btn_Login;

	@FXML
	private ImageView btn_GoogleSignin;

	/**
	 * on social login click event, controll will be passed to the loading screen
	 * view
	 */
	@FXML
	void clicked_btn_GoogleSignin(MouseEvent event) {
		logger.info("clicked google signin");

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		LoadingScreenController ctrl = new LoadingScreenController(stage);
		ViewBuilder.getInstance().withView(FXMLConfiguration.SOCIAL_LOGIN_LOADING_VIEW_PATH).toStage(stage)
				.withTitle("Feedback: Login").withController(ctrl).show();

	}

	/**
	 * on login click event handler IF user login was a success, user will be
	 * navigated to purchase history view
	 */
	@FXML
	void clicked_btn_Login(MouseEvent event) {
		logger.info("clicked basic login button");

		FeedbackService service = new ApacheHttpFeedbackService();
		String email = txt_Email.getText();
		String password = txt_Password.getText();

		try {
			service.login(email, password);
		} catch (Exception ex) {
			AlertPopup.errorAlert(ex.getMessage());
			return;
		}

		// user has successfully logged in to the system
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			PurchaseHistoryController ctrl = new PurchaseHistoryController();
			ViewBuilder.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH).toStage(stage)
					.withTitle("Feedback: Purchase History").withController(ctrl).show();
		} catch (Exception e) {
			logger.error(e.getMessage());
			AlertPopup.errorAlert(e.getMessage());
		}
	}

	/**
	 * changes the border color to indicate the validity of email entered
	 */
	@FXML
	void keypressed_Email(KeyEvent event) {
		if (EmailValidator.getInstance().isValid(txt_Email.getText())) {
			logger.debug("valid email address found::" + txt_Email.getText());
			textValiditySwitcher.setControl(txt_Email).changeClass("valid");
		} else {
			textValiditySwitcher.setControl(txt_Email).changeClass("invalid");
		}
	}
}
