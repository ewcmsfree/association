package com.ewcms.security.group.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 分组与用户/组织机构关系表
 * <br/>
 * 将用户/组织机构放一张表目的是提高查询性能
 * 
 * <ul>
 * <li>groupId:组编号</li>
 * <li>organizationId:组织机构编号</li>
 * <li>organizationName:组织机构名称(不作为数据字段)</li>
 * <li>userId:用户编号</li>
 * <li>username:用户名(不作为数据字段)</li>
 * <li>realname:实名(不作为数据字段)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_group_relation",
		indexes = {
			@Index(name = "idx_sec_group_relation_group", columnList = "group_id"),
			@Index(name = "idx_sec_group_relation_organiztion", columnList = "organization_id"),
			@Index(name = "idx_sec_group_relation_user", columnList = "user_id")
		}
)
//@EnableQueryCache
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_group_relation_id", allocationSize = 1)
public class GroupRelation extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 4355431792271654413L;

	@Column(name = "group_id")
    private Long groupId;
    @Column(name = "organization_id")
    private Long organizationId;
    @Formula(value = "(select s_o.name from sec_organization s_o where s_o.id = organization_id)")
    private String organizationName;
    @Column(name = "user_id")
    private Long userId;
    @Formula(value = "(select s_u.username from sec_user s_u where s_u.id = user_id)")
    private String username;
//    @Formula(value = "(select s_u.real_name from sec_user s_u where s_u.id = user_id)")
//    private String realname;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

	public String getOrganizationName() {
		return organizationName;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getUsername() {
		return username;
	}

//	public String getRealname() {
//		return realname;
//	}
}