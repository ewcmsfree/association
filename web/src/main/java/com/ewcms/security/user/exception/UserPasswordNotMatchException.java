package com.ewcms.security.user.exception;

/**
 *
 * @author 吴智俊
 */
public class UserPasswordNotMatchException extends UserException {

	private static final long serialVersionUID = 4086555439032247931L;

	public UserPasswordNotMatchException(){
		super("user.password.not.match", null);
	}
}
