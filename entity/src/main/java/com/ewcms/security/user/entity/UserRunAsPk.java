package com.ewcms.security.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 授予身份主键
 * 
 * <ul>
 * <li>fromUserId:从用户编号</li>
 * <li>toUserId:到用户编号</li>
 * </ul>
 *
 * @author 吴智俊
 */
@Embeddable
public class UserRunAsPk implements Serializable {

	private static final long serialVersionUID = 1509868656402585509L;

	@Column(name = "from_user_id")
	private Long fromUserId;
	@Column(name = "to_user_id")
	private Long toUserId;

	public UserRunAsPk() {
	}
	
	public UserRunAsPk(Long fromUserId, Long toUserId){
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
	}
	
	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fromUserId == null) ? 0 : fromUserId.hashCode());
		result = prime * result
				+ ((toUserId == null) ? 0 : toUserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRunAsPk other = (UserRunAsPk) obj;
		if (fromUserId == null) {
			if (other.fromUserId != null)
				return false;
		} else if (!fromUserId.equals(other.fromUserId))
			return false;
		if (toUserId == null) {
			if (other.toUserId != null)
				return false;
		} else if (!toUserId.equals(other.toUserId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRunAs{" + 
				"fromUserId=" + fromUserId + 
				", toUserId=" + toUserId + 
				"}";
	}
}