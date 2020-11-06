package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.AlertPopup;
import org.s1n7ax.feedback.common.Resource;
import org.s1n7ax.feedback.configuration.FXMLConfiguration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXViewController {

	private Logger logger = LogManager.getLogger(FXViewController.class);

	public static FXViewController getInstance() {
		return new FXViewController();
	}
	
	public void showFXML(Stage stage, String fxmlPath) {

		logger.info("changing stage scene to::" + fxmlPath);

		URL url = Resource.getResource(fxmlPath);
		Parent view;

		try {

			view = FXMLLoader.load(url);

		} catch (IOException ex) {

			logger.error(ex);
			AlertPopup.errorAlert("Unable to change the view. Please contact sys admin");
			return;

		}

		Scene scene = new Scene(view);
		scene.getStylesheets().add(Resource.getResource(FXMLConfiguration.ROOT_STYLE_PATH).toExternalForm());
		
		stage.setTitle("Feedback");
		stage.setScene(scene);
		stage.show();
	}
}
