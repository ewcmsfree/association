package com.ewcms.security.permission.service;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.permission.entity.Permission;
import com.ewcms.security.permission.repository.PermissionRepository;

/**
 * @author wu_zhijun
 */
@Service
public class PermissionService extends BaseService<Permission, Long> {

    private PermissionRepository getPermissionRepository() {
        return (PermissionRepository) baseRepository;
    }

    public String findNames(Set<Long> ids){
		Set<String> names = getPermissionRepository().findNames(ids);
		return StringUtils.join(names, ",");
	}
    
    public Permission findByName(String name){
    	return getPermissionRepository().findByName(name);
    }
}
