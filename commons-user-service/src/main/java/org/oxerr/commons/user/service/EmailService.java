package org.oxerr.commons.user.service;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.commons.user.domain.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {

	Email saveEmail(@Nonnull Email email);

	Optional<Email> getEmailByAddress(@Nonnull String address);

	Page<Email> getEmails(
		@Nullable String address,
		@Nullable String username,
		@Nonnull Pageable pageable
	);

}
