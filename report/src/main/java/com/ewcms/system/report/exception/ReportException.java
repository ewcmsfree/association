package com.ewcms.system.report.exception;

import com.ewcms.common.exception.BaseException;

/**
 *
 * @author 吴智俊
 */
public class ReportException extends BaseException {

	private static final long serialVersionUID = -1153311318717256864L;

	public ReportException(String code, Object[] args) {
        super("report", code, args, null);
    }

}