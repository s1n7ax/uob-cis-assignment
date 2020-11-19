package org.s1n7ax.feedback.browser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.browser.Browser;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Handle browser commands in Windows platform
 */
public class WindowsBrowser implements Browser {
	private Logger logger = LogManager.getLogger(WindowsBrowser.class);

	/**
	 * launch default browser
	 * @param url url to navigate in the browser window
	 */
	public void launch(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (URISyntaxException e) {
			logger.error(e);
			throw new RuntimeException(
					"Trying to launch the browser with invalid URL. Please contact system administrator");
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}
