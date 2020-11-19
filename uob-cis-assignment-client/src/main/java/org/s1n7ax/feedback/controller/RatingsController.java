package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.entity.Rating;
import org.s1n7ax.feedback.service.FeedbackService;
import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * displays ratings of a seller in a bar chart
 */
public class RatingsController {

	private Logger logger = LogManager.getLogger(RatingsController.class);
	private FeedbackService service = new ApacheHttpFeedbackService();

	private Long sellerId;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BarChart<?, ?> barChart;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	public RatingsController(Long sellerId) {
		this.sellerId = sellerId;
	}

	@FXML
	void initialize() {

		try {
			Rating[] ratings = service.getRating(sellerId);
			XYChart.Series series = new XYChart.Series<>();

			for (Rating r : ratings) {
				String quality = r.getQuality().replaceAll("\\s", "\n");

				series.getData().add(new XYChart.Data<>(quality, r.getRating()));
			}

			barChart.getData().addAll(series);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			AlertPopup.errorAlert(e.getMessage());

		}
	}
}
