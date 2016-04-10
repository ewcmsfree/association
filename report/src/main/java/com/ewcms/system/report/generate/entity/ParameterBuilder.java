package com.ewcms.system.report.generate.entity;

import java.io.Serializable;
import java.util.Map;

import com.ewcms.system.report.entity.TextReport;

/**
 * @author 吴智俊
 */
public class ParameterBuilder implements Serializable {

	private static final long serialVersionUID = -6953111348682302225L;

	private TextReport.Type textType;
	private Map<String, String> paramMap;

	public TextReport.Type getTextType() {
		return textType;
	}

	public void setTextType(TextReport.Type textType) {
		this.textType = textType;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}
