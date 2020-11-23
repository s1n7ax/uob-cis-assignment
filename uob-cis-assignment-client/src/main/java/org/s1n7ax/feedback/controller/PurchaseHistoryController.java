package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.DefaultErrorHandler;
import org.s1n7ax.feedback.ui.Views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * controller of the purchase history view
 */
public class PurchaseHistoryController {

	private final Logger logger = LogManager.getLogger(PurchaseHistoryController.class);
	private final FeedbackService service = new ApacheHttpFeedbackService();
	private final Views views = new Views();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox ele_Container;

	@FXML
	private Label lbl_Email;

	/**
	 * log out from the system and return back to login view
	 */
	@FXML
	void clicked_btn_Logout(MouseEvent event) {
		logger.info("logout clicked");
		DefaultErrorHandler.runHandledAndClose(event, () -> {
			service.logout();
			views.showLogin();
		});
	}

	/**
	 * display purchase history record components in view
	 */
	@FXML
	void initialize() {
		logger.info("initializing");
		DefaultErrorHandler.runHandled(() -> {
			String email = service.isAuthenticated();
			lbl_Email.setText(email);

			PurchaseHistory[] purchaseHistoryArr = service.getPurchaseHistory();
			List<Parent> componentList = new ArrayList<>();

			for (PurchaseHistory h : purchaseHistoryArr) {

				Long purchaseHistoryId = h.getId();
				Long sellerId = h.getProduct().getSeller().getId();
				String seller = h.getProduct().getSeller().getName();
				String product = h.getProduct().getProductName();
				double price = h.getProduct().getPrice();

				componentList.add(views.getPurchaseHistoryRecord(purchaseHistoryId, sellerId, seller, product, price));
			}
			
			ele_Container.getChildren().addAll(componentList);
		});
	}
}
