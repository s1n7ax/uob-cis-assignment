package org.s1n7ax.feedback.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.security.auth.login.LoginException;

import org.s1n7ax.feedback.model.LoginCredential;
import org.s1n7ax.feedback.security.GoogleSignInAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.VerifyException;

/**
 * AuthController handle authentication and authorization endpoints
 */
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private GoogleSignInAuthentication googleSigninAuth;

	@Value("${oauth.google.authorization.uri}")
	private String googleOAuthURL;

	@Value("${oauth.google.authorization.client_id}")
	private String googleOAuthClientId;

	@Value("${oauth.google.authorization.redirect_uri}")
	private String googleOAuthRedirectURI;

	/**
	 * Authenticates a user when login credentials are sent via POST body
	 * 
	 * @param credential user login credentials to authenticate the current session
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginCredential credential) {

		Authentication currAuth = SecurityContextHolder.getContext().getAuthentication();

		// already logged in user sending a login request again
		// re-authentication will be ignored
		if (!currAuth.getName().equals("anonymousUser"))
			return ResponseEntity.ok().build();

		// validate user
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword()));

		// add the authentication token to current context
		SecurityContextHolder.getContext().setAuthentication(auth);

		return ResponseEntity.ok().build();

	}

	/**
	 * Redirects the browser session to requested social login preperation endpoint
	 * (/login/social/google) validity will not be checked
	 * 
	 * @param sessionId - session of the standalone application
	 * @param type      - type of the social login (only supported google openid
	 *                  right now)
	 */
	@GetMapping("/login/social")
	public ResponseEntity<?> social(@RequestParam String sessionId,
			@RequestParam(defaultValue = "google") String type) {

		// send bad request status if the session is not found
		if (sessionId == null || sessionId.equals(""))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		switch (type.toLowerCase()) {
		case "google": {

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/login/social/google"));
			headers.add("Set-Cookie", String.format("JSESSIONID=%s;path=/", sessionId));

			return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);

		}

		default:
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * Redirects to google authorizaton URL
	 */
	@GetMapping("/login/social/google")
	public ResponseEntity<?> googleSignin() {

		String uris = String.format(googleOAuthURL, googleOAuthClientId, googleOAuthRedirectURI);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(uris));

		return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);

	}

	/**
	 * Handle redirect from google authorization endpoint
	 * 
	 * @param code authorization code
	 * @throws IOException
	 */
	@GetMapping("/oauth/google")
	public ResponseEntity<?> googleOAuth(@RequestParam String code) throws IOException {

		String email;

		try {

			// validate checks if the user is valid and email is verified
			email = googleSigninAuth.validate(code);

		} catch (LoginException ex) {

			// login exception will be thrown when the auth code is invalid
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		} catch (VerifyException ex) {

			// verify exception will be thrown when the email is not varified
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}

		// create authentication for the new user
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
				AuthorityUtils.createAuthorityList("ROLE_CUSTOMER"));

		// authenticate the authentication for the current session
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		try {
            File file = ResourceUtils.getFile("classpath:static/google-signin-success.html");
            return ResponseEntity.ok(Files.readString(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
        	return ResponseEntity.ok("Successfully logged in");
        }
	}
}
