package org.s1n7ax.feedback.common;

import java.net.URL;

import org.s1n7ax.feedback.Main;

public class Resource {
	public static URL getResource(String path) {
		return Main.class.getResource(path);
	}
}
