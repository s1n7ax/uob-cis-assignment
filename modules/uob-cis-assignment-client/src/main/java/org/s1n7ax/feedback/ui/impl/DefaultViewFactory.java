package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;

import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.controller.ErrorAlertController;
import org.s1n7ax.feedback.controller.FeedbackController;
import org.s1n7ax.feedback.controller.RatingsController;
import org.s1n7ax.feedback.ui.View;
import org.s1n7ax.feedback.ui.ViewFactory;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * DefaultViewFactory returns reusable Views
 */
public class DefaultViewFactory implements ViewFactory {

	/**
	 * Returns login view
	 */
	@Override
	public View getLoginView() throws IOException {
		return getLoginView(null);
	}

	/**
	 * Returns login view
	 *
	 * @param stage to configure view on
	 */
	@Override
	public View getLoginView(Stage stage) throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.LOGIN_VIEW_PATH).withStage(stage)
				.withTitle("Login").build();
	}

	/**
	 * Returns purchase history view
	 */
	@Override
	public View getPurchaseHistoryView() throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH)
				.withTitle("Purchase History").build();
	}

	/**
	 * Returns feedback view
	 *
	 * @param purchaseHistoryId id of the purchase
	 * @param sellerId          id of the seller
	 * @param seller            name of the seller
	 * @param product           name of the product
	 * @param price             price of the product
	 */
	@Override
	public View getFeedbackView(long purchaseHistoryId, long sellerId, String seller, String product, double price)
			throws IOException {
		FeedbackController ctrl = new FeedbackController(purchaseHistoryId, sellerId, seller, product, price);

		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.FEEDBACK_VIEW_PATH).withController(ctrl)
				.withTitle("Customer Feedback").build();
	}

	/**
	 * Returns login view
	 */
	@Override
	public View getSocialLoginView() throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.SOCIAL_LOGIN_LOADING_VIEW_PATH)
				.withTitle("Google Social Login").build();
	}

	/**
	 * Returns rating view
	 *
	 * @param sellerId id of the seller
	 */
	@Override
	public View getRatingsView(long sellerId) throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.RATINGS_VIEW_PATH).withTitle("Seller Ratings")
				.withController(new RatingsController(sellerId)).build();
	}

	/**
	 * Returns error view
	 *
	 * @param message to set in view
	 */
	@Override
	public View getErrorAlertView(String message) throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.ERROR_ALERT).withTitle("Error")
				.withModality(Modality.APPLICATION_MODAL).withController(new ErrorAlertController(message)).build();
	}

	/**
	 * Returns success view
	 *
	 * @param message to set in view
	 */
	@Override
	public View getSuccessAlertView(String message) throws IOException {
		return new DefaultViewBuilder().withFXMLPath(FXMLConfiguration.SUCCESS_ALERT).withTitle("Success")
				.withModality(Modality.APPLICATION_MODAL).withController(new ErrorAlertController(message)).build();
	}
}
