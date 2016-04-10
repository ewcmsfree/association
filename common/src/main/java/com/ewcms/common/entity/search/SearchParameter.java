/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.entity.search;

import static org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 查询参数
 * spring mvc直接映射查询参数类，页面查询参数构造在jquery.ewcms.js中$.ewcms.query()方法。
 * 
 * <ul>
 * <li>page:当前页数</li>
 * <li>rows:当前记录数</li>
 * <li>sort:页面使用的排序列</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
public class SearchParameter<ID> {

	private int page = 1;
	private int rows = 30;
	private String sort;
	private String order;
	private Map<String, Object> parameters = Maps.newHashMap();
	private List<ID> selections = Lists.<ID>newArrayList();
	private Map<String, Direction> sorts = Maps.newLinkedHashMap();

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<ID> getSelections() {
		return selections;
	}

	public void setSelections(List<ID> selections) {
		this.selections = selections;
	}

	public Map<String, Direction> getSorts() {
		return sorts;
	}

	public void setSorts(Map<String, Direction> sorts) {
		this.sorts = sorts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + page;
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + rows;
		result = prime * result
				+ ((selections == null) ? 0 : selections.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((sorts == null) ? 0 : sorts.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchParameter<ID> other = ((SearchParameter<ID>) obj);
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (page != other.page)
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (rows != other.rows)
			return false;
		if (selections == null) {
			if (other.selections != null)
				return false;
		} else if (!selections.equals(other.selections))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (sorts == null) {
			if (other.sorts != null)
				return false;
		} else if (!sorts.equals(other.sorts))
			return false;
		return true;
	}

}
