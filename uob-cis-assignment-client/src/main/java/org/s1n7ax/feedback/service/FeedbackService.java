package org.s1n7ax.feedback.service;

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
	 * Logs in to the system
	 * 
	 * @param email    email of the user
	 * @param password password of the user
	 */
	public void login(String email, String password) throws Exception;
}
