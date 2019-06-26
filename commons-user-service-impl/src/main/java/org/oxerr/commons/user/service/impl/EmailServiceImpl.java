package org.oxerr.commons.user.service.impl;

import java.util.Optional;

import org.oxerr.commons.user.domain.Email;
import org.oxerr.commons.user.domain.QEmail;
import org.oxerr.commons.user.repository.EmailRepository;
import org.oxerr.commons.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service
public class EmailServiceImpl implements EmailService {

	private final QEmail qEmail = QEmail.email;

	private final EmailRepository emailRepository;

	@Autowired
	public EmailServiceImpl(EmailRepository emailRepository) {
		this.emailRepository = emailRepository;
	}

	@Override
	public Email saveEmail(Email email) {
		return this.emailRepository.save(email);
	}

	@Override
	public Optional<Email> getEmailByAddress(String address) {
		return this.emailRepository.findByAddress(address);
	}

	@Override
	public Page<Email> getEmails(
		String address,
		String username,
		Pageable pageable
	) {
		final Predicate predicate = ExpressionUtils.allOf(
			address != null ? qEmail.address.eq(address) : null,
			username != null ? qEmail.user.username.eq(username) : null
		);
		return this.emailRepository.findAll(predicate, pageable);
	}

}
