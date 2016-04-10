package com.ewcms.security.organization.entity;

/**
 * 组织机构类型
 * 
 * @author wu_zhijun
 */
public enum OrganizationType {
    bloc("集团"), branch_office("分公司"), department("部门"), group("部门小组");

    private final String info;

    private OrganizationType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}