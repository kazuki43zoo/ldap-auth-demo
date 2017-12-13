package com.example.ldapauthdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user", "/user.*", "/user/**").hasRole("USER")
				.antMatchers("/admin", "/admin.*", "/admin/**").hasRole("ADMIN")
				.antMatchers("/**").authenticated()
				.and()
				.logout().permitAll()
				.and()
				.formLogin().permitAll();
	}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.ldapAuthentication()
			.userDnPatterns("uid={0},ou=people")
			.groupSearchBase("ou=groups")
//			.contextSource()
//				.root("dc=example,dc=com")
			.contextSource()
				.url("ldap://localhost:10389/dc=example,dc=com")
				.managerDn("cn=admin,dc=example,dc=com")
				.managerPassword("password")
			.and()
			.userDetailsContextMapper(new PersonContextMapper());
}
}
