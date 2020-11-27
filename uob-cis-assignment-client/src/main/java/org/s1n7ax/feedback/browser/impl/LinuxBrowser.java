package org.s1n7ax.feedback.browser.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.browser.Browser;
import org.s1n7ax.feedback.browser.DefaultBrowserOpener;

/**
 * Handle browser commands in Linux platform
 */
public class LinuxBrowser extends DefaultBrowserOpener implements Browser {
	private Logger logger = LogManager.getLogger(LinuxBrowser.class);

	/**
	 * Opens the browser window and navigates to the given URL
	 *
	 * @param url url to navigate in the browser window
	 */
	public void launch(String url) {

		try {
			URI uri = new URI(url);
			launchBrowser(uri);
		} catch (URISyntaxException e) {
			logger.error(e);
			throw new RuntimeException(
					"Trying to launch the browser with invalid URL. Please contact system administrator");
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Tries to launch the browser using java.awt Launch will fall back to manually
	 * opening the browser available in the system
	 *
	 * @param url url to navigate in the browser window
	 * @throws throws exception if LinuxBrowser can not launch the browser
	 */
	private void launchBrowser(URI uri) throws Exception {
		// tries to open the default browser using java.awt
		try {
			openDefaultBrowser(uri.toString());
		} catch (UnsupportedOperationException e) {
			logger.warn(e.getMessage(), e);
		}

		// manually trying to execute known set of browsers
		try {
			launchCompatibilityBrowser(uri);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * trying to find known set of browsers in PATH and run browser
	 * 
	 * @param url url to navigate in the browser window
	 * @throws throws exception if LinuxBrowser can not launch the browser
	 */
	private void launchCompatibilityBrowser(URI uri) throws Exception {
		String[] browsers = { "chrome", "chromium", "firefox" };

		for (String browser : browsers) {
			try {
				Runtime.getRuntime().exec(String.format("%s %s", browser, uri.toString()));
				return;
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}

		throw new Exception("Failed to open the browser. Any of:" + browsers + " not found in the system.");
	}
}
