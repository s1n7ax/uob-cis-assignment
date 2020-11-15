package org.s1n7ax.feedback.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

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

	@GetMapping("/restricted")
	public String restricted() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
