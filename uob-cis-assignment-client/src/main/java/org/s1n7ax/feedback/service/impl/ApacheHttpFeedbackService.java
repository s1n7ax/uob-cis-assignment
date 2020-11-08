package org.s1n7ax.feedback.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.google.gson.Gson;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.s1n7ax.feedback.configuration.FeedbackServiceConfig;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.entity.Rating;
import org.s1n7ax.feedback.http.ApacheHttpClientService;
import org.s1n7ax.feedback.http.impl.DefaultApacheHttpClientService;
import org.s1n7ax.feedback.service.FeedbackService;

/**
 * HttpFeedbackService
 */
public class ApacheHttpFeedbackService implements FeedbackService {
	private Logger logger = LogManager.getLogger(ApacheHttpFeedbackService.class);
	private HttpClient client;
	private CookieStore cookieStore;

	public ApacheHttpFeedbackService() {

		ApacheHttpClientService service = DefaultApacheHttpClientService.getInstance();
		client = service.getClient();
		cookieStore = service.getCookieStore();

	}

	public ApacheHttpFeedbackService(HttpClient client, CookieStore cookieStore) {

		this.client = client;
		this.cookieStore = cookieStore;

	}

	public ApacheHttpFeedbackService(ApacheHttpClientService service) {

		client = service.getClient();
		cookieStore = service.getCookieStore();

	}

