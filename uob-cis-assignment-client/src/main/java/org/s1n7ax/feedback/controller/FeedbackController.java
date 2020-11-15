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
import org.s1n7ax.feedback.ui.impl.FXViewController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FeedbackController {

	private final Logger logger = LogManager.getLogger(FeedbackController.class);
	private final FeedbackService service = new ApacheHttpFeedbackService();

	private final Long purchaseHistoryId;
	private final Long sellerId;
	private final String seller;
	private final String product;
	private final double price;

	private RateChanged onRateChange;
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

	@FXML
	void clicked_lbl_Seller(MouseEvent event) {
		FXViewController.getInstance().toStage(new Stage()).withView(FXMLConfiguration.RATINGS_VIEW_PATH)
				.withController(new RatingsController(sellerId)).show();
	}

	@FXML
	void clicked_btn_Back(MouseEvent event) {

		logger.info("back button clicked");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		PurchaseHistoryController ctrl = new PurchaseHistoryController();
		FXViewController.getInstance().withView(FXMLConfiguration.PURCHASE_HISTORY_VIEW_PATH).withController(ctrl)
				.toStage(stage).show();
	}

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

		onRateChange = new RateChanged() {
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

			for (Feedback feedback : feedbacks) {

				FeedbackRecordController ctrl = new FeedbackRecordController(feedback.getId(), feedback.getRate(),
						feedback.getQuestion().getQuestion(), onRateChange);

				Parent view = FXViewController.getInstance().withView(FXMLConfiguration.FEEDBACK_RECORD_VIEW_PATH)
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
