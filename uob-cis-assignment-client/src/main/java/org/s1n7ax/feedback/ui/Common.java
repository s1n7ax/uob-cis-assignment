package org.s1n7ax.feedback.ui;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Common {
	public static Stage getStage(Event event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
}