	@Override
	public String getSession() throws Exception {

		logger.info("getting the session");

		Cookie cookie = getCookie(FeedbackServiceConfig.SESSION_KEY, "/");

		// cookie already exist
		if (cookie != null)
			return cookie.getValue();

		logger.debug("cookie is not available in cookie store");

		CloseableHttpResponse response = null;

		try {

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.GET_SESSION_EP).build();
			HttpGet get = new HttpGet(uri);
			response = (CloseableHttpResponse) client.execute(get);
			cookie = getCookie(FeedbackServiceConfig.SESSION_KEY, "/");

			if (cookie == null) {
				logger.error(FeedbackServiceConfig.SESSION_KEY + " was not found after tyring to retrieve from server");
				throw new Exception("unable to get session id");
			}

			logger.info("getting the session - successful");

			return cookie.getValue();

		} catch (URISyntaxException ex) {

			logger.error("invalid URL", ex);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException e) {

			logger.error("unable to connect to host", e);
			throw new Exception("Unable to connect to server");

		} finally {

			if (response != null)
				response.close();

		}

	}

	@Override
	public void login(String email, String password) throws Exception {

		logger.info("basic login with email::" + email + ", password::" + password.replaceAll("\\w", "*"));

		CloseableHttpResponse response = null;

		try {

			JSONObject obj = new JSONObject(Map.of("email", email, "password", password));
			StringEntity reqBody = new StringEntity(obj.toString(), ContentType.APPLICATION_JSON);

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.POST_BASIC_LOGIN_EP).build();
			HttpPost post = new HttpPost(uri);
			post.setEntity(reqBody);

			response = (CloseableHttpResponse) client.execute(post);
			int status = response.getStatusLine().getStatusCode();

			if (status == 200) {
				logger.info("basic login successful");
				logger.info("session id::" + getCookie(FeedbackServiceConfig.SESSION_KEY, "/"));
				return;
			}

			if (status == 401) {
				logger.error("login request failed. server returned 401");
				throw new Exception("Invalid email or password");
			}

			logger.error("Login failed due to status::" + status + " response");
			throw new LoginException("Login failed due to status::" + status + " response");

		} catch (URISyntaxException ex) {

			logger.error("invalid uri", ex);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (ClientProtocolException ex) {

			logger.error("invalid uri", ex);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException ex) {

			logger.error("unable to connect to host", ex);
			throw new Exception("Unable to connect to server");

		}

		finally {

			if (response != null)
				response.close();

		}

	}

	/**
	 * Get cookie by name and path
	 * 
	 * @param name
	 * @param path
	 * @return
	 */
	private Cookie getCookie(String name, String path) {

		return cookieStore.getCookies().stream()
				.filter(cookie -> (cookie.getName().equals(name) && cookie.getPath().equals(path))).findFirst()
				.orElse(null);

	}

	private URIBuilder getURIBuilder() {

		return new URIBuilder() //
				.setScheme(FeedbackServiceConfig.PROTOCOL) //
				.setHost(FeedbackServiceConfig.HOST) //
				.setPort(FeedbackServiceConfig.PORT) //
				.setPath(FeedbackServiceConfig.GET_SESSION_EP); //

	}

	@Override
	public PurchaseHistory[] getPurchaseHistory() throws Exception {

		logger.info("getting purchase history");

		try {

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.GET_PURCHASE_HISTORY_EP).build();

			HttpGet get = new HttpGet(uri);
			CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

			logger.info("session id::" + getCookie(FeedbackServiceConfig.SESSION_KEY, "/"));

			int status = response.getStatusLine().getStatusCode();

			if (status != 200) {

				logger.error("getting purchase history failed due to status::" + status + " response");
				throw new Exception("Unhandled response status::" + status + ". Please contact system administrator");
			}

			String payload = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Gson gson = new Gson();
			PurchaseHistory[] purchaseHistoryArr = gson.fromJson(payload, PurchaseHistory[].class);
			response.close();

			logger.info("getting purchase history - successful");

			return purchaseHistoryArr;

		} catch (URISyntaxException e) {

			logger.error("invalid uri syntax", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (ClientProtocolException e) {

			logger.error("invalid protocol", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException e) {

			logger.error("unable to connect to host", e);
			throw new Exception("Unable to connect to server");

		}
	}

	@Override
	public Feedback[] getFeedback(Long purchaseHistoryId) throws Exception {

		try {

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.GET_FEEDBACKS_EP)
					.addParameter("purchaseHistoryId", String.valueOf(purchaseHistoryId)).build();

			HttpGet get = new HttpGet(uri);
			CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

			int status = response.getStatusLine().getStatusCode();

			if (status != 200) {
				logger.error("getting feedback failed due to status::" + status + " response");
				throw new Exception("Getting feedbacks failed due to status::" + status + " response");
			}

			String payload = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Gson gson = new Gson();
			Feedback[] feedbacks = gson.fromJson(payload, Feedback[].class);
			response.close();

			return feedbacks;

		} catch (URISyntaxException e) {

			logger.error("invalid uri syntex", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (ClientProtocolException e) {

			logger.error("invalid protocol", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException e) {

			logger.error("unable to connect to host", e);
			throw new Exception("Unable to connect to server");

		}
	}

	@Override
	public void updateFeedback(Long purchaseHistoryId, Feedback[] feedbacks) throws Exception {

		logger.info("posting feedbacks");

		try {

			Gson gson = new Gson();
			String json = gson.toJson(feedbacks, Feedback[].class);
			logger.debug("json::" + json);

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.POST_FEEDBACKS_EP)
					.addParameter("purchaseHistoryId", String.valueOf(purchaseHistoryId)).build();
			StringEntity reqBody = new StringEntity(json, ContentType.APPLICATION_JSON);
			HttpPost post = new HttpPost(uri);
			post.setEntity(reqBody);

			CloseableHttpResponse response = (CloseableHttpResponse) client.execute(post);
			int status = response.getStatusLine().getStatusCode();

			if (status != 200) {
				logger.error("updating feedback failed due to status::" + status + " response");
				throw new Exception("Updating feedback failed.  Please contact system administrator");
			}

		} catch (URISyntaxException ex) {

			logger.error("invalid uri", ex);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (ClientProtocolException ex) {

			logger.error("invalid uri", ex);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException ex) {

			logger.error("unable to connect to host", ex);
			throw new Exception("Unable to connect to server");

		}
	}

	@Override
	public Rating[] getRating(Long sellerId) throws Exception {
		try {

			URI uri = getURIBuilder().setPath(FeedbackServiceConfig.GET_RATING_EP)
					.addParameter("sellerId", String.valueOf(sellerId)).build();

			HttpGet get = new HttpGet(uri);
			CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

			int status = response.getStatusLine().getStatusCode();

			if (status != 200) {
				logger.error("getting feedback failed due to status::" + status + " response");
				throw new Exception("Getting feedbacks failed due to status::" + status + " response");
			}

			String payload = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

			Gson gson = new Gson();
			Rating[] ratings = gson.fromJson(payload, Rating[].class);
			response.close();

			return ratings;

		} catch (URISyntaxException e) {

			logger.error("invalid uri syntex", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (ClientProtocolException e) {

			logger.error("invalid protocol", e);
			throw new Exception("Application configuration error. Please contact system administrator");

		} catch (IOException e) {

			logger.error("unable to connect to host", e);
			throw new Exception("Unable to connect to server");

		}
	}
}
