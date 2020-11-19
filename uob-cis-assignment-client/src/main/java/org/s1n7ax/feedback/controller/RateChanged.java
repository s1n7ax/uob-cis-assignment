package org.s1n7ax.feedback.controller;

/**
 * Rate changed callback
 */
public interface RateChanged {

	/**
	 * this will be called when the rate has changed
	 */
	public void onRateChanged(final Long questionId, final int rate);
}
