package org.oxerr.commons.user.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Access(AccessType.PROPERTY)
@Table(
	name = "\"user\"",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_user_username",
			columnNames = "username"
		),
	}
)
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 2019062001L;

	private String username;
	private String password;

	private String nickname;

	private Set<Role> roles;

	private boolean enabled;

	public User() {
	}

	public User(
		String username,
		String password,
		Set<Role> roles,
		boolean enabled
	) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
	}

	@Override
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@XmlTransient
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nickname")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@XmlTransient
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "\"authorities\"",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"),
		foreignKey = @ForeignKey(name = "fk_authorities_user"),
		inverseForeignKey = @ForeignKey(name = "fk_authorities_role")
	)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@XmlTransient
	@Transient
	private Set<Role> getOrCreateRoles() {
		Set<Role> exists = this.getRoles();
		if (exists == null) {
			exists = new HashSet<>();
			this.setRoles(exists);
		}
		return exists;
	}

	@XmlTransient
	@Transient
	public void addRoles(Set<Role> roles) {
		if (roles == null || roles.isEmpty()) {
			return;
		}

		final Set<Role> exists = this.getOrCreateRoles();

		try {
			exists.addAll(roles);
		} catch (UnsupportedOperationException e) {
			final Set<Role> ret = new HashSet<>();
			ret.addAll(exists);
			ret.addAll(roles);
			this.setRoles(ret);
		}
	}

	@XmlTransient
	@Transient
	public void addRole(Role role) {
		if (role == null) {
			return;
		}

		final Set<Role> exists = this.getOrCreateRoles();

		try {
			exists.add(role);
		} catch (UnsupportedOperationException e) {
			final Set<Role> ret = new HashSet<>();
			ret.addAll(exists);
			ret.add(role);
			this.setRoles(ret);
		}
	}

	/**
	 * Returns if this user has any of the specified authorities.
	 *
	 * @param authorities requires at least one of the authorities (i.e.
	 * "ROLE_USER","ROLE_ADMIN" would mean either "ROLE_USER" or "ROLE_ADMIN"
	 * is required).
	 * @return true if any of the authorities is present, otherwise false.
	 */
	@XmlTransient
	@Transient
	public boolean hasAnyAuthority(String... authorities) {
		return getRoles().stream().map(role -> role.getAuthority())
			.anyMatch(Arrays.asList(authorities)::contains);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Column(nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	@Transient
	@XmlTransient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
