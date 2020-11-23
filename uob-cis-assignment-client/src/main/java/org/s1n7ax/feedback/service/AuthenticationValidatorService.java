package org.s1n7ax.feedback.service;

import org.s1n7ax.feedback.service.impl.ApacheHttpFeedbackService;

import javafx.concurrent.Task;

public class AuthenticationValidatorService extends Task<Boolean> {

	private final long timeout;
	private final long sleep;

	public AuthenticationValidatorService(long timeout, long sleep) {
		this.timeout = timeout;
		this.sleep = sleep;
	}

	@Override
	protected Boolean call() throws Exception {

		final FeedbackService service = new ApacheHttpFeedbackService();
		final long start = System.currentTimeMillis();

		String email = null;
		while ((email = service.isAuthenticated()) == null && (System.currentTimeMillis() - start) < timeout) {
			Thread.sleep(sleep);
		}

		if (email == null) {
			return false;
		}

		return true;
	}
}
