package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;
import java.net.URL;

import org.s1n7ax.feedback.common.Resource;
import org.s1n7ax.feedback.ui.FXMLParentFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * DefaultFXMLParentFactory returns reused FXML components
 */
public class DefaultFXMLParentFactory implements FXMLParentFactory {

	/**
	 * Returns parent from file path to fxml file
	 *
	 * @param fxmlPath path to fxml file from org.s1n7ax.feedback package
	 */
	@Override
	public Parent getParent(String fxmlPath) throws IOException {
		return getParent(fxmlPath, null);
	}

	/**
	 * Returns parent from file path to fxml file
	 *
	 * @param fxmlPath   path to fxml file from org.s1n7ax.feedback package
	 * @param controller controller to set when loading the component
	 */
	@Override
	public Parent getParent(String fxmlPath, Object controller) throws IOException {
		URL url = Resource.getResource(fxmlPath);
		FXMLLoader loader = new FXMLLoader(url);

		if (controller != null) {
			loader.setController(controller);
		}

		return loader.load();
	}
}
