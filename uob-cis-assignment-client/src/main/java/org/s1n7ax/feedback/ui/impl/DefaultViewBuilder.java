package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.View;
import org.s1n7ax.feedback.ui.ViewBuilder;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * DefaultViewBuilder build Views
 */
public class DefaultViewBuilder implements ViewBuilder {

	private final Logger logger = LogManager.getLogger(DefaultViewBuilder.class);

	private final View view;

	private Stage stage;
	private Object controller;
	private Parent parent;
	private String fxmlPath;
	private Scene scene;
	private String title;
	private double width;
	private double height;
	private boolean isModalitySet;
	private Modality modality;
	private boolean isResizableSet;
	private boolean resizable;

	public DefaultViewBuilder() {
		this.view = new DefaultView();
	}

	@Override
	public ViewBuilder withStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	@Override
	public ViewBuilder withController(Object controller) {
		this.controller = controller;
		return this;
	}

	@Override
	public ViewBuilder withParent(Parent parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public ViewBuilder withFXMLPath(String fxmlPath) {
		this.fxmlPath = fxmlPath;
		return this;
	}

	@Override
	public ViewBuilder withScene(Scene scene) {
		this.scene = scene;
		return this;
	}

	@Override
	public ViewBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public ViewBuilder withWidth(double width) {
		this.width = width;
		return this;
	}

	@Override
	public ViewBuilder withHeight(double height) {
		this.height = height;
		return this;
	}

	@Override
	public ViewBuilder withModality(Modality modality) {
		this.isModalitySet = true;
		this.modality = modality;
		return this;
	}

	@Override
	public ViewBuilder withResizable(boolean resizable) {
		this.isResizableSet = true;
		this.resizable = resizable;
		return this;
	}

	/**
	 * Returns the built View
	 */
	@Override
	public View build() throws IOException {
		Parent parent = getParent();
		Scene scene = getScene(parent);
		Stage stage = getStage();

		stage.setScene(scene);

		view.setStage(stage);
		return view;
	}

	/**
	 * Returns the parent
	 *
	 * IF Parent type view is set, view will be used as it is IF controller is set,
	 * controller will be set for view
	 *
	 * @return loaded fxml parent
	 * @throws IOException when the file not found when loading fxml
	 */
	private Parent getParent() throws IOException {
		if (parent != null) {
			logger.debug("view is set. using existing view");
			return parent;
		}

		return new DefaultFXMLParentFactory().getParent(fxmlPath, controller);
	}

	/**
	 * Returns the stage
	 *
	 * IF stage is not set, new stage will be created IF title is set, title will be
	 * set to stage IF width and height is set, those properties will be set to
	 * stage
	 *
	 * @return stage
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

		if (isModalitySet)
			stage.initModality(modality);

		if (isResizableSet)
			stage.setResizable(resizable);

		return stage;
	}

	/**
	 * Returns the scene
	 *
	 * IF scene is not set, new scene will be created with passed view
	 *
	 * @param parent parent to be initialize when creating scene
	 */
	private Scene getScene(Parent parent) {
		if (scene == null) {
			logger.debug("scene is not set. creating new scene");
			scene = new Scene(parent);
		}

		return scene;
	}
}
