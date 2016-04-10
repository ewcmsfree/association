package com.ewcms.personnel.archive.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 学习经历
 * 
 * <ul>
 * <li>universityName:就读院校名称</li>
 * <li>major:所学专业</li>
 * <li>graduationDate:毕业时间</li>
 * <li>education:学历</li>
 * <li>degree:学位</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */

@Entity
@Table(name = "pel_education_experience")
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_education_experience_id", allocationSize = 1)
public class EducationExperience extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 7173811738728987938L;

	@NotNull(message = "{not.null}")
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@NotEmpty(message = "{not.null}")
	@Column(name = "university_name")
	private String universityName;
	@NotEmpty(message = "{not.null}")
	@Column(name = "major")
	private String major;
	@NotNull(message = "{not.null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "graduation_date")
	private Date graduationDate;
	@NotEmpty(message = "{not.null}")
	@Column(name = "education")
	private String education;
	@NotEmpty(message = "{not.null}")
	@Column(name = "degree")
	private String degree;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@JSONField(format = "yyyy-MM-dd")
	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
