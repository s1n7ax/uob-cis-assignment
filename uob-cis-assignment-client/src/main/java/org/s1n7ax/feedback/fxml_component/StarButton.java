package org.s1n7ax.feedback.fxml_component;

import javafx.beans.NamedArg;
import javafx.scene.image.ImageView;

/**
 * StarButton component that represent single star
 */
public class StarButton extends ImageView {

	private final int value;

	public StarButton(@NamedArg("value") String value) {
		super();
		this.value = Integer.parseInt(value);
	}

	public int getValue() {
		return value;
	}
}
