package org.s1n7ax.feedback.configuration;

/**
 * 
 * FeedbackServiceConfig defines constants related to the feedback service
 */
public final class FeedbackServiceConfig {

	// service
	public final static String PROTOCOL = "http";
	public final static String HOST = "localhost";
	public final static int PORT = 8080;
	public final static String SESSION_KEY = "JSESSIONID";
	public final static String SESSION_PARAM_NAME = "sessionId";

	// endpoints
	public final static String GET_SESSION_EP = "/";
	public final static String POST_BASIC_LOGIN_EP = "/login";
	public final static String GET_LOGOUT_EP = "/logout";
	public final static String GET_SOCIAL_LOGIN_EP = "/login/social";
	public final static String GET_PURCHASE_HISTORY_EP = "/purchase/history";
	public final static String GET_FEEDBACKS_EP = "/purchase/feedback";
	public final static String POST_FEEDBACKS_EP = "/purchase/feedback";
	public final static String GET_RATING_EP = "/rating";
	public final static String GET_IS_AUTHENTICATED = "/restricted";
	
	// authentication
	public final static long SOCIAL_LOGIN_TIMEOUT = 60000;
	public final static long SOCIAL_LOGIN_SLEEP = 500;
}
