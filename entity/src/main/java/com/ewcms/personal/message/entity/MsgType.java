package com.ewcms.personal.message.entity;

/**
 *
 * @author 吴智俊
 */
public enum MsgType {
	GENERAL("消息"),	NOTICE("公告"),SUBSCRIPTION("订阅");
	
	private String info;

	private MsgType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
