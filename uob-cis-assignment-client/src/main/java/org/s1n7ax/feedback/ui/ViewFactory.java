package org.s1n7ax.feedback.ui;

import java.io.IOException;

import javafx.stage.Stage;

/**
 * ViewFactory
 */
public interface ViewFactory {

	View getLoginView(Stage stage) throws IOException;

	View getLoginView() throws IOException;

	View getPurchaseHistoryView() throws IOException;

	View getFeedbackView(long purchaseHistoryId, long sellerId, String seller, String product, double price) throws IOException;

	View getSocialLoginView() throws IOException;

	View getRatingsView(long sellerId) throws IOException;

	View getErrorAlertView(String message) throws IOException;

	View getSuccessAlertView(String message) throws IOException;

}
