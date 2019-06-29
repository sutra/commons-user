package org.oxerr.commons.user.security;

import java.util.Optional;

import org.oxerr.commons.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * {@link AuditorAware} implementation.
 * It will return the currently authenticated principal as auditor
 * on every call to {@link #getCurrentAuditor()}.
 */
@Component
public class SecurityAuditorAware implements AuditorAware<User> {

	private final SecurityService securityService;

	@Autowired
	public SecurityAuditorAware(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> getCurrentAuditor() {
		return this.securityService.getPrincipal();
	}

}
