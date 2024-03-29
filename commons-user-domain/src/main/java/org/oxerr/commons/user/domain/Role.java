package org.oxerr.commons.user.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Cacheable
@Access(AccessType.PROPERTY)
@Table(
	name = "\"role\"",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_role_name",
			columnNames = "name"
		),
	}
)
public class Role extends BaseEntity {

	private static final long serialVersionUID = 2019062401L;

	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";

	private static final String ROLE_PREFIX = "ROLE_";

	public static final String ROLE_USER = ROLE_PREFIX + USER;
	public static final String ROLE_ADMIN = ROLE_PREFIX + ADMIN;

	private String name;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
