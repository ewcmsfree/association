package com.ewcms.personnel.currentstate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 目前情况
 * 
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author wu_zhijun
 *
 */

@Entity
@Table(name = "pel_current_state", uniqueConstraints = { @UniqueConstraint(name = "unique_pel_current_state_name", columnNames = "name") })
@SequenceGenerator(name = "seq", sequenceName = "seq_pel_current_state_id", allocationSize = 1)
public class CurrentState extends BaseSequenceEntity<Long>{

	private static final long serialVersionUID = -44690845850319443L;

	@Column(name = "name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
