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
import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>aclClass:AclClass对象</li>
 * <li>objectIdIdentity:实体对象主键值</li>
 * <li>parentId:父节点编号</li>
 * <li>ownerSid:AclSid对象</li>
 * <li>entriesInheriting:是否继承父类</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "acl_object_identity", 
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_acl_object_identity_class_identity", columnNames = {"object_id_class", "object_id_identity"}) 
		}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq", sequenceName = "seq_acl_object_identity_id", allocationSize = 1)
public class AclObjectIdentity extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -2932824571233730851L;

	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = AclClass.class)
	@JoinColumn(name = "object_id_class", nullable = false)
	private AclClass aclClass;
	@Column(name = "object_id_identity", nullable = false)
	private Long objectIdIdentity;
	@Column(name = "parent_id", nullable = false)
	private Long parentId;
    @Column(name = "parent_ids")
    private String parentIds;
    @Formula(value = "(select count(*) from acl_object_identity f_t where f_t.parent_id = id)")
    private int hasChildren;
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = AclSid.class)
	@JoinColumn(name = "owner_id")
	private AclSid ownerSid;
	@Column(name = "entries_inheriting", nullable = false)
	private Boolean entriesInheriting = Boolean.TRUE;
	@Column(name = "site_id", nullable = false)
	private Long siteId;

	public AclClass getAclClass() {
		return aclClass;
	}

	public void setAclClass(AclClass aclClass) {
		this.aclClass = aclClass;
	}

	public Long getObjectIdIdentity() {
		return objectIdIdentity;
	}

	public void setObjectIdIdentity(Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    
    public AclSid getOwnerSid() {
		return ownerSid;
	}

	public void setOwnerSid(AclSid ownerSid) {
		this.ownerSid = ownerSid;
	}

	public Boolean getEntriesInheriting() {
		return entriesInheriting;
	}

	public void setEntriesInheriting(Boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}
	
    public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

    public String getSeparator() {
        return "/";
    }

    public String makeSelfAsNewParentIds() {
        return getParentIds() + getId() + getSeparator();
    }

	public boolean isHasChildren() {
    	return this.hasChildren > 0;
    }
    
    public boolean isRoot() {
        if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
    }

}
