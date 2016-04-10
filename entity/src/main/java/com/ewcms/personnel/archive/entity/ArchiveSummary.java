package com.ewcms.personnel.archive.entity;

import java.io.Serializable;

/**
 * 登记表总述
 * 
 * @author wu_zhijun
 *
 */
public class ArchiveSummary implements Serializable {

	private static final long serialVersionUID = -6549241373645272381L;

	private Long organizationId;
	private String organizationName;
	private Long total;
	private Long throughCount;

	public ArchiveSummary(Long total, Long throughCount){
		this.total = total;
		this.throughCount = throughCount;
	}
	
	public ArchiveSummary(Long organizationId, String organizationName, Long total, Long throughCount) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.total = total;
		this.throughCount = throughCount;
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

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getThroughCount() {
		return throughCount;
	}

	public void setThroughCount(Long throughCount) {
		this.throughCount = throughCount;
	}
}
