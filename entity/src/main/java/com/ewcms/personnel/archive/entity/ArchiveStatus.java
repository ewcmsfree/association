package com.ewcms.personnel.archive.entity;

public enum ArchiveStatus {
	
    useredit("修改信息"), submitthrough("提交审核"), through("审核通过"), nothrough("审核不通过");

    private final String info;

    private ArchiveStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
