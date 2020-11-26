package org.s1n7ax.feedback.ui;

import java.io.IOException;

import javafx.scene.Parent;

/**
 * FXMLParentFactory
 */
public interface FXMLParentFactory {

	Parent getParent(String fxmlPath) throws IOException;

	Parent getParent(String fxmlPath, Object controller) throws IOException;
}
