package com.ewcms.personnel.photo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

@Entity
@Table(name = "pel_archive_photo", uniqueConstraints = {@UniqueConstraint(name = "unique_pel_archive_photo_user_id", columnNames = "user_id")})
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_archive_photo", allocationSize = 1)
public class Photo extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 6110254229044427156L;
	
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "real")
	private byte[] real;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_date", columnDefinition = "Timestamp default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	@Column(name = "format_name")
	private String formatName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JSONField(serialize = false)
	public byte[] getReal() {
		return real;
	}

	public void setReal(byte[] real) {
		this.real = real;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
}
