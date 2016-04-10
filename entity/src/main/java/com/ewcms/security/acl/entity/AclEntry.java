package com.ewcms.security.acl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * <ul>
 * <li>aclObjectIdentity:AclObjectIdentity对象</li>
 * <li>aceOrder:排序</li>
 * <li>aclSid:AclSid对象</li>
 * <li>mask:操作权限掩码</li>
 * <li>granting:权限被授权或拒绝</li>
 * <li>auditSuccess:是否审核成功</li>
 * <li>auditFailure:是否审核失败</li>
 * </ul>
 * @author 吴智俊
 */
@Entity
@Table(name = "acl_entry", 
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_acl_entry_identity_order", columnNames = {"acl_object_identity", "ace_order"}) 
		}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq", sequenceName = "seq_acl_entry_id", allocationSize = 1)
public class AclEntry extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -5801709810867325542L;

	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = AclObjectIdentity.class)
	@JoinColumn(name = "acl_object_identity", nullable = false)
	private AclObjectIdentity aclObjectIdentity;
	@Column(name = "ace_order", nullable = false)
	private Integer aceOrder;
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = AclSid.class)
	@JoinColumn(name = "sid", nullable = false)
	private AclSid aclSid;
	@Column(name = "mask", nullable = false)
	private Integer mask;
	@Column(name = "granting", nullable = false)
	private Boolean granting = true;
	@Column(name = "audit_success", nullable = false)
	private Boolean auditSuccess = true;
	@Column(name = "audit_failure", nullable = false)
	private Boolean auditFailure = false;

	public AclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public Integer getAceOrder() {
		return aceOrder;
	}

	public void setAceOrder(Integer aceOrder) {
		this.aceOrder = aceOrder;
	}

	public AclSid getAclSid() {
		return aclSid;
	}

	public void setAclSid(AclSid aclSid) {
		this.aclSid = aclSid;
	}

	public Integer getMask() {
		return mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public Boolean getGranting() {
		return granting;
	}

	public void setGranting(Boolean granting) {
		this.granting = granting;
	}

	public Boolean getAuditSuccess() {
		return auditSuccess;
	}

	public void setAuditSuccess(Boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public Boolean getAuditFailure() {
		return auditFailure;
	}

	public void setAuditFailure(Boolean auditFailure) {
		this.auditFailure = auditFailure;
	}
}
