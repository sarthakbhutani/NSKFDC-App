package com.nskfdc.scgj.common;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;



	@Configuration
	@EnableWebSecurity
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				// @formatter:off
				http
						.httpBasic().and()
						.authorizeRequests()
						.antMatchers("/index.html","/","/SCGJ_admin.html","/trainingPartner.html","/css/**","/js/**","/images/**").permitAll()
						.anyRequest().authenticated()
						.and()
						.csrf()
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
				// @formatter:on
			}


//
//		 @Override
//		    protected void configure(HttpSecurity http) throws Exception {
//		     http.csrf().disable();   
//			 http.authorizeRequests().anyRequest().permitAll();
//		    }

		
	}

