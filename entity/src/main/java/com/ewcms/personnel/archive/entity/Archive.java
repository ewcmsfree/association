package com.ewcms.personnel.archive.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.personnel.allowance.entity.Allowance;
import com.ewcms.personnel.currentstate.entity.CurrentState;
import com.ewcms.personnel.nation.entity.Nation;
import com.ewcms.security.organization.entity.Organization;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 专家信息表
 * 
 * <ul>
 * <li>userId:登录用户编号</li>
 * <li>organizations:所属学会集合</li>
 * <li>name:姓名</li>
 * <li>sex:性别</li>
 * <li>birthday:出生日期</li>
 * <li>located:籍贯</li>
 * <li>nation:民族</li>
 * <li>political:政治面貌</li>
 * <li>workAddress:工作单位</li>
 * <li>duties:职务</li>
 * <li>acadOffice:学会任职</li>
 * <li>titles:技术职称</li>
 * <li>isShuoDao:是否硕导</li>
 * <li>isBoDao:是否博导</li>
 * <li>postalAddress:通讯地址</li>
 * <li>zipCode:邮政编码</li>
 * <li>officePhone:办公电话</li>
 * <li>fax:传真</li>
 * <li>mobilePhoneNumber:手机(通过注册用户查询)<li>
 * <li>email:邮箱(通过注册用户查询)</li>
 * <li>nowProfessional:现从事专业</li>
 * <li>specialty:特长专业</li>
 * <li>learningSituation:进修情况（国内、国外）</li>
 * <li>jobStatus:参加全国、国际学术组织（机构）及任职情况<li>
 * <li>isAllowance:享受政府津贴情况</li>
 * <li>allowances:享受政府津巾对象集合</li>
 * <li>foreignLevel:熟悉何种外语及熟练程度</li>
 * <Li>jobResume:工作简历</li>
 * <li>situationResume:主要科研成果、论著、论文及获奖情况</li>
 * <li>currentStates:目前情况</li>
 * <li>createTime:创建时间</li>
 * <li>deleted:是否锁定</li>
 * <li>status:当前状态</li>
 * <li>isVoyage:是否享受远航工程资助</li>
 * <li>voyageYear:享受远航工程资助年份</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */

@Entity
@Table(name = "pel_archive", uniqueConstraints = {@UniqueConstraint(name = "unique_pel_archive_user_id", columnNames = "user_id")})
@SequenceGenerator(name="seq", sequenceName="seq_pel_archive_id", allocationSize = 1)
public class Archive extends BaseSequenceEntity<Long> implements LogicDeleteable{

	private static final long serialVersionUID = -2099383100016556054L;
	
