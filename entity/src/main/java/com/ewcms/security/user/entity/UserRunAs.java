package com.ewcms.security.user.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ewcms.common.entity.AbstractEntity;

/**
 * 授予身份
 * 
 * <ul>
 * <li>id:UserRunAsPk对象</li>
 * </ul>
 *
 * @author 吴智俊
 */
@Entity
@Table(name = "sec_user_runas")
public class UserRunAs extends AbstractEntity<UserRunAsPk>{

	private static final long serialVersionUID = -1516454639153241460L;

	@EmbeddedId
	private UserRunAsPk id;

	@Override
	public UserRunAsPk getId() {
		return id;
	}

	@Override
	public void setId(UserRunAsPk id) {
		this.id = id;
	}
}