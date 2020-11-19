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
import org.s1n7ax.feedback.ui.ViewBuilder;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * controller of the purchase history view
 */
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
	private Label lbl_Email;

	/**
	 * log out from the system and return back to login view
	 */
	@FXML
	void clicked_btn_Logout(MouseEvent event) {
		try {

			service.logout();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			ViewBuilder.getInstance().withView(FXMLConfiguration.LOGIN_VIEW_PATH).withTitle("Feedback: Login")
					.toStage(stage).show();

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());

		}

	}

	/**
	 * display purchase history record components in view
	 */
	@FXML
	void initialize() {

		String email = getEmail();
		if (email == null)
			email = "unknown_user";
		lbl_Email.setText(email);

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

			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());

		}

	}

	/**
	 * gets the email of currently logged in user
	 */
	private String getEmail() {
		try {
			return service.isAuthenticated();
		} catch (Exception e) {
			AlertPopup.errorAlert(e.getMessage());
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * returns new purchase history record
	 */
	private Parent getPurchaseHistoryRecord(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) throws IOException {

		PurchaseHistoryRecordController ctrl = new PurchaseHistoryRecordController(purchaseHistoryId, sellerId, seller,
				product, price);

		Parent view = ViewBuilder.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_RECORD_VIEW_PATH)
				.withController(ctrl).getView();

		return view;
	}
}
