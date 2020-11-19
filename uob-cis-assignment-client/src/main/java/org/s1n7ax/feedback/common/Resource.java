package org.s1n7ax.feedback.common;

import java.net.URL;
import org.s1n7ax.feedback.Main;

/**
 * provide resource access from Main package location
 */
public class Resource {

	/**
	 * returns url of the resource
	 *
	 * @return url of the resource
	 */
	public static URL getResource(String path) {
		return Main.class.getResource(path);
	}
}
