package org.s1n7ax.feedback.ui;

import org.s1n7ax.feedback.ui.impl.DefaultParentFactory;

/**
* Parents
*/
public class Parents {
	private ParentFactory factory;

	public Parents() {
		this.factory = new DefaultParentFactory();
	}

	public void setFactory(ParentFactory factory) {
		this.factory = factory;
	}

	public ParentFactory getFactory() {
		return factory;
	}
}
