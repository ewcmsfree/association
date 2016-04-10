package com.ewcms.common.entity.search.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @author wu_zhijun
 */
public class SearchException extends NestedRuntimeException {

	private static final long serialVersionUID = -567174891454762710L;

	public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
