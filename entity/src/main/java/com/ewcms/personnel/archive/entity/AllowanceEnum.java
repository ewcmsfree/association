package com.ewcms.personnel.archive.entity;

public enum AllowanceEnum {
	TRUE("享受"), FALSE("不享受");

    private final String info;

    private AllowanceEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
