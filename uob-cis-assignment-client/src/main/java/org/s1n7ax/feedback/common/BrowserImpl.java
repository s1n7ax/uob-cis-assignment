package org.s1n7ax.feedback.common;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BrowserImpl {
	Logger logger = LogManager.getLogger(BrowserImpl.class);

	public static BrowserImpl getInstance() {
		return new BrowserImpl();
	}

	public void launchBrowser(String url) throws URISyntaxException, IOException {
		logger.info(String.format("launching browser on URL %s", url));

		try {
			launchDefaultBrowser(url);
			return;
		} catch (IOException ex) {
			logger.warn("failed to launch default browser");
		} catch (UnsupportedOperationException ex) {
			logger.warn("failed to launch default browser");
		}

		try {
			launchCompatibilityBrowser(url);
		} catch (IOException ex) {
			logger.warn("failed to launch compatibility browser");
			throw ex;
		}
	}

	private void launchDefaultBrowser(String url) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI(url));
	}

	private void launchCompatibilityBrowser(String url) throws IOException {
		logger.info("launching compatibility browser version on URL::" + url);
		String os = System.getProperty("os.name");

		logger.info("compatibility verion::" + os);

		switch (os) {
		case "Linux": {
			launchLinuxBrowser(url);
		}
			break;
		}

	}

	private void launchLinuxBrowser(String url) throws IOException {
		logger.info("launching linux compatibility browser");
		/* Runtime rt = Runtime.getRuntime(); */
		/* String[] browsers = { "chromium", "firefox", "chrome" }; */

		Runtime.getRuntime().exec(String.format("%s %s", "chromium", url));
	}
}
