package com.ewcms.security.permission.entity;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;
import com.google.common.collect.Lists;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.List;

/**
 * 角色表
 * 
 * <ul>
 * <li>name:名称(前端显示)</li>
 * <li>role:角色标识(系统中验证使用)</li>
 * <li>description:详细描述</li>
 * <li>resourcePermissions:用户/组织机构/工作职务关联</li>
 * <li>show:是否可用</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_role",
		indexes = {
			@Index(name = "idx_sec_role_name", columnList = "name"),
			@Index(name = "idx_sec_role_role", columnList = "role"),
			@Index(name = "idx_sec_role_show", columnList = "is_show")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_role_id", allocationSize = 1)
public class Role extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 2134368242336485963L;
	
	@Column(name = "name")
    private String name;
	@Column(name = "role")
    private String role;
	@Column(name = "description")
    private String description;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = RoleResourcePermission.class, mappedBy = "role", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
    @OrderBy
    private List<RoleResourcePermission> resourcePermissions;
    @Column(name = "is_show")
    private Boolean show = Boolean.TRUE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleResourcePermission> getResourcePermissions() {
    	return (resourcePermissions == null) ? Lists.<RoleResourcePermission>newArrayList() : resourcePermissions;
    }

    public void setResourcePermissions(List<RoleResourcePermission> resourcePermissions) {
        this.resourcePermissions = resourcePermissions;
    }

    public void addResourcePermission(RoleResourcePermission roleResourcePermission) {
        roleResourcePermission.setRole(this);
        getResourcePermissions().add(roleResourcePermission);
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}