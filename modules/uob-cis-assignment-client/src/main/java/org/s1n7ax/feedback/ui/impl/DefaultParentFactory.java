package org.s1n7ax.feedback.ui.impl;

import java.io.IOException;

import org.s1n7ax.feedback.configuration.FXMLConfiguration;
import org.s1n7ax.feedback.controller.FeedbackRecordController;
import org.s1n7ax.feedback.controller.PurchaseHistoryRecordController;
import org.s1n7ax.feedback.event.RateChanged;
import org.s1n7ax.feedback.ui.ParentFactory;

import javafx.scene.Parent;

/**
 * DefaultParentFactory Returns reusable ui components
 */
public class DefaultParentFactory implements ParentFactory {

	/**
	 * Returns purchase history record
	 *
	 * @param purchaseHistoryId id of the purchase
	 * @param sellerId id of the seller
	 * @param seller name of the seller
	 * @param product name of the product
	 * @param price price of the product
	 * */
	@Override
	public Parent getPurchaseHistoryRecordParent(Long purchaseHistoryId, Long sellerId, String seller, String product,
			double price) throws IOException {
		PurchaseHistoryRecordController ctrl = new PurchaseHistoryRecordController(purchaseHistoryId, sellerId, seller,
				product, price);
		return new DefaultFXMLParentFactory().getParent(FXMLConfiguration.PURCHASE_HISTORY_RECORD_VIEW_PATH, ctrl);
	}

	/**
	 * Returns feedback record ui component
	 *
	 * @param id id of the feedback
	 * @param rate rate given from the user for the question
	 * @param question feedback question
	 * @param onRateChange rate changed event callback
	 * */
	@Override
	public Parent getFeedbackRecordParent(Long id, int rate, String question, RateChanged onRateChange)
			throws IOException {
		FeedbackRecordController ctrl = new FeedbackRecordController(id, rate, question, onRateChange);
		return new DefaultFXMLParentFactory().getParent(FXMLConfiguration.FEEDBACK_RECORD_VIEW_PATH, ctrl);
	}

}
