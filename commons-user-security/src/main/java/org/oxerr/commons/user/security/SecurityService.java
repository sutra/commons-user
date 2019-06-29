package org.oxerr.commons.user.security;

import java.security.Principal;
import java.util.Optional;

import javax.annotation.Nullable;

import org.oxerr.commons.user.domain.User;
import org.springframework.security.core.Authentication;

public interface SecurityService {

	Optional<User> getPrincipal();

	Optional<User> getPrincipal(@Nullable Principal principal);

	Optional<User> getPrincipal(@Nullable Authentication authentication);

}
