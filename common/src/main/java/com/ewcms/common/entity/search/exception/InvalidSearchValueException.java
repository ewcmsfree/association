package com.ewcms.common.entity.search.exception;

/**
 * @author wu_zhijun
 */
public final class InvalidSearchValueException extends SearchException {

	private static final long serialVersionUID = 8132467923794353246L;

	public InvalidSearchValueException(String searchProperty, String entityProperty, Object value) {
        this(searchProperty, entityProperty, value, null);
    }

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value, Throwable cause) {
        super("Invalid Search Value, searchProperty [" + searchProperty + "], " +
                "entityProperty [" + entityProperty + "], value [" + value + "]", cause);
    }

}
