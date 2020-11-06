package org.s1n7ax.feedback.model;

import org.springframework.stereotype.Service;

/**
 * LoginCredential
 */
@Service
public class LoginCredential {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginCredential [email=" + email + ", password=" + password + "]";
	}
}
