package org.oxerr.commons.user.service.impl;

import java.util.Optional;

import org.oxerr.commons.user.domain.Phone;
import org.oxerr.commons.user.domain.QPhone;
import org.oxerr.commons.user.repository.PhoneRepository;
import org.oxerr.commons.user.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service
public class PhoneServiceImpl implements PhoneService {

	private final QPhone qPhone = QPhone.phone;
	private final PhoneRepository phoneRepository;

	@Autowired
	public PhoneServiceImpl(PhoneRepository phoneRepository) {
		this.phoneRepository = phoneRepository;
	}

	@Override
	public Phone savePhone(Phone phone) {
		return this.phoneRepository.save(phone);
	}

	@Override
	public Optional<Phone> getPhoneByNumber(String number) {
		return this.phoneRepository.findByNumber(number);
	}

	@Override
	public Page<Phone> getPhones(
		String number,
		String username,
		Pageable pageable
	) {
		final Predicate predicate = ExpressionUtils.allOf(
			number != null ? qPhone.number.eq(number) : null,
			username != null ? qPhone.user.username.eq(username) : null
		);
		return this.phoneRepository.findAll(predicate, pageable);
	}

}
