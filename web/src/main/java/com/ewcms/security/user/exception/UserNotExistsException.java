package com.ewcms.security.user.exception;

/**
 *
 * @author 吴智俊
 */
public class UserNotExistsException extends UserException {

	private static final long serialVersionUID = 3554641057062544061L;

	public UserNotExistsException() {
		super("user.not.exists", null);
	}
}
