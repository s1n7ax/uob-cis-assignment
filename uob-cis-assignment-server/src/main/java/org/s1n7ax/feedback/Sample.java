package org.s1n7ax.feedback;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample
 */
public class Sample {

	public static void main(String[] args) {
		List<String> li = new ArrayList<>();

		li.add("Srinesh");
		li.add("Nisala");

		String name = li.stream().filter(e -> e.equals("Srinesh")).findFirst().orElse(null);
		System.out.println(name);
	}
}
