package com.ewcms.security.acl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 对象路径
 * 
 * <ul>
 * <li>className:对象完全路径</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "acl_class")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_acl_class_id", allocationSize = 1)
public class AclClass extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -8580948602699535028L;

	@Column(name = "class", nullable = false, columnDefinition = "text", unique = true)
	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
