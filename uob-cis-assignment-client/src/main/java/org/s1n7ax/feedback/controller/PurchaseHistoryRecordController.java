package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.Views;
import org.s1n7ax.feedback.ui.commons.DefaultErrorHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Controls purchase history record
 */
public class PurchaseHistoryRecordController {

	private final Logger logger = LogManager.getLogger(PurchaseHistoryRecordController.class);
	private final Views views = new Views();

	private final Long purchaseHistoryId;
	private final Long sellerId;
	private final String seller;
	private final String product;
	private final double price;

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

	/**
	 * Initialize the controller
	 *
	 * @param purchaseHistoryId id of the purchase. this will be used to open
	 *                          feedback view
	 * @param sellerId          is of the seller. this will be used to open feedback
	 *                          view
	 * @param seller            name of the seller. this will be used to show data
	 *                          the view
	 * @param product           name of the product. this will be used to open
	 *                          feedback view
	 */
	public PurchaseHistoryRecordController(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) {
		this.purchaseHistoryId = purchaseHistoryId;
		this.sellerId = sellerId;
		this.seller = seller;
		this.product = product;
		this.price = price;
	}

	/**
	 * Opens feedback view of current purchase history on click
	 */
	@FXML
	void btnFeedbackClicked(MouseEvent event) {
		logger.info("feedback clicked");

		DefaultErrorHandler.runHandledAndClose(event, () -> {
			views.getFactory().getFeedbackView(purchaseHistoryId, sellerId, seller, product, price).show();
		});
	}

	/**
	 * Shows seller's ratings on click
	 */
	@FXML
	void lblSellerClicked(MouseEvent event) {
		logger.info("seller rating clicked");

		DefaultErrorHandler.runHandled(() -> {
			views.getFactory().getRatingsView(sellerId).show();
		});
	}

	@FXML
	void initialize() {
		logger.info("initializing");

		lblSeller.setText(seller);
		lblProduct.setText(product);
		lblPrice.setText(String.valueOf(price));
	}
}
