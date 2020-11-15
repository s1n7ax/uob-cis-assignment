package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.Resource;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXViewController {

	private Logger logger = LogManager.getLogger(FXViewController.class);

	private Stage stage;
	private Object controller;
	private Parent view;
	private String viewPath;
	private Scene scene;
	private String title;

	public static FXViewController getInstance() {
		return new FXViewController();
	}

	public FXViewController withController(Object controller) {
		this.controller = controller;
		return this;
	}

	public FXViewController withView(String path) {
		this.viewPath = path;
		return this;
	}

	public FXViewController withView(Parent view) {
		this.view = view;
		return this;
	}

	public FXViewController withScene(Scene scene) {
		this.scene = scene;
		return this;
	}

	public FXViewController withTitle(String title) {
		this.title = title;
		return this;
	}

	public FXViewController toStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public void show() {
		logger.info("showing the view");

		try {
			Scene scene = getScene();
			Stage stage = getStage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Parent getView() throws IOException {

		if (view != null)
			return view;

		if (viewPath == null)
			throw new IOException("view nor view path has been set");

		URL url = Resource.getResource(viewPath);
		FXMLLoader loader = new FXMLLoader(url);

		if (controller != null)
			loader.setController(controller);

		return loader.load();

	}

	private Stage getStage() throws NoSuchElementException {
		if (stage == null)
			throw new NoSuchElementException("stage is not set");

		if (title != null)
			stage.setTitle(title);

		return stage;
	}

	private Scene getScene() throws IOException {
		Parent p = getView();

		if (scene != null)
			return scene;

		scene = new Scene(p);

		return scene;
	}
}
