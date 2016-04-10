package com.ewcms.security.user.web.controller.entity;

import java.io.Serializable;

/**
 *
 * @author 吴智俊
 */
public class SwitchUser implements Serializable {

	private static final long serialVersionUID = -3826313382536231141L;

	private String username;
	private String realname;
	private String opMethod;

	public SwitchUser(String username, String realname, String opMethod) {
		this.username = username;
		this.realname = realname;
		this.opMethod = opMethod;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getOpMethod() {
		return opMethod;
	}

	public void setOpMethod(String opMethod) {
		this.opMethod = opMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SwitchUser other = (SwitchUser) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
