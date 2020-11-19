package org.s1n7ax.feedback.browser;

/**
 * Browser command interface
 */
public interface Browser {

	/**
	 * opens a browser windows available in the current operation system and navigate to the passed url
	 *
	 * @param url url to navigate to
	 */
	void launch(String url);
}