	@NotNull(message = "{not.null}")
	@Column(name = "user_id")
	private Long userId;
	@NotEmpty(message = "{not.null}")
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = Organization.class)
	@JoinTable(name = "pel_archive_organization", joinColumns = @JoinColumn(name = "archive_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
	private List<Organization> organizations = Lists.newArrayList();
	@NotEmpty(message = "{not.null}")
	@Column(name = "name")
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex = Sex.male;
	@NotNull(message = "{not.null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	private Date birthday;
	@NotEmpty(message = "{not.null}")
	@Column(name = "located")
	private String located;
	@NotNull(message = "{not.null}")
	@OneToOne(cascade = {CascadeType.REFRESH}, targetEntity = Nation.class)
	@JoinColumn(name = "nation_id")
	private Nation nation;
	@NotNull(message = "{not.null}")
	@Column(name = "political")
	private String political;
	@Column(name = "work_address")
	private String workAddress;
	@Column(name = "duties")
	private String duties;
	@Column(name = "acad_office")
	private String acadOffice;
	@NotEmpty(message = "{not.null}")
	@Column(name = "titles")
	private String titles;
	@Column(name = "is_shuodao")
	private Boolean isShuoDao;
	@Column(name = "is_bodao")
	private Boolean isBoDao;
	@NotEmpty(message = "{not.null}")
	@Column(name = "postal_address")
	private String postalAddress;
	@NotEmpty(message = "{not.null}")
	@Column(name = "zip_code")
	private String zipCode;
	@Column(name = "office_phone")
	private String officePhone;
	@Column(name = "fax")
	private String fax;
    @Formula(value = "(select s_o.mobile_phone_number from sec_user s_o where s_o.id = user_id)")
    private String mobilePhoneNumber;
    @Formula(value = "(select s_o.email from sec_user s_o where s_o.id = user_id)")
    private String email;
    @NotEmpty(message = "{not.null}")
    @Column(name = "now_professional", columnDefinition = "text")
    private String nowProfessional;
    @NotEmpty(message = "{not.null}")
    @Column(name = "specialty", columnDefinition = "text")
    private String specialty;
    @Column(name = "learning_situation", columnDefinition = "text")
    private String learningSituation;
    @Column(name = "job_status", columnDefinition = "text")
    private String jobStatus;
	@Enumerated(EnumType.STRING)
    @Column(name = "allowance")
    private AllowanceEnum isAllowance = AllowanceEnum.FALSE; 
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = Allowance.class)
	@JoinTable(name = "pel_archive_allowance", joinColumns = @JoinColumn(name = "archive_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "allowance_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
    private Set<Allowance> allowances = Sets.newHashSet();
    @Column(name = "goreign_level")
    private String foreignLevel;
    @NotEmpty(message = "{not.null}")
    @Column(name = "job_resume", columnDefinition = "text")
    private String jobResume;
    @Column(name = "situation_resume", columnDefinition = "text")
    private String situationResume;
    @NotEmpty(message = "{not.null}")
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = CurrentState.class)
	@JoinTable(name = "pel_archive_current_state", joinColumns = @JoinColumn(name = "archive_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "currentState_id", referencedColumnName = "id"))
	@OrderBy(value = "id")
    private Set<CurrentState> currentStates = Sets.newHashSet();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time", columnDefinition = "Timestamp default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArchiveStatus status = ArchiveStatus.useredit;
    @Column(name = "is_voyage")
    private Boolean isVoyage = Boolean.FALSE;
    @Column(name = "voyage_year")
    private String voyageYear;
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public String getSexInfo(){
		return (sex != null) ? sex.getInfo() : Sex.male.getInfo();
	}

	@JSONField(format = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLocated() {
		return located;
	}

	public void setLocated(String located) {
		this.located = located;
	}

	@JSONField(serialize = false)
	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getAcadOffice() {
		return acadOffice;
	}

	public void setAcadOffice(String acadOffice) {
		this.acadOffice = acadOffice;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public Boolean getIsShuoDao() {
		return isShuoDao;
	}

	public void setIsShuoDao(Boolean isShuoDao) {
		this.isShuoDao = isShuoDao;
	}

	public Boolean getIsBoDao() {
		return isBoDao;
	}

	public void setIsBoDao(Boolean isBoDao) {
		this.isBoDao = isBoDao;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNowProfessional() {
		return nowProfessional;
	}

	public void setNowProfessional(String nowProfessional) {
		this.nowProfessional = nowProfessional;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getLearningSituation() {
		return learningSituation;
	}

	public void setLearningSituation(String learningSituation) {
		this.learningSituation = learningSituation;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public AllowanceEnum getIsAllowance() {
		return isAllowance;
	}

	public void setIsAllowance(AllowanceEnum isAllowance) {
		this.isAllowance = isAllowance;
	}

	public String getAllowanceInfo(){
		return (isAllowance != null) ? isAllowance.getInfo() : AllowanceEnum.FALSE.getInfo();
	}
	
	@JSONField(serialize = false)
	public Set<Allowance> getAllowances() {
		return allowances;
	}

	public void setAllowances(Set<Allowance> allowances) {
		this.allowances = allowances;
	}

	public String getForeignLevel() {
		return foreignLevel;
	}

	public void setForeignLevel(String foreignLevel) {
		this.foreignLevel = foreignLevel;
	}

	public String getJobResume() {
		return jobResume;
	}

	public void setJobResume(String jobResume) {
		this.jobResume = jobResume;
	}

	public String getSituationResume() {
		return situationResume;
	}

	public void setSituationResume(String situationResume) {
		this.situationResume = situationResume;
	}

	@JSONField(serialize = false)
	public Set<CurrentState> getCurrentStates() {
		return currentStates;
	}

	public void setCurrentStates(Set<CurrentState> currentStates) {
		this.currentStates = currentStates;
	}
	
	public String getCurrentStateNames() {
		return (EmptyUtil.isCollectionNotEmpty(currentStates)) ? Collections3.extractToString(currentStates, "name", ",") : "";
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ArchiveStatus getStatus() {
		return status;
	}

	public void setStatus(ArchiveStatus status) {
		this.status = status;
	}

    public String getStatusInfo(){
    	return status == null ? "" : status.getInfo();
    }

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
	
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }

	@JSONField(serialize = false)
	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getOrganizationIds() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToList(organizations, "id") : Lists.newArrayList();
	}

	public String getOrganizationNames() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToString(organizations, "name", ",") : "";
	}

	public Boolean getIsVoyage() {
		return isVoyage;
	}

	public void setIsVoyage(Boolean isVoyage) {
		this.isVoyage = isVoyage;
	}

	public String getVoyageYear() {
		return voyageYear;
	}

	public void setVoyageYear(String voyageYear) {
		this.voyageYear = voyageYear;
	}
}
