package com.ewcms.personnel.allowance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 津贴
 * 
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */

@Entity
@Table(name = "pel_allowance", uniqueConstraints = {@UniqueConstraint(name = "unique_pel_allowance_name", columnNames = "name")})
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_allowance_id", allocationSize = 1)
public class Allowance extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = 386998205475789606L;

	@Column(name = "name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
