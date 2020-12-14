package org.s1n7ax.feedback.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * HomeController is the root
 */
@RestController
public class HomeController {

	/**
	 * Root is an open endpoint and main purpose is to send back the session cookie
	 */
	@GetMapping("/")
	public ResponseEntity<?> home() {

		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", String.format("JSESSIONID=%s", sessionId));

		return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	/**
	 * Restricted endpoint can be accessed by only authorized users This endpoint
	 * will be used to check if the user is logged in or not when user is logged in
	 * using social login or to get the logged in user's email address
	 *
	 *
	 * @return email of the currently logged in user
	 */
	@GetMapping("/restricted")
	public String restricted() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
