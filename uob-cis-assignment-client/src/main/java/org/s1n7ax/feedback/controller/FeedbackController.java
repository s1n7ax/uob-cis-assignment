package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.event.RateChanged;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.Parents;
import org.s1n7ax.feedback.ui.Views;
import org.s1n7ax.feedback.ui.commons.DefaultErrorHandler;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Controls the feedback view
 */
public class FeedbackController {

	private final Logger logger = LogManager.getLogger(FeedbackController.class);
	private final FeedbackService service = new ApacheHttpFeedbackService();
	private final Views views = new Views();
	private final Parents parents = new Parents();

	private final Long purchaseHistoryId;
	private final Long sellerId;
	private final String seller;
	private final String product;
	private final double price;

	private Feedback[] feedbacks;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lblSeller;

	@FXML
	private Label lblProduct;

	@FXML
	private Label lblPrice;

	@FXML
	private VBox eleFeedbackContainer;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnSubmit;

	/**
	 * Initialize the controller
	 *
	 * @param purchaseHistoryId id of the purchase. this id will be used to retrieve
	 *                          and set data in server
	 * @param sellerId          id if the seller. this will be used to retrieve and
	 *                          set data in the server
	 * @param seller            seller name to display in the view
	 * @param product           product name to display in the view
	 * @param price             price to display in the view
	 */
	public FeedbackController(Long purchaseHistoryId, Long sellerId, String seller, String product, double price) {
		this.purchaseHistoryId = purchaseHistoryId;
		this.sellerId = sellerId;
		this.seller = seller;
		this.product = product;
		this.price = price;
	}

	/**
	 * Opens seller's rating window on click
	 */
	@FXML
	void lblSellerClicked(MouseEvent event) {
		logger.info("seller name clicked");
		DefaultErrorHandler.runHandled(() -> {
			views.getFactory().getRatingsView(sellerId).show();
		});
	}

	/**
	 * User will be sent back to purchase history window on click
	 */
	@FXML
	void btnBackClicked(MouseEvent event) {
		logger.info("back button clicked");
		DefaultErrorHandler.runHandledAndClose(event, () -> {
			views.getFactory().getPurchaseHistoryView().show();
		});
	}

	/**
	 * Submits rating changes to server on click
	 */
	@FXML
	void btnSubmitClicked(MouseEvent event) {
		logger.info("submit button clicked");
		DefaultErrorHandler.runHandled(() -> {
			service.updateFeedback(purchaseHistoryId, feedbacks);
			views.getFactory().getSuccessAlertView("Ratings submitted!").show();
		});
	}

	@FXML
	void initialize() {
		logger.info("initializing");
		lblSeller.setText(seller);
		lblProduct.setText(product);
		lblPrice.setText(String.valueOf(price));

		// updates rate model on rate change in UI rate changed event callback
		RateChanged onRateChange = new RateChanged() {
			@Override
			public void onRateChanged(Long questionId, int rate) {

				logger.debug("rate changed event fired");

				for (int i = 0; i < feedbacks.length; i++) {
					if (feedbacks[i].getId() == questionId) {
						feedbacks[i].setRate(rate);
						logger.info("rate changed for question id::" + questionId + " rate::" + rate);
						break;
					}
				}
			}
		};

		DefaultErrorHandler.runHandled(() -> {
			feedbacks = service.getFeedback(purchaseHistoryId);
			List<Parent> questions = new ArrayList<>();
			for (Feedback feedback : feedbacks) {
				Parent parent = parents.getFactory().getFeedbackRecordParent(feedback.getId(), feedback.getRate(),
						feedback.getQuestion().getQuestion(), onRateChange);
				questions.add(parent);
			}

			eleFeedbackContainer.getChildren().addAll(questions);
		});
	}
}
