package org.s1n7ax.feedback.browser;

/**
 * Returns a browser instance that is working for the current system
 */
public interface BrowserFactory {

	/**
	 * Returns new Browser instace
	 *
	 * @return browser instance
	 */
	Browser get();
}
