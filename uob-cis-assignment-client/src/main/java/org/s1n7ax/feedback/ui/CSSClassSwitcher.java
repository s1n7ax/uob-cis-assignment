package org.s1n7ax.feedback.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Control;

/**
 * CSSClassSwitcher changes the javafx class on an component.
 */
public class CSSClassSwitcher {
	Control control;
	Map<String, String> classMap = new HashMap<>();
	String lastChangedClass;

	private CSSClassSwitcher() {
	}

	public static CSSClassSwitcher getInstance() {
		return new CSSClassSwitcher();
	}

	/**
	 * takes the UI component that should be handled
	 *
	 * @return current instance
	 * @param contorl UI component whose classes should be switched
	 */
	public CSSClassSwitcher setControl(Control control) {
		this.control = control;
		return this;
	}

	/**
	 * register a class in switcher class name and the class value is the same
	 *
	 * @param className name of the class to register
	 * @return current instance
	 */
	public CSSClassSwitcher addClass(String className) {
		addClass(className, className);
		return this;
	}

	/**
	 * register a class in switcher class name and the class value is the same
	 *
	 * @param key       key to access the class value
	 * @param className class name that goes in to the javafx component itself
	 * @return current instance
	 */
	public CSSClassSwitcher addClass(String key, String className) {
		classMap.put(key, className);
		return this;
	}

	/**
	 * Changes the classes to passed class and all existing registered classes will
	 * be removed
	 * 
	 * @param key
	 * @return current instance
	 */
	public CSSClassSwitcher changeClass(String key) {
		// cancel change if the key is not found in the map
		if (!classMap.containsKey(key))
			return this;

		// there should be an control to change class
		if (control == null)
			return this;

		// multiple changes to same class will be ignored
		if (lastChangedClass != null && lastChangedClass.equals(key))
			return this;

		// remove last changed class
		if (lastChangedClass != null)
			control.getStyleClass().remove(classMap.get(lastChangedClass));

		// change class if the class is in the classes map
		if (classMap.containsKey(key)) {
			control.getStyleClass().add(classMap.get(key));
			lastChangedClass = key;
		}

		return this;
	}
}
