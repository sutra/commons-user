package org.oxerr.commons.user.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.oxerr.commons.user.domain.QRole;
import org.oxerr.commons.user.domain.Role;
import org.oxerr.commons.user.repository.RoleRepository;
import org.oxerr.commons.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service
public class RoleServiceImpl implements RoleService {

	private final QRole qRole = QRole.role;
	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role saveRole(Role role) {
		return this.roleRepository.save(role);
	}

	@Override
	public Optional<Role> getRole(UUID id) {
		return this.roleRepository.findById(id);
	}

	@Override
	public Optional<Role> getRoleByName(String name) {
		return this.roleRepository.findByName(name);
	}

	@Override
	public Page<Role> getRoles(String name, Pageable pageable) {
		final Predicate predicate = ExpressionUtils.allOf(
			name != null ? qRole.name.eq(name) : null
		);
		return this.roleRepository.findAll(predicate, pageable);
	}

}
