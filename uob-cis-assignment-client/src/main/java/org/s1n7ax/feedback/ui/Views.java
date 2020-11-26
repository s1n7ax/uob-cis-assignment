package org.s1n7ax.feedback.ui;

import org.s1n7ax.feedback.ui.impl.DefaultViewFactory;

/**
* Views
*/
public class Views {

	private ViewFactory factory;

	public Views() {
		factory = new DefaultViewFactory();
	}

	public void setFactory(ViewFactory factory) {
		this.factory = factory;
	}

	public ViewFactory getFactory() {
		return factory;
	}
}
