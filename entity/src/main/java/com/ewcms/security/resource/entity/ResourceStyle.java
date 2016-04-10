package com.ewcms.security.resource.entity;

/**
 *
 * @author 吴智俊
 */
public enum ResourceStyle {
	
	accordion("手风琴"), tree("树型");

    private final String info;

    private ResourceStyle(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}