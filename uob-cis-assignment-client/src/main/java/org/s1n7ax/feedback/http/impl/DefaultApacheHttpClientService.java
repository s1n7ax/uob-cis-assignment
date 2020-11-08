package org.s1n7ax.feedback.http.impl;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.s1n7ax.feedback.http.ApacheHttpClientService;

/**
 * DefaultApacheHttpClientService singleton to hold http client and cookie store
 */
public class DefaultApacheHttpClientService implements ApacheHttpClientService {

	private static DefaultApacheHttpClientService instance = new DefaultApacheHttpClientService();

	private HttpClient client;
	private CookieStore cookieStore;

	public static DefaultApacheHttpClientService getInstance() {
		return instance;
	}

	private DefaultApacheHttpClientService() {
		cookieStore = new BasicCookieStore();
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	@Override
	public HttpClient getClient() {
		return client;
	}

	@Override
	public CookieStore getCookieStore() {
		return cookieStore;
	}
}
