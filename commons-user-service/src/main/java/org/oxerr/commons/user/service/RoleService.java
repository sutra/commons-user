package org.oxerr.commons.user.service;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.commons.user.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

	Role saveRole(@Nonnull Role role);

	Optional<Role> getRole(@Nonnull UUID id);

	Optional<Role> getRoleByName(@Nonnull String name);

	Page<Role> getRoles(@Nullable String name, @Nonnull Pageable pageable);

}
