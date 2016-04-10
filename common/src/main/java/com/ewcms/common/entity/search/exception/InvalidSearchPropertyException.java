package com.ewcms.common.entity.search.exception;

/**
 * @author wu_zhijun
 */
public final class InvalidSearchPropertyException extends SearchException {

	private static final long serialVersionUID = 4484147511614363801L;

	public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
        this(searchProperty, entityProperty, null);
    }

    public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
        super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
    }


}
