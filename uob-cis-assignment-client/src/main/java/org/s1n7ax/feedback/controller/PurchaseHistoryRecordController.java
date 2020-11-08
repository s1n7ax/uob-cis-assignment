package org.s1n7ax.feedback.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * PurchaseHistoryRecordController
 */
public class PurchaseHistoryRecordController {

	private Logger logger = LogManager.getLogger(PurchaseHistoryRecordController.class);

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
	private Label lbl_Seller;

	@FXML
	private Label lbl_Product;

	@FXML
	private Label lbl_Price;

	public PurchaseHistoryRecordController(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) {
		this.purchaseHistoryId = purchaseHistoryId;
		this.sellerId = sellerId;
		this.seller = seller;
		this.product = product;
		this.price = price;
	}

	@FXML
	void clicked_btn_Feedback(MouseEvent event) {

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FeedbackController ctrl = new FeedbackController(purchaseHistoryId, sellerId, seller, product, price);

		try {

			FXViewController.getInstance().withView(FXMLConfiguration.FEEDBACK_VIEW_PATH).withController(ctrl)
					.toStage(stage).show();

		} catch (IOException e) {

			logger.error("invalid fxml path", e);
			AlertPopup.errorAlert("Application configuration error. Please contact system administrator");
			return;

		}
	}

	@FXML
	void clicked_lbl_Seller(MouseEvent event) {
		try {

			FXViewController.getInstance().toStage(new Stage()).withView(FXMLConfiguration.RATINGS_VIEW_PATH)
					.withController(new RatingsController(sellerId)).show();

		} catch (IOException e) {

			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());

		}
	}

	@FXML
	void initialize() {

		if (purchaseHistoryId == 0 || seller == null || product == null)
			throw new RuntimeException("id, seller, product has to be set");

		lbl_Seller.setText(seller);
		lbl_Product.setText(product);
		lbl_Price.setText(String.valueOf(price));

	}
}
