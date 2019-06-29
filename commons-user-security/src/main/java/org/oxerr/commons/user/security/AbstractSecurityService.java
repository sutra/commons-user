package org.oxerr.commons.user.security;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.commons.user.domain.User;
import org.oxerr.commons.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractSecurityService implements SecurityService {

	private final Logger log = LogManager.getLogger();
	private final UserService userService;

	public AbstractSecurityService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Optional<User> getPrincipal() {
		final @Nullable Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return this.getPrincipal(authentication);
	}

	@Override
	public Optional<User> getPrincipal(Principal principal) {
		log.debug("principal: {}", principal);

		final Optional<User> user;

		if (principal == null) {
			user = Optional.empty();
		} else if (principal instanceof Authentication) {
			user = this.getPrincipal((Authentication) principal);
		} else if (principal.getName() != null) {
			user = this.userService.getUserByUsername(principal.getName());
		} else {
			user = Optional.empty();
		}

		return user;
	}

	@Override
	public Optional<User> getPrincipal(Authentication authentication) {
		log.debug("authentication: {}", authentication);

		if (authentication == null) {
			return Optional.empty();
		}

		final Optional<User> user;
		final Object principal = authentication.getPrincipal();

		if (principal == null) {
			user = Optional.empty();
		} else if (principal instanceof User) {
			user = Optional.of((User) principal);
		} else if (principal instanceof UserDetails) {
			user = this.getPrincipal((UserDetails) principal);
		} else if (principal instanceof String) {
			user = this.userService.getUserByUsername((String) principal);
		} else {
			user = Optional.empty();
		}

		return user;
	}

	protected Optional<User> getPrincipal(@Nullable UserDetails userDetails) {
		log.debug("userDetails: {}", userDetails);

		final Optional<User> user;
		if (userDetails == null) {
			user = Optional.empty();
		} else {
			final @Nonnull String username = userDetails.getUsername();
			user = this.userService.getUserByUsername(username);
		}
		return user;
	}

}
