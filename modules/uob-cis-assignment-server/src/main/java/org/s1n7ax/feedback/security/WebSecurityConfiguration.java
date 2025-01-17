package org.s1n7ax.feedback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Set the web security configuration
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * Configure users
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser(User.withUsername("srinesh@gmail.com").password("123").roles("CUSTOMER"));
	}

	/**
	 * Configure the http security for endpoints
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http //
				.csrf().disable() //
				.headers().frameOptions().disable() //
				.and() //
				.authorizeRequests() //
				.antMatchers("/").permitAll() //
				.antMatchers("/login").permitAll() //
				.antMatchers("/login/social").permitAll() //
				.antMatchers("/login/social/google").permitAll() //
				.antMatchers("/oauth/google").permitAll() //
				.antMatchers("/h2-console").permitAll() //
				.anyRequest().authenticated() //
				.and().httpBasic();
	}

	/**
	 * Configure the password encoder
	 */
	@Bean
	protected PasswordEncoder getPasswordEncorder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * Override authentication manager bean
	 * if this is not implemented, service will not be started
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
