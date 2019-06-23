package org.oxerr.commons.user.service;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.commons.user.domain.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService {

	Phone savePhone(@Nonnull Phone phone);

	Optional<Phone> getPhoneByNumber(@Nonnull String number);

	Page<Phone> getPhones(
		@Nullable String number,
		@Nullable String username,
		@Nonnull Pageable pageable
	);

}
