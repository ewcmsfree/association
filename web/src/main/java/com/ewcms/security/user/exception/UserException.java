package com.ewcms.security.user.exception;

import com.ewcms.common.exception.BaseException;

/**
 *
 * @author 吴智俊
 */
public class UserException extends BaseException {

	private static final long serialVersionUID = -3101004367674161360L;

	public UserException(String code, Object[] args){
		super("user", code, args, null);
	}
}
