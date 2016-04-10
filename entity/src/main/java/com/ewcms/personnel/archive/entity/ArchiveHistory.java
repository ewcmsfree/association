package com.ewcms.personnel.archive.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

@Entity
@Table(name = "pel_archive_history")
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_archive_history_id", allocationSize = 1)
public class ArchiveHistory extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 7214116805144754362L;

	@Column(name = "archive_id")
	private Long archiveId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArchiveStatus status;
	@Column(name = "reason")
	private String reason;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "op_date", columnDefinition = "Timestamp default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date opDate;

	public Long getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(Long archiveId) {
		this.archiveId = archiveId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDate() {
		return opDate;
	}
}
