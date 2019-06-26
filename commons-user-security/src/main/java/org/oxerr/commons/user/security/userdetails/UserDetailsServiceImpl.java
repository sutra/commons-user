package org.oxerr.commons.user.security.userdetails;

import org.oxerr.commons.user.domain.User;
import org.oxerr.commons.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = this.userService.getUserByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(
				this.messages.getMessage("JdbcDaoImpl.notFound",
					new Object[] { username }, "Username {0} not found")
			));
		return org.springframework.security.core.userdetails.User.builder()
			.username(username)
			.password(user.getPassword())
			.authorities(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
			.disabled(!user.isEnabled())
			.build();
	}

}
