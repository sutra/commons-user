package org.oxerr.commons.user.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The phone.
 *
 * <p>
 * One {@link User} could have 0..* #{@link Phone}s.
 * </p>
 */
@Entity
@Cacheable
@Access(AccessType.PROPERTY)
@Table(
	name = "phone",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_phone_number",
			columnNames = "number"
		),
	}
)
public class Phone extends BaseEntity {

	private static final long serialVersionUID = 2019062001L;

	private User user;

	private String number;

	public Phone() {
	}

	public Phone(User user) {
		this.user = user;
	}

	public Phone(User user, String number) {
		this.user = user;
		this.number = number;
	}

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "user_id",
		foreignKey = @ForeignKey(name = "fk_phone_user")
	)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "\"number\"", nullable = false)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
