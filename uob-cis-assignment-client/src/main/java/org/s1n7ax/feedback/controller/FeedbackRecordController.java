package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.s1n7ax.feedback.common.Resource;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.fxml_component.StarButton;

import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
	private Label lbl_Question;

	@FXML
	private ImageView btn_Star1;

	@FXML
	private ImageView btn_Star2;

	@FXML
	private ImageView btn_Star3;

	@FXML
	private ImageView btn_Star4;

	@FXML
	private ImageView btn_Star5;

	public FeedbackRecordController(Long id, int rate, String question, RateChanged onRateChange) {
		this.id = id;
		this.rate = rate;
		this.question = question;
		this.onRateChange = onRateChange;
	}

	@FXML
	void clicked_StarContainer(MouseEvent event) {
		EventTarget target = event.getTarget();

		if (!(target instanceof StarButton))
			return;

		StarButton btn = (StarButton) target;

		rate = btn.getValue();
		updateRateInView();

		onRateChange.onRateChanged(id, rate);
	}

	@FXML
	void initialize() {
		lbl_Question.setText(question);
		updateRateInView();
	}

	private void updateRateInView() {
		int count = 0;
		ImageView[] imageViews = { btn_Star1, btn_Star2, btn_Star3, btn_Star4, btn_Star5 };

		while (rate != 0 && count < rate) {
			imageViews[count].setImage(selectedStar);
			count++;
		}

		while (count < 5) {
			imageViews[count].setImage(deselectedStar);
			count++;
		}
	}

}
