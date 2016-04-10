package com.ewcms.security.permission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.security.permission.entity.RoleResourcePermission;
import com.ewcms.security.permission.repository.RoleResourcePermissionRepository;
import com.ewcms.security.resource.entity.Resource;
import com.ewcms.security.resource.service.ResourceService;

/**
 * @author wu_zhijun
 */
@Service
public class RoleResourcePermissionService extends BaseService<RoleResourcePermission, Long> {
	
	private RoleResourcePermissionRepository getRoleResourcePermissionRepository(){
		return (RoleResourcePermissionRepository) baseRepository;
	}
	
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;
    
    @Override
    public RoleResourcePermission save(RoleResourcePermission m){
    	RoleResourcePermission dbRoleResourcePermission = getRoleResourcePermissionRepository().findByRoleAndResourceId(m.getRole(), m.getResourceId());
    	if (dbRoleResourcePermission != null){
    		m.setId(dbRoleResourcePermission.getId());
    	}
    	return super.save(m);
    }
    
    /**
     * 查询资源和权限的全名
     * 
     * @param searchable
     * @param separator 分格符
     * @return
     */
    public Map<String, Object> findRoleResourcePermissionFullNames(Searchable searchable, String separator){
    	Page<RoleResourcePermission> pages = findAll(searchable);
    	
    	List<RoleResourcePermission> roleResourcePermissions = pages.getContent();
    	//if (EmptyUtil.isCollectionEmpty(roleResourcePermissions)) return Lists.newArrayList();
    	
    	for (RoleResourcePermission roleResourcePermission : roleResourcePermissions){
//    	for (int i = 0; i <= roleResourcePermissions.size() - 1; i++){
//    		RoleResourcePermission userOrganizationJob = roleResourcePermissions.get(i);
    		
    		Resource resource = resourceService.findOne(roleResourcePermission.getResourceId());
			if (resource != null){
				roleResourcePermission.setResourceName(resourceService.findNames(resource.getName(), resource.getParentIds(), false, separator));
			}
			
			String permissionNames = permissionService.findNames(roleResourcePermission.getPermissionIds());
			roleResourcePermission.setPermissionNames(permissionNames);
			
//			roleResourcePermissions.set(i, userOrganizationJob);
    	}
    	
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", pages.getTotalElements());
		resultMap.put("rows", roleResourcePermissions);

    	return resultMap;
    }
}
