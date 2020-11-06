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

	private HttpClient client;
	private CookieStore cookieStore;
	private HttpClientBuilder builder;

	public static DefaultApacheHttpClientService getInstance() {
		return new DefaultApacheHttpClientService();
	}

	public static DefaultApacheHttpClientService getInstance(HttpClientBuilder builder) {
		return new DefaultApacheHttpClientService(builder);
	}

	public DefaultApacheHttpClientService() {
	}

	public DefaultApacheHttpClientService(HttpClientBuilder builder) {
		this.builder = builder;
	}

	@Override
	public HttpClient getClient() {

		if (client != null)
			return client;

		synchronized (DefaultApacheHttpClientService.class) {
			if (client == null)
				init();
		}

		return client;

	}

	@Override
	public CookieStore getCookieStore() {

		if (cookieStore != null)
			return cookieStore;

		synchronized (DefaultApacheHttpClientService.class) {
			if (cookieStore == null)
				init();
		}

		return cookieStore;

	}

	/**
	 * Instantiate http client and cookie store
	 */
	private void init() {

		cookieStore = new BasicCookieStore();

		if (builder == null)
			builder = HttpClientBuilder.create();

		client = builder.setDefaultCookieStore(cookieStore).build();

	}
}
