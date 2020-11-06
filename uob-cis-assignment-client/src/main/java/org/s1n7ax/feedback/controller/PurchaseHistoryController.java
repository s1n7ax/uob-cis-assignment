package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PurchaseHistoryController {

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
		String[] labels = { "Seller", "Product", "Price" };
		String[] values = { "John", "iPhone 12", "$899" };

		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color: black");
		hbox.setPadding(new Insets(10, 10, 10, 10));
		AnchorPane.setRightAnchor(hbox, 0.0);

		// vbox 1
		VBox vbox1 = new VBox();
		vbox1.setPadding(new Insets(0, 0, 0, 20));
		for (String value : labels) {
			Label label = new Label();
			label.setText(value);
			vbox1.getChildren().add(label);
		}

		// vbox 2
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(0, 100, 0, 20));
		HBox.setHgrow(vbox2, Priority.ALWAYS);
		for (String value : values) {
			Label label = new Label();
			label.setText(value);
			vbox2.getChildren().add(label);
		}

		// vbox 3
		VBox vbox3 = new VBox();
		vbox3.setPadding(new Insets(0, 20, 0, 20));
		vbox3.alignmentProperty();
		vbox3.setAlignment(Pos.CENTER);

		Button feedback = new Button();
		feedback.setText("Feedback");
		vbox3.getChildren().add(feedback);

		hbox.getChildren().addAll(vbox1, vbox2, vbox3);

		ele_Container.getChildren().add(hbox);
	}
}
