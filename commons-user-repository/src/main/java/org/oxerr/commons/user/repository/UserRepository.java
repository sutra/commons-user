package org.oxerr.commons.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.oxerr.commons.user.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
	extends
		CrudRepository<User, UUID>,
		JpaSpecificationExecutor<User>,
		QuerydslPredicateExecutor<User> {

	Optional<User> findByUsername(String username);

}
