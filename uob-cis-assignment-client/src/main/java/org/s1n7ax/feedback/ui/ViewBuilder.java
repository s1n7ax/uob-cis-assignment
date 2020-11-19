package org.s1n7ax.feedback.ui;

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

/**
 *
 */
public class ViewBuilder {

	private Logger logger = LogManager.getLogger(ViewBuilder.class);

	private Stage stage;
	private Object controller;
	private Parent view;
	private String viewPath;
	private Scene scene;
	private String title;
	private boolean resizable;

	public static ViewBuilder getInstance() {
		return new ViewBuilder();
	}

	public ViewBuilder withController(Object controller) {
		this.controller = controller;
		return this;
	}

	public ViewBuilder withView(String path) {
		this.viewPath = path;
		return this;
	}

	public ViewBuilder withView(Parent view) {
		this.view = view;
		return this;
	}

	public ViewBuilder withScene(Scene scene) {
		this.scene = scene;
		return this;
	}

	public ViewBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public ViewBuilder withResizable(boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	public ViewBuilder toStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public void show() {
		logger.info("showing the view");

		try {
			Scene scene = getScene();
			Stage stage = getStage();

			stage.setResizable(resizable);
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
