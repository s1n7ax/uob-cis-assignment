package org.s1n7ax.feedback.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PurchaseHistoryController {

	private Logger logger = LogManager.getLogger(PurchaseHistoryController.class);
	private FeedbackService service = new ApacheHttpFeedbackService();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox ele_Container;

	@FXML
	void clicked_btn_Feedback(MouseEvent event) {

	}

	@FXML
	void initialize() {

		try {

			PurchaseHistory[] purchaseHistoryArr = service.getPurchaseHistory();
			List<Parent> componentList = new ArrayList<>();

			for (PurchaseHistory h : purchaseHistoryArr) {

				Long purchaseHistoryId = h.getId();
				Long sellerId = h.getProduct().getSeller().getId();
				String seller = h.getProduct().getSeller().getName();
				String product = h.getProduct().getProductName();
				double price = h.getProduct().getPrice();

				componentList.add(getPurchaseHistoryRecord(purchaseHistoryId, sellerId, seller, product, price));

			}

			ele_Container.getChildren().addAll(componentList);

		} catch (Exception e) {

			logger.error("error", e);
			AlertPopup.errorAlert(e.getMessage());

		}

	}

	private Parent getPurchaseHistoryRecord(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) throws IOException {

		PurchaseHistoryRecordController ctrl = new PurchaseHistoryRecordController(purchaseHistoryId, sellerId, seller,
				product, price);

		Parent view = FXViewController.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_RECORD_VIEW_PATH)
				.withController(ctrl).getView();

		return view;
	}
}
