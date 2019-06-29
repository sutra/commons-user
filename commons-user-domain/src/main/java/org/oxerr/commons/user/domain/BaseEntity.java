package org.oxerr.commons.user.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Access(AccessType.PROPERTY)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2019062001L;

	private UUID id;

	private User createdBy;
	private Instant createdDate;

	private User lastModifiedBy;
	private Instant lastModifiedDate;

	private long version;

	@Id
	@Column(name = "id")
	@GeneratedValue
	public UUID getId() {
		return id;
	}

	protected void setId(UUID id) {
		this.id = id;
	}

	@CreatedBy
	@ManyToOne
	@JoinColumn(
		name = "created_by",
		updatable = false,
		foreignKey = @ForeignKey(name = "fk_created_by")
	)
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@CreatedDate
	@Column(
		name = "created_date",
		columnDefinition = "timestamp with time zone",
		nullable = false,
		updatable = false
	)
	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	@LastModifiedBy
	@ManyToOne
	@JoinColumn(
		name = "last_modified_by",
		foreignKey = @ForeignKey(name = "fk_last_modified_by")
	)
	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@LastModifiedDate
	@Column(
		name = "last_modified_date",
		columnDefinition = "timestamp with time zone",
		nullable = false
	)
	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Version
	@Column(name = "version", nullable = false)
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Transient
	@XmlTransient
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		BaseEntity that = (BaseEntity) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

}
