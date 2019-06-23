package org.oxerr.commons.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.oxerr.commons.user.domain.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository
	extends
		CrudRepository<Role, UUID>,
		JpaSpecificationExecutor<Role>,
		QuerydslPredicateExecutor<Role> {

	Optional<Role> findByName(String name);

}
