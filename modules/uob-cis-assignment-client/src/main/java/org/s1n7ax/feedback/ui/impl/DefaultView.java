package org.s1n7ax.feedback.ui.impl;

import org.s1n7ax.feedback.ui.View;

import javafx.stage.Stage;

/**
 * DefaultView defines operations for view
 */
public class DefaultView implements View {
	private Stage stage;

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Shows the view
	 */
	@Override
	public void show() {
		stage.show();
	}
}
