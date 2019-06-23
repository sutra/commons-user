package org.oxerr.commons.user.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.oxerr.commons.user.domain.QUser;
import org.oxerr.commons.user.domain.Role;
import org.oxerr.commons.user.domain.User;
import org.oxerr.commons.user.repository.UserRepository;
import org.oxerr.commons.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service
public class UserServiceImpl implements UserService {

	private final QUser qUser = QUser.user;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(
		UserRepository userRepository,
		PasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public Optional<User> getUser(UUID id) {
		return this.userRepository.findById(id);
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public Page<User> getUsers(
		String username,
		Boolean enabled,
		UUID[] roleIds,
		Pageable pageable
	) {
		final Predicate predicate = ExpressionUtils.allOf(
			username != null ? qUser.username.eq(username) : null,
			enabled != null ? qUser.enabled.eq(enabled) : null,
			ArrayUtils.isNotEmpty(roleIds) ? qUser.roles.any().id.in(roleIds) : null
		);
		return this.userRepository.findAll(predicate, pageable);
	}

	@Override
	public User createUser(
		String username,
		String rawPassword,
		Set<Role> roles,
		boolean enabled
	) {
		final User user = new User(
			username,
			this.passwordEncoder.encode(rawPassword), roles, enabled
		);
		return this.saveUser(user);
	}

	@Override
	public User changePassword(User user, String rawPassword) {
		user.setPassword(this.passwordEncoder.encode(rawPassword));
		return this.saveUser(user);
	}

}
