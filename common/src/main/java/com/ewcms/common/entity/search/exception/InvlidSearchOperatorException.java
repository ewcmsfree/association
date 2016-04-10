package com.ewcms.common.entity.search.exception;

import com.ewcms.common.entity.search.SearchOperator;

/**
 * @author wu_zhijun
 */
public final class InvlidSearchOperatorException extends SearchException {

	private static final long serialVersionUID = 1684342455573926124L;

	public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
