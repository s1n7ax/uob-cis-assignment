package org.s1n7ax.feedback.ui;

import java.io.IOException;

import org.s1n7ax.feedback.event.RateChanged;

import javafx.scene.Parent;

/**
 * ParentFactory
 */
public interface ParentFactory {

	Parent getPurchaseHistoryRecordParent(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) throws IOException;

	Parent getFeedbackRecordParent(Long id, int rate, String question, RateChanged onRateChange) throws IOException;
}
