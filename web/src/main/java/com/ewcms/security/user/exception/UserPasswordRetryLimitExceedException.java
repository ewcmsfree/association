package com.ewcms.security.user.exception;

/**
 *
 * @author 吴智俊
 */
public class UserPasswordRetryLimitExceedException extends UserException {

	private static final long serialVersionUID = 1825896370633039375L;

	public UserPasswordRetryLimitExceedException(int retryLimitCount) {
		super("user.password.retry.limit.exceed", new Object[]{retryLimitCount});
	}
}
