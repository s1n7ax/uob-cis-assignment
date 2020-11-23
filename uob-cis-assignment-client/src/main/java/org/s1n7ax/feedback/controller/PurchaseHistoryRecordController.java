package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.DefaultErrorHandler;
import org.s1n7ax.feedback.ui.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * controls purchase history record
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

	public PurchaseHistoryRecordController(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) {
		this.purchaseHistoryId = purchaseHistoryId;
		this.sellerId = sellerId;
		this.seller = seller;
		this.product = product;
		this.price = price;
	}

	/**
	 * go to feedback view of current purchase history
	 */
	@FXML
	void btnFeedbackClicked(MouseEvent event) {
		logger.info("feedback clicked");

		DefaultErrorHandler.runHandledAndClose(event, () -> {
			views.showFeedback(purchaseHistoryId, sellerId, seller, product, price);
		});
	}

	/**
	 * displays seller ratings
	 */
	@FXML
	void lblSellerClicked(MouseEvent event) {
		logger.info("seller rating clicked");

		DefaultErrorHandler.runHandled(() -> {
			views.showRatings(sellerId);
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
