package com.ewcms.security.permission.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 权限表
 * 
 * <ul>
 * <li>name:名称(前端显示)</li>
 * <li>permission:权限标识(系统中验证时使用)</li>
 * <li>description:详细描述</li>
 * <li>show:是否可用</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_permission",
		indexes = {
			@Index(name = "idx_sec_permission_name", columnList = "name"),
			@Index(name = "idx_sec_permission_permission", columnList = "permission"),
			@Index(name = "idx_sec_permission_show", columnList = "is_show")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_permission_id", allocationSize = 1)
public class Permission extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -1105163086967728858L;
	
	@Column(name = "name")
    private String name;
	@Column(name = "permission")
    private String permission;
	@Column(name = "description")
    private String description;
    @Column(name = "is_show")
    private Boolean show = Boolean.TRUE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}