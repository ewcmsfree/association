package com.ewcms.security.group.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;

import javax.persistence.*;

/**
 * 分组超类
 * 
 * <ul>
 * <li>name:分组名称</li>
 * <li>type:分组类型</li>
 * <li>defaultGroup:是否是默认分组</li>
 * <li>show:是否显示/可用</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_group",
		indexes = {
			@Index(name = "idx_sec_group_type", columnList = "type"),
			@Index(name = "idx_sec_group_show", columnList = "is_show"),
			@Index(name = "idx_sec_group_default_group", columnList = "default_group")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_group_id", allocationSize = 1)
public class Group extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -3364647548484984722L;

	@Column(name = "name", unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GroupType type;
    @Column(name = "default_group")
    private Boolean defaultGroup = Boolean.FALSE;
    @Column(name = "is_show")
    private Boolean show = Boolean.TRUE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getTypeInfo(){
    	return type == null ? "" : type.getInfo();
    }
    
    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}