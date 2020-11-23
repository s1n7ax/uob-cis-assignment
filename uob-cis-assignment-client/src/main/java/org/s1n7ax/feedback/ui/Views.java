package org.s1n7ax.feedback.ui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.Resource;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.controller.ErrorAlertController;
import org.s1n7ax.feedback.controller.FeedbackController;
import org.s1n7ax.feedback.controller.FeedbackRecordController;
import org.s1n7ax.feedback.controller.PurchaseHistoryRecordController;
import org.s1n7ax.feedback.controller.RatingsController;
import org.s1n7ax.feedback.event.RateChanged;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Views displays certain UIs in application
 */
public class Views {
	private final Logger logger = LogManager.getLogger(Views.class);
	private final String configError = "Application configuration error. Please contact system admin";

	public void showLogin() throws Exception {
		showLogin(null);
	}

	public void showLogin(Stage stage) throws Exception {
		try {
			logger.debug("showing login screen");
			ViewBuilder.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).withTitle("Login").withStage(stage)
					.show();
		} catch (IOException e) {
			handleDefaultError(e);
		}
	}

	public void showSocialLogin() throws Exception {
		try {
			logger.debug("showing social login screen");
			ViewBuilder.getInstance().withView(FXMLConfiguration.SOCIAL_LOGIN_LOADING_VIEW_PATH)
					.withTitle("Google Social Login").show();
		} catch (IOException e) {
			handleDefaultError(e);
		}
	}

	public void showPurchaseHistory() throws Exception {
		try {
			logger.debug("showing purchase history screen");
			ViewBuilder.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH)
					.withTitle("Purchase History").show();
		} catch (IOException e) {
			handleDefaultError(e);
		}
	}

	public void showFeedback(Long purchaseHistoryId, Long sellerId, String seller, String product, double price)
			throws Exception {
		try {
			logger.debug(String.format("showing feedback screen for seller::%s product::%s", seller, product));
			FeedbackController ctrl = new FeedbackController(purchaseHistoryId, sellerId, seller, product, price);
			ViewBuilder.getInstance().withView(FXMLConfiguration.FEEDBACK_VIEW_PATH).withController(ctrl)
					.withTitle("Customer Feedback").show();
		} catch (Exception e) {
			handleDefaultError(e);
		}
	}

	public void showRatings(Long sellerId) throws Exception {
		try {
			logger.debug("showing ratings screen for sellerId::" + sellerId);
			ViewBuilder.getInstance().withView(FXMLConfiguration.RATINGS_VIEW_PATH).withTitle("Seller Ratings")
					.withController(new RatingsController(sellerId)).show();
		} catch (Exception e) {
			handleDefaultError(e);
		}
	}

	public void showErrorAlert(String message) {
		try {
			ViewBuilder.getInstance().withView(FXMLConfiguration.ERROR_ALERT).withTitle("Error")
					.withModality(Modality.APPLICATION_MODAL).withController(new ErrorAlertController(message)).show();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void showSuccessAlert(String message) {
		try {
			ViewBuilder.getInstance().withView(FXMLConfiguration.SUCCESS_ALERT).withTitle("Success")
					.withModality(Modality.APPLICATION_MODAL).withController(new ErrorAlertController(message)).show();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public Parent getPurchaseHistoryRecord(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) throws Exception {
		logger.debug(String.format("returning purchase history record for seller::%s product::%s", seller, product));
		try {
			PurchaseHistoryRecordController ctrl = new PurchaseHistoryRecordController(purchaseHistoryId, sellerId,
					seller, product, price);
			return ViewBuilder.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_RECORD_VIEW_PATH)
					.withController(ctrl).getView();
		} catch (IOException e) {
			handleDefaultError(e);
			return null;
		}
	}

	public Parent getFeedbackRecord(Long id, int rate, String question, RateChanged onRateChange) throws Exception {
		try {
			FeedbackRecordController ctrl = new FeedbackRecordController(id, rate, question, onRateChange);
			return ViewBuilder.getInstance().withView(FXMLConfiguration.FEEDBACK_RECORD_VIEW_PATH).withController(ctrl)
					.getView();
		} catch (IOException e) {
			handleDefaultError(e);
			return null;
		}

	}

	public Image getSelectedStar() {
		return new Image(Resource.getResource(FXMLConfiguration.SELECTED_STAR_IMAGE_PATH).toString());
	}

	public Image getDeselectedStar() {
		return new Image(Resource.getResource(FXMLConfiguration.DESELECTED_STAR_IMAGE_PATH).toString());
	}

	private void handleDefaultError(Exception e) throws Exception {
		AlertPopup.errorAlert(configError);
		logger.error(e.getMessage(), e);
	}
}
