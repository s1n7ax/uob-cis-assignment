package org.s1n7ax.feedback.ui;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * ViewBuilder
 */
public interface ViewBuilder {

	ViewBuilder withStage(Stage stage);

	ViewBuilder withController(Object controller);

	ViewBuilder withParent(Parent parent);

	ViewBuilder withFXMLPath(String path);

	ViewBuilder withScene(Scene scene);

	ViewBuilder withTitle(String title);

	ViewBuilder withWidth(double width);

	ViewBuilder withHeight(double height);

	ViewBuilder withModality(Modality modality);

	ViewBuilder withResizable(boolean resizable);

	View build() throws IOException;

}
