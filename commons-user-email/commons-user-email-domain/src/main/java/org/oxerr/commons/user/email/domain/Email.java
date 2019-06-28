package org.oxerr.commons.user.email.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.oxerr.commons.user.domain.BaseEntity;
import org.oxerr.commons.user.domain.User;

/**
 * The email.
 * <p>
 * One {@link User} could have 0..* #{@link Email}s.
 * </p>
 */
@Entity
@Cacheable
@Access(AccessType.PROPERTY)
@Table(
	name = "email",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_email_address",
			columnNames = "address"
		),
	},
	indexes = {
		@Index(
			name = "idx_email_user",
			columnList = "user_id"
		),
	}
)
public class Email extends BaseEntity {

	private static final long serialVersionUID = 2019062001L;

	private User user;

	private String address;

	public Email() {
	}

	public Email(User user) {
		this.user = user;
	}

	public Email(User user, String address) {
		this.user = user;
		this.address = address;
	}

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "user_id",
		foreignKey = @ForeignKey(name = "fk_email_user")
	)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
