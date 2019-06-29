package org.oxerr.commons.user.phone.repository;

import java.util.Optional;
import java.util.UUID;

import org.oxerr.commons.user.phone.domain.Phone;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository
	extends
		CrudRepository<Phone, UUID>,
		JpaSpecificationExecutor<Phone>,
		QuerydslPredicateExecutor<Phone> {

	Optional<Phone> findByNumber(String number);

}
