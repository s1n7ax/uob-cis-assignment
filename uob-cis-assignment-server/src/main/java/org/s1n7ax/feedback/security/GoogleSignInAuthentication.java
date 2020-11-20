package org.s1n7ax.feedback.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.VerifyException;

/**
 * Google signin service
 */
@Service
public class GoogleSignInAuthentication {

	@Value("${oauth.google.authorization.uri}")
	private String googleOAuthURL;

	@Value("${oauth.google.authorization.client_secret_file}")
	private String clientSecretFile;

	@Value("${oauth.google.authorization.redirect_uri}")
	private String redirectURI;

	private GoogleClientSecrets clientSecrets;

	/**
	 * Read the client secret file and returns clientSecret object
	 *
	 * @return client secret
	 * @throws FileNotFoundException if the secret file not found
	 * @throws IOException if secrets file can not be read
	 */
	public GoogleClientSecrets getClientSecrets() throws FileNotFoundException, IOException {

		if (clientSecrets != null)
			return clientSecrets;

		synchronized (GoogleClientSecrets.class) {
			if (clientSecrets == null) {
				File file = ResourceUtils.getFile(String.format("classpath:%s", clientSecretFile));
				clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(file));
			}
		}

		return clientSecrets;

	}

	/**
	 * Validate authorization code and returns email of the logged in user
	 *
	 * @return email of the user
	 * @throws IOException if the secrets cannot be retrieved
	 * @throws LoginException if the authorization code is not valid
	 */
	public String validate(String authCode) throws IOException, LoginException {

		GoogleTokenResponse res;
		try {

			res = new GoogleAuthorizationCodeTokenRequest( //
					new NetHttpTransport(), //
					JacksonFactory.getDefaultInstance(), //
					"https://oauth2.googleapis.com/token", //
					getClientSecrets().getDetails().getClientId(), //
					getClientSecrets().getDetails().getClientSecret(), //
					authCode, //
					redirectURI) //
							.execute();

		} catch (TokenResponseException ex) {
			throw new LoginException("Invalid social login");
		}

		GoogleIdToken idToken = res.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();

		if (!payload.getEmailVerified())
			throw new VerifyException("Email is not varified");

		return payload.getEmail();

	}
}
