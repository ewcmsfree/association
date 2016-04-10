package com.ewcms.security.user.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 为了提高连表性能 使用数据冗余 而不是 组织机构(1)-----(*)职务的中间表
 * 即在该表中 用户--组织机构--职务 是唯一的  但 用户-组织机构可能重复
 * 
 * <ul>
 * <li>user:用户对象</li>
 * <li>organizationId:组织机构编号</li>
 * <li>organizationName:组织机构名称</li>
 * <li>jobId:工作职务编号</li>
 * <li>jobName:工作职务名称</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_user_organization_job",
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_sys_user_organization_job", columnNames = {"user_id", "organization_id", "job_id"})
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_user_organization_job_id", allocationSize = 1)
public class UserOrganizationJob extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -6943978780504218113L;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private User user;
	@NotNull(message = "{not.null}")
    @Column(name = "organization_id")
    private Long organizationId;
    @Formula(value = "(select s_o.name from sec_organization s_o where s_o.id = organization_id)")
    private String organizationName;
    @NotNull(message = "{not.null}")
    @Column(name = "job_id")
    private Long jobId;
    @Formula(value = "(select s_j.name from sec_job s_j where s_j.id = job_id)")
    private String jobName;

    public UserOrganizationJob() {}

    public UserOrganizationJob(Long id) {
        setId(id);
    }

    public UserOrganizationJob(Long organizationId, Long jobId) {
        this.organizationId = organizationId;
        this.jobId = jobId;
    }

    @JSONField(serialize = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getOrganizationName() {
		return organizationName;
	}
    
	public String getJobName() {
		return jobName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
    public String toString() {
        return "UserOrganizationJob{id = " + this.getId() +
                ",organizationId=" + organizationId +
                ", jobId=" + jobId +
                ", userId=" + (user != null ? user.getId() : "null") +
                '}';
    }
}