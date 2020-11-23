package org.s1n7ax.feedback.ui;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.common.Resource;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * ViewBuilder build and show fxml views
 */
public class ViewBuilder {

	private Logger logger = LogManager.getLogger(ViewBuilder.class);

	private Stage stage;
	private Object controller;
	private Parent view;
	private String viewPath;
	private Scene scene;
	private String title;
	private boolean resizable = true;
	private double width;
	private double height;
	private boolean isInitModalitySet;
	private Modality modality;

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

	public ViewBuilder withWidth(double width) {
		this.width = width;
		return this;
	}

	public ViewBuilder withHeight(double height) {
		this.height = height;
		return this;
	}

	public ViewBuilder withResizable(boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	public ViewBuilder withStage(Stage stage) {
		this.stage = stage;
		return this;
	}
	
	public ViewBuilder withModality(Modality modality) {
		isInitModalitySet = true;
		this.modality = modality;
		return this;
	}

	public void show() throws IOException {
		Parent view = getView();
		Scene scene = getScene(view);
		Stage stage = getStage();

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Returns the view
	 *
	 * IF Parent type view is set, view will be used as it is IF controller is set,
	 * controller will be set for view
	 *
	 * @return loaded view
	 * @throws IOException if loader failed to load the file
	 */
	public Parent getView() throws IOException {

		if (view != null) {
			logger.debug("view is set. using existing view");
			return view;
		}

		if (viewPath == null)
			throw new RuntimeException("view or viewPath should be set get view");

		URL url = Resource.getResource(viewPath);
		FXMLLoader loader = new FXMLLoader(url);

		if (controller != null) {
			logger.debug("controller is set. setting controller to loader");
			loader.setController(controller);
		}

		logger.debug("loading the view");
		view = loader.load();
		return view;
	}

	/**
	 * Returns the stage
	 *
	 * IF stage is not set, new stage will be created IF titlel is set, title will
	 * be set to stage IF width and height is set, those properties will be set to
	 * stage
	 */
	private Stage getStage() {
		if (stage == null) {
			logger.debug("stage is not set. creating new stage");
			stage = new Stage();
		}

		if (title != null)
			stage.setTitle(title);

		if (width != 0)
			stage.setWidth(width);

		if (height != 0)
			stage.setHeight(height);
		
		if(isInitModalitySet)
			stage.initModality(modality);

		stage.setResizable(resizable);

		return stage;
	}

	/**
	 * Returns the scene
	 *
	 * IF scene is not set, new scene will be created with passed view
	 *
	 * @param view view that should be set to existing or new scene
	 */
	private Scene getScene(Parent view) {
		if (scene == null) {
			logger.debug("scene is not set. creating new scene");
			scene = new Scene(view);
		}

		return scene;
	}
}
