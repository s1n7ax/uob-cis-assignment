package org.s1n7ax.feedback.controller;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.s1n7ax.feedback.entity.Feedback;
import org.s1n7ax.feedback.entity.PurchaseHistory;
import org.s1n7ax.feedback.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * HomeControllerTest
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllersTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void openResourceCheck() {
		int status = restTemplate.getForEntity(format(getUrl(), "/"), ResponseEntity.class).getStatusCode().value();

		assertThat(status).isEqualTo(200).describedAs("root EP should be public");
	}

	@Test
	public void restrictedResourceCheck() throws Exception {
		{
			int status = restTemplate
					.exchange(format(getUrl(), "/restricted"), HttpMethod.GET, getValidUserEntity(), String.class)
					.getStatusCode().value();

			assertThat(status).isEqualTo(200).describedAs("user should be able to access EP");
		}

		{
			int status = restTemplate
					.exchange(format(getUrl(), "/restricted"), HttpMethod.GET, getInvalidUserEntity(), String.class)
					.getStatusCode().value();

			assertThat(status).isEqualTo(401).describedAs("user should not be able to access EP");
		}
	}

	long purchaseHistoryId;

	@Test
	public PurchaseHistory[] purchaseHistoryResourceCheck() throws Exception {
		ResponseEntity<PurchaseHistory[]> res = restTemplate.exchange(format(getUrl(), "/purchase/history"),
				HttpMethod.GET, getValidUserEntity(), PurchaseHistory[].class);

		assertThat(res.getStatusCode().value()).isEqualTo(200);

		PurchaseHistory[] body = (PurchaseHistory[]) res.getBody();
		assertThat(body.length).isEqualTo(1);
		assertThat(body[0].getId()).isNotEqualTo(0);

		purchaseHistoryId = body[0].getId();
		return body;
	}

	@Test
	public Feedback[] purchaseFeedbackResourceCheck() throws Exception {
		PurchaseHistory[] history = purchaseHistoryResourceCheck();

		ResponseEntity<Feedback[]> res = restTemplate.exchange(
				format(getUrl(), "/purchase/feedback?purchaseHistoryId=" + history[0].getId()), HttpMethod.GET,
				getValidUserEntity(), Feedback[].class);

		assertThat(res.getStatusCode().value()).isEqualTo(200);

		Feedback[] body = (Feedback[]) res.getBody();

		assertThat(body.length).isEqualTo(4);
		assertThat(body[0].getId()).isGreaterThan(0);

		return body;
	}

	@Test
	public void purchaseFeedbackSubmissionCheck() throws Exception {
		Feedback[] feedbacks = purchaseFeedbackResourceCheck();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Basic " + getBase64Auth("srinesh@gmail.com", "123"));
		headers.add("Content-Type", "application/json");

		int i = 1;
		for (Feedback feedback : feedbacks) {
			feedback.setRate(i);
			i++;
		}

		HttpEntity<Object> entity = new HttpEntity<Object>(feedbacks, headers);

		ResponseEntity<Feedback[]> res = restTemplate.exchange(
				format(getUrl(), "/purchase/feedback?purchaseHistoryId=" + purchaseHistoryId), HttpMethod.POST, entity,
				Feedback[].class);

		assertThat(res.getStatusCode().value()).isEqualTo(200);

		feedbacks = purchaseFeedbackResourceCheck();

		i = 1;
		for (Feedback feedback : feedbacks) {
			assertThat(feedback.getRate()).isEqualTo(i);
			i++;
		}
	}

	public void sellerRatingCheck() throws Exception {
		PurchaseHistory[] history = purchaseHistoryResourceCheck();

		ResponseEntity<Rating[]> res = restTemplate.exchange(
				format(getUrl(), "/rating?sellerId=" + history[0].getProduct().getSeller().getId()), HttpMethod.GET,
				getValidUserEntity(), Rating[].class);

		assertThat(res.getStatusCode().value()).isEqualTo(200);

		Rating[] body = (Rating[]) res.getBody();

		int i = 1;
		for (Rating rate : body) {
			assertThat(rate.getRating()).isEqualTo(i);
			i++;
		}
	}

	private HttpEntity<MultiValueMap<String, String>> getValidUserEntity() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Basic " + getBase64Auth("srinesh@gmail.com", "123"));
		return new HttpEntity<>(headers);
	}

	private HttpEntity<MultiValueMap<String, String>> getInvalidUserEntity() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Basic " + getBase64Auth("srinesh@gmail.com", "1234"));
		return new HttpEntity<>(headers);
	}

	private String getBase64Auth(String username, String password) {
		return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	private String getUrl() {
		return "http://localhost:" + port + "%s";
	}
}
