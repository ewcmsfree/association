package com.ewcms.system.report.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "sys_report_repository")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_report_repository_id", allocationSize = 1)
public class Repository extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -1166379145505781440L;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	@Column(name = "entity")
	private byte[] entity;
	@Column(name = "type")
	private String type;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updatedate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "publishdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSONField(serialize = false)
	public byte[] getEntity() {
		return entity;
	}

	public void setEntity(byte[] entity) {
		this.entity = entity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
}
