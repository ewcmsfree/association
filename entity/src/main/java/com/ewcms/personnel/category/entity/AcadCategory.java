package com.ewcms.personnel.category.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.organization.entity.Organization;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author zhoudongchu
 */
@Entity
@Table(name = "pel_acadcategory", uniqueConstraints = { @UniqueConstraint(name = "unique_pel_acadcategory_name", columnNames = "name") })
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_acadcategory_id", allocationSize = 1)
public class AcadCategory extends BaseSequenceEntity<Long> {
	
	private static final long serialVersionUID = -5116041484672386895L;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Transient
	private String selectOrganizationIds;
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = Organization.class)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	// 集合缓存
	@OrderBy()
	private List<Organization> organizations = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getSelectOrganizationIds() {
		return selectOrganizationIds;
	}

	public void setSelectOrganizationIds(String selectOrganizationIds) {
		this.selectOrganizationIds = selectOrganizationIds;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getOrganizationIds() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToList(organizations, "id") : Lists.newArrayList();
	}

	public String getOrganizationNames() {
		return (EmptyUtil.isCollectionNotEmpty(organizations)) ? Collections3.extractToString(organizations, "name", ",") : "";
	}
}
