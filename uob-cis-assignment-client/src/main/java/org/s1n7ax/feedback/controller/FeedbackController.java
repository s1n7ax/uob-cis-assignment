package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;
import org.s1n7ax.feedback.ui.ViewBuilder;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * controls the feedback view
 */
public class FeedbackController {

	private final Logger logger = LogManager.getLogger(FeedbackController.class);
	private final FeedbackService service = new ApacheHttpFeedbackService();

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
	private Label lbl_Seller;

	@FXML
	private Label lbl_Product;

	@FXML
	private Label lbl_Price;

	@FXML
	private VBox ele_FeedbackContainer;

	@FXML
	private Button btn_Back;

	@FXML
	private Button btn_Submit;

	public FeedbackController(Long purchaseHistoryId, Long sellerId, String seller, String product, double price) {
		this.purchaseHistoryId = purchaseHistoryId;
		this.sellerId = sellerId;
		this.seller = seller;
		this.product = product;
		this.price = price;
	}

	/**
	 * on click on seller name, seller's rating window will be opened
	 */
	@FXML
	void clicked_lbl_Seller(MouseEvent event) {
		logger.info("seller name clicked");

		ViewBuilder.getInstance().toStage(new Stage()).withView(FXMLConfiguration.RATINGS_VIEW_PATH)
				.withTitle("Feedback: Seller Ratings").withController(new RatingsController(sellerId)).show();
	}

	/**
	 * click on back button, user will be returned back to purchase history window
	 */
	@FXML
	void clicked_btn_Back(MouseEvent event) {
		logger.info("back button clicked");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		PurchaseHistoryController ctrl = new PurchaseHistoryController();
		ViewBuilder.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH).withController(ctrl)
				.withTitle("Feedback: Purchase History").toStage(stage).show();
	}

	/**
	 * click on submit, the changes made to ratings of the seller will be submitted
	 * to server
	 */
	@FXML
	void clicked_btn_Submit(MouseEvent event) {
		logger.info("submit button clicked");

		try {
			service.updateFeedback(purchaseHistoryId, feedbacks);
			AlertPopup.successAlert("Feedback ratings updated");
		} catch (Exception e) {
			logger.error(e.getMessage());
			AlertPopup.errorAlert(e.getMessage());
		}
	}

	@FXML
	void initialize() {

		lbl_Seller.setText(seller);
		lbl_Product.setText(product);
		lbl_Price.setText(String.valueOf(price));

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

		try {
			feedbacks = service.getFeedback(purchaseHistoryId);
			List<Parent> questions = new ArrayList<>();

			// create new feedback record component for each and every feedback passed
			for (Feedback feedback : feedbacks) {
				FeedbackRecordController ctrl = new FeedbackRecordController(feedback.getId(), feedback.getRate(),
						feedback.getQuestion().getQuestion(), onRateChange);

				Parent view = ViewBuilder.getInstance().withView(FXMLConfiguration.FEEDBACK_RECORD_VIEW_PATH)
						.withController(ctrl).getView();

				questions.add(view);
			}

			ele_FeedbackContainer.getChildren().addAll(questions);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());
		}
	}
}
