package org.s1n7ax.feedback.service;

import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.entity.Rating;

/**
 * FeedbackService defines feedback service business logics
 */
public interface FeedbackService {

	/**
	 * Gets the servers session id for the current client session
	 * 
	 * @return
	 */
	public String getSession() throws Exception;

	/**
	 * End the current client session
	 * @throws Exception
	 */
	public void endSession() throws Exception;
	
	/**
	 * Logs in to the system
	 * 
	 * @param email    email of the user
	 * @param password password of the user
	 */
	public void login(String email, String password) throws Exception;

	/**
	 * Logs out from the system
	 */
	public void logout() throws Exception;

	/**
	 * Checks if the session is authenticated
	 *
	 * @return true if session is successfully authenticated
	 */
	public String isAuthenticated() throws Exception;

	/**
	 * Get customer's purchase histry
	 * 
	 * @throws Exception
	 */
	public PurchaseHistory[] getPurchaseHistory() throws Exception;

	/**
	 * Get a list of feedbacks got for the purchase
	 * 
	 * @param purchaseHistoryId id of the purchase history record
	 * @return list of feedbacks
	 * @throws Exception
	 */
	public Feedback[] getFeedback(Long purchaseHistoryId) throws Exception;

	/**
	 * update feedback in the database
	 * 
	 * @param feedbacks list of feedbacks to update
	 */
	public void updateFeedback(Long purchaseHistoryId, Feedback[] feedbacks) throws Exception;

	/**
	 * Retrive ratings of a seller
	 * 
	 * @param sellerId seller id to get the ratings
	 * @return
	 */
	public Rating[] getRating(Long sellerId) throws Exception;
}
