package com.ewcms.personnel.nation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 民族
 * 
 * <li>name:名称</li>
 * 
 * @author wu_zhijun
 */

@Entity
@Table(name = "pel_nation", uniqueConstraints = { @UniqueConstraint(name = "unique_pel_nation_name", columnNames = "name") })
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_nation_id", allocationSize = 1)
public class Nation extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 3535761977387920948L;

	@Column(name = "name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
