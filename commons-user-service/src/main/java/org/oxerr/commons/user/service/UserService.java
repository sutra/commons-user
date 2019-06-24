package org.oxerr.commons.user.service;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.commons.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	User saveUser(@Nonnull User user);

	Optional<User> getUser(@Nonnull UUID id);

	Optional<User> getUserByUsername(@Nonnull String username);

	Page<User> getUsers(
		@Nullable String username,
		@Nullable Boolean enabled,
		@Nullable UUID[] roleIds,
		@Nonnull Pageable pageable
	);

}
