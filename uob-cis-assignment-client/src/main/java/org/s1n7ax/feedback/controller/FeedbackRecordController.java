package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.s1n7ax.feedback.component.StarButton;
import org.s1n7ax.feedback.event.RateChanged;
import org.s1n7ax.feedback.ui.Views;

import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controller of feedback record 
 */
public class FeedbackRecordController {
	private final Views views = new Views();
	
	private final Long id;
	private int rate;
	private final String question;
	private final RateChanged onRateChange;
	

	private Image selectedStar = views.getSelectedStar();
	private Image deselectedStar = views.getDeselectedStar();

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

	public FeedbackRecordController(Long id, int rate, String question, RateChanged onRateChange) {
		this.id = id;
		this.rate = rate;
		this.question = question;
		this.onRateChange = onRateChange;
	}

	/**
	 * star click event handler
	 */
	@FXML
	void eleStarContainerClicked(MouseEvent event) {
		EventTarget target = event.getTarget();

		if (!(target instanceof StarButton))
			return;

		StarButton btn = (StarButton) target;

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
	 * update the view for the model
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
