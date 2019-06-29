package org.oxerr.commons.user.email.repository;

import java.util.Optional;
import java.util.UUID;

import org.oxerr.commons.user.email.domain.Email;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository
	extends
		CrudRepository<Email, UUID>,
		JpaSpecificationExecutor<Email>,
		QuerydslPredicateExecutor<Email> {

	Optional<Email> findByAddress(String address);

}
