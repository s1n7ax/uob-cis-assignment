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

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser(User.withUsername("srinesh@gmail.com").password("123").roles("CUSTOMER"));
	}

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

	@Bean
	public PasswordEncoder getPasswordEncorder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
