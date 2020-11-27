package org.s1n7ax.feedback.browser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultBrowserOpener {
	private final Logger logger = LogManager.getLogger(DefaultBrowserOpener.class);
	
	public void openDefaultBrowser(String url) throws IOException, URISyntaxException {
		logger.debug("opening default browser");
		Desktop.getDesktop().browse(new URI(url));
	}
}
