package org.s1n7ax.feedback;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

/**
 * Sample
 */
public class Sample {

	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		URI uri = new URIBuilder() //
				.setHost("localhost") //
				.setScheme("http").setPort(8080) //
				.setPath("/home") //
				.build();
		System.out.println(uri.toString());
	}
}
