package org.s1n7ax.feedback.service.impl;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * DefaultApacheHttpClientService singleton to hold http client and cookie store
 */
public class DefaultApacheHttpClientService {

	private static DefaultApacheHttpClientService instance;

	private HttpClient client;
	private CookieStore cookieStore;

	public static DefaultApacheHttpClientService getInstance() {
		if(instance != null)
			return instance;
		
		synchronized (DefaultApacheHttpClientService.class) {
			if(instance == null)
				instance = new DefaultApacheHttpClientService();
		}
		
		return instance;
	}

	private DefaultApacheHttpClientService() {
		cookieStore = new BasicCookieStore();
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	public HttpClient getClient() {
		return client;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}
}
