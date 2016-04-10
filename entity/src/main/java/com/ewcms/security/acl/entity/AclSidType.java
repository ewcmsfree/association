package com.ewcms.security.acl.entity;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author 吴智俊
 */
public enum AclSidType {
	role("角色"), user("用户"), user_group("用户组"), organization_group("组织机构组");
	
	private final String info;

    private AclSidType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    
    public static AclSidType valueByInfo(String info){
    	info = formatInfo(info);
		for (AclSidType aclSidType : values()) {
            if (aclSidType.getInfo().equals(info)) {
                return aclSidType;
            }
        }
		return null;
	}
    
    private static String formatInfo(String info) {
        if (StringUtils.isBlank(info)) {
            return info;
        }
        return info.trim().toLowerCase().replace("  ", " ");
    }
}
