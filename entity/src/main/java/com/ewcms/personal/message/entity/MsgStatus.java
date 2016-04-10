/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.personal.message.entity;

/**
 * 
 * @author wu_zhijun
 *
 */
public enum MsgStatus {
	FAVORITE("收藏"),TRASH("垃圾");
	
	private String info;

	private MsgStatus(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
