package com.ewcms.security.user.exception;

/**
 *
 * @author 吴智俊
 */
public class UserBlockedException extends UserException {

	private static final long serialVersionUID = 5899898178720844006L;

	public UserBlockedException(String reason){
		super("user.blocked", new Object[]{reason});
	}
}
