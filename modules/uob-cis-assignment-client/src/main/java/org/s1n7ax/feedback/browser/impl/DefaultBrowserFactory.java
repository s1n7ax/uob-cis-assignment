package org.s1n7ax.feedback.browser.impl;

import org.s1n7ax.feedback.browser.BrowserFactory;
import org.s1n7ax.feedback.browser.Browser;

/**
 * Default browser factory that support linux and windows operating systems
 */
public class DefaultBrowserFactory implements BrowserFactory {

	/**
	 * returns a browser
	 */
	public Browser get() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("linux"))
			return new LinuxBrowser();

		if (os.contains("windows"))
			return new WindowsBrowser();

		throw new RuntimeException("Application doesn't support browsers in platform::" + os);
	}
}
