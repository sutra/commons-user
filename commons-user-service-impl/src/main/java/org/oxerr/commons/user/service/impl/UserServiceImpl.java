package org.oxerr.commons.user.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.oxerr.commons.user.domain.QUser;
import org.oxerr.commons.user.domain.User;
import org.oxerr.commons.user.repository.UserRepository;
import org.oxerr.commons.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service
public class UserServiceImpl implements UserService {

	private final QUser qUser = QUser.user;

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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

}
