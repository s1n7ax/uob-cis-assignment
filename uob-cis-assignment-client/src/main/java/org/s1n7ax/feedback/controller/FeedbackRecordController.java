package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.s1n7ax.feedback.common.Resource;
import org.s1n7ax.feedback.component.StarButton;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.event.RateChanged;

import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controller of feedback record
 * 
 * Feedback record represents a single question and it's ratings
 */
public class FeedbackRecordController {
	private final Long id;
	private int rate;
	private final String question;
	private final RateChanged onRateChange;

	private Image selectedStar = new Image(Resource.getResource(FXMLConfiguration.SELECTED_STAR_IMAGE_PATH).toString());
	private Image deselectedStar = new Image(
			Resource.getResource(FXMLConfiguration.DESELECTED_STAR_IMAGE_PATH).toString());

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lblQuestion;

	@FXML
	private ImageView btnStar1;

	@FXML
	private ImageView btnStar2;

	@FXML
	private ImageView btnStar3;

	@FXML
	private ImageView btnStar4;

	@FXML
	private ImageView btnStar5;

	/**
	 * Initialize the controller
	 *
	 * @param id           id of the feedback. this will be used to set changes in
	 *                     server
	 * @param question     question question to display in the view
	 * @param onRateChange rate change event callback
	 */
	public FeedbackRecordController(Long id, int rate, String question, RateChanged onRateChange) {
		this.id = id;
		this.rate = rate;
		this.question = question;
		this.onRateChange = onRateChange;
	}

	/**
	 * Handles click events
	 *
	 * Updates the local model and UI After the local changes, onRateChange callback
	 * will be called to notify parent
	 */
	@FXML
	void eleStarContainerClicked(MouseEvent event) {
		EventTarget target = event.getTarget();

		if (!(target instanceof StarButton))
			return;

		StarButton btn = (StarButton) target;

		// if user select the same star, star will be unchecked
		if (rate == btn.getValue())
			rate = 0;
		else
			rate = btn.getValue();

		updateRateInView();

		// call the feedback view controller callback
		onRateChange.onRateChanged(id, rate);
	}

	@FXML
	void initialize() {
		lblQuestion.setText(question);
		updateRateInView();
	}

	/**
	 * Updates the view for the model
	 *
	 * Updates star icons according rate
	 */
	private void updateRateInView() {
		int count = 0;
		ImageView[] imageViews = { btnStar1, btnStar2, btnStar3, btnStar4, btnStar5 };

		// update selected star images
		while (rate != 0 && count < rate) {
			imageViews[count].setImage(selectedStar);
			count++;
		}

		// update not selected star images
		while (count < 5) {
			imageViews[count].setImage(deselectedStar);
			count++;
		}
	}
}
