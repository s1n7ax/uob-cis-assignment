package org.s1n7ax.feedback.common;

/**
 * Browser
 */
public interface Browser {

	/**
	 * launch browser instance and navigate to given url
	 * 
	 * @param url location to navigate after the browser window is opened
	 */
	void launch(String url);
}
