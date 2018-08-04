package com.nskfdc.scgj.common;


import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {

	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			Privilege privilege = method.getMethodAnnotation(Privilege.class);
			boolean found = false;
			if (privilege != null) {
				Collection<String> authorities = SecurityContextHolder
						.getContext().getAuthentication().getAuthorities()
						.stream().map(auth -> auth.getAuthority())
						.collect(Collectors.toList());
				for (String methodAuth : privilege.value()) {
					if (authorities.contains(methodAuth)) {
						found = true;
						break;
					}
				}

				if(!found){
					throw new AuthorizationServiceException("you are not authorized for this action!");
				}

			}
			return true;
		}
		return true;

	}

}
