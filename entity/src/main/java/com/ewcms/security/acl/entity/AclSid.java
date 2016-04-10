package com.ewcms.security.acl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>sid:认证对象(一个用户名或角色名或用户组或机构组)</li>
 * <li>type:AclSidType对象</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "acl_sid", 
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_acl_sid_sid_type", columnNames = {"sid", "type"}) 
		}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq", sequenceName = "seq_acl_sid_id", allocationSize = 1)
public class AclSid extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 113298141547371439L;

	@Column(name = "sid", nullable = false)
	private Long sid;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private AclSidType type;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public AclSidType getType() {
		return type;
	}

	public void setType(AclSidType type) {
		this.type = type;
	}
}
