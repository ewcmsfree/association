package com.ewcms.security.permission.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.hibernate.type.CollectionToStringUserType;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;
import com.google.common.collect.Sets;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.Set;

/**
 * 
 * 角色/资源/权限关联表
 * 此处没有使用关联是为了提高性能（后续会挨着查询资源和权限列表，因为有缓存，数据量也不是很大所以性能不会差）
 * 
 * <ul>
 * <li>role:角色对象</li>
 * <li>resourceId:资源编号</li>
 * <li>resourceName:资源名称(不作为数据字段)</li>
 * <li>permissionIds:权限编号列表(数据库通过字符串存储逗号分隔)</li>
 * <li>permissionNames:权限名称列表(不作为数据字段)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */

@TypeDef(
        name = "SetToStringUserType",
        typeClass = CollectionToStringUserType.class,
        parameters = {
                @Parameter(name = "separator", value = ","),
                @Parameter(name = "collectionType", value = "java.util.HashSet"),
                @Parameter(name = "elementType", value = "java.lang.Long")
        }
)
@Entity
@Table(name = "sec_role_resource_permission",
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_sec_role_resource_permission", columnNames = {"role_id", "resource_id"})
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_role_resource_permission_id", allocationSize = 1)
public class RoleResourcePermission extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 7135436090519483442L;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Role role;
    @Column(name = "resource_id")
    private Long resourceId;
    @Formula(value = "(select s_o.name from sec_resource s_o where s_o.id = resource_id)")
    private String resourceName;
    @NotNull(message = "{not.null}")
    @Column(name = "permission_ids")
    @Type(type = "SetToStringUserType")
    private Set<Long> permissionIds;
    @Transient
    private String permissionNames;

    public RoleResourcePermission() {
    }

    public RoleResourcePermission(Long id) {
        setId(id);
    }

    public RoleResourcePermission(Long resourceId, Set<Long> permissionIds) {
        this.resourceId = resourceId;
        this.permissionIds = permissionIds;
    }

    @JSONField(serialize = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@JSONField(serialize = false)
	public Set<Long> getPermissionIds() {
        if (permissionIds == null) {
            permissionIds = Sets.newHashSet();
        }
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public String getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(String permissionNames) {
		this.permissionNames = permissionNames;
	}

	@Override
    public String toString() {
        return "RoleResourcePermission{id=" + this.getId() +
                ",roleId=" + (role != null ? role.getId() : "null") +
                ", resourceId=" + resourceId +
                ", permissionIds=" + permissionIds +
                '}';
    }
}