package org.s1n7ax.feedback.http;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

/**
 * ServiceClient
 */
public interface ApacheHttpClientService {

	/**
	 * Returns http client
	 * 
	 * @return
	 */
	public HttpClient getClient();

	/**
	 * Returns cookie store
	 * 
	 * @return
	 */
	public CookieStore getCookieStore();
}
