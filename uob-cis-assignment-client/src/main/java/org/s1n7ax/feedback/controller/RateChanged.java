package org.s1n7ax.feedback.controller;

/**
 * RateChangedCallback
 */
public interface RateChanged {

	public void onRateChanged(final Long questionId, final int rate);
}
