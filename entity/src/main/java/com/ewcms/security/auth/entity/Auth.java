package com.ewcms.security.auth.entity;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.hibernate.type.CollectionToStringUserType;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;
import com.google.common.collect.Sets;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.util.Set;

/**
 * 授权
 * 
 * 组织机构/工作职位/用户/角色之间的关系表<br/>
 * <ul>
 * 1、授权的五种情况
 * <li>只给组织机构授权 (orgnizationId=? and jobId=0)</li>
 * <li>只给工作职务授权 (orgnizationId=0 and jobId=?)</li>
 * <li>给组织机构和工作职务都授权 (orgnizationId=? and jobId=?)</li>
 * <li>给用户授权 (userId=?)</li>
 * <li>给组授权 (groupId=?)</li>
 * <br/>
 * 因此查询用户有没有权限就是 where (orgnizationId=? and jobId=0) or (organizationId = 0 and
 * jobId=?) or (orgnizationId=? and jobId=?) or (userId=?) or (groupId=?)
 * </ul>
 * <ul>
 * 2、为了提高性能,放到一张表,此处不做关系映射（这样需要配合缓存）
 * </ul>
 * <ul>
 * 3、如果另一方是可选的（如只选组织机构 或 只选工作职务）,那么默认0,使用0的目的是为了也让走索引
 * </ul>
 * 
 * <ul>
 * <li>organizationId:组织机构编号</li>
 * <li>organizationName:组织机构名称(不作为数据字段)</li>
 * <li>jobId:工作职务编号</li>
 * <li>jobName:工作职务名称(不作为数据字段)</li>
 * <li>userId:用户编号</li>
 * <li>username:用户名(不作为数据字段)</li>
 * <li>groupId:组编号</li>
 * <li>groupName:组名称(不作为数据字段)</li>
 * <li>roleIds:角色编号列表</li>
 * <li>type:授权类型</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@TypeDef(name = "SetToStringUserType", typeClass = CollectionToStringUserType.class, parameters = {
		@Parameter(name = "separator", value = ","),
		@Parameter(name = "collectionType", value = "java.util.HashSet"),
		@Parameter(name = "elementType", value = "java.lang.Long") })
@Entity
@Table(name = "sec_auth",
		indexes = {
			@Index(name = "idx_sec_auth_organization", columnList = "organization_id"),
			@Index(name = "idx_sec_auth_job", columnList = "job_id"),
			@Index(name = "idx_sec_auth_user", columnList = "user_id"),
			@Index(name = "idx_sec_auth_group", columnList = "group_id"),
			@Index(name = "idx_sec_auth_type", columnList = "type")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq", sequenceName = "seq_sec_auth_id", allocationSize = 1)
public class Auth extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 3145983208046266292L;

	@Column(name = "organization_id")
	private Long organizationId = 0L;
	@Formula(value = "(select s_o.name from sec_organization s_o where s_o.id = organization_id)")
	private String organizationName;
	@Column(name = "job_id")
	private Long jobId = 0L;
	@Formula(value = "(select s_o.name from sec_job s_o where s_o.id = job_id)")
	private String jobName;
	@Column(name = "user_id")
	private Long userId = 0L;
	@Formula(value = "(select s_o.username || case when s_p.name is not null then ('(' || s_p.name || ')') else '' end from sec_user s_o left join pel_archive s_p on s_o.id = s_p.user_id where s_o.id=user_id)")
	private String userName;
	@Column(name = "group_id")
	private Long groupId = 0L;
	@Formula(value = "(select s_o.name from sec_group s_o where s_o.id = group_id)")
	private String groupName;
	@Type(type = "SetToStringUserType")
	@Column(name = "role_ids")
	private Set<Long> roleIds;
	@Transient
	private String roleNames;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private AuthType type;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public Set<Long> getRoleIds() {
		return (roleIds == null) ? Sets.<Long> newHashSet() : roleIds;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public AuthType getType() {
		return type;
	}

	public void setType(AuthType type) {
		this.type = type;
	}

	public String getTypeInfo() {
		return type == null ? "" : type.getInfo();
	}
}