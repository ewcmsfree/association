package com.ewcms.security.permission.service;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.security.permission.entity.Permission;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.entity.RoleResourcePermission;
import com.ewcms.security.permission.repository.RoleRepository;
import com.ewcms.security.resource.entity.Resource;
import com.ewcms.security.resource.service.ResourceService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class RoleService extends BaseService<Role, Long> {

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleResourcePermissionService roleResourcePermissionService;
	@Autowired
	private ResourceService resourceService;
	
    public RoleRepository getRoleRepository() {
        return (RoleRepository) baseRepository;
    }

    @Override
    public Role update(Role role) {
    	Role dbRole = findOne(role.getId());
    	role.setResourcePermissions(dbRole.getResourcePermissions());
    	
    	return super.update(role);
    }

    /**
     * 获取可用的角色列表
     *
     * @param roleIds
     * @return
     */
    public Set<Role> findShowRoles(Set<Long> roleIds) {
    	if (EmptyUtil.isCollectionEmpty(roleIds)){
    		return Sets.newHashSet();
    	} else {
    		return getRoleRepository().findShowRoles(roleIds);
    	}
    }

    public List<ComboBox> findPermission(Long id){
    	Set<Long> permissionIds = Sets.newHashSet();
		if (id != null){
			Role role = findOne(id);
			List<RoleResourcePermission> roleResourcePermissions = role.getResourcePermissions();
			for (RoleResourcePermission roleResourcePermission : roleResourcePermissions){
				permissionIds.addAll(roleResourcePermission.getPermissionIds());
			}
		}
		
		List<ComboBox> comboBoxs = Lists.newArrayList();
		List<Permission> permissions = permissionService.findAll();
		if (permissions != null){
			ComboBox comboBox = null;
			for (Permission permission : permissions){
				Boolean selected = permissionIds.contains(permission.getId());
				
				comboBox = new ComboBox();
				comboBox.setId(permission.getId());
				comboBox.setText(permission.getName() + " - (" + permission.getPermission() + ")");
				comboBox.setSelected(selected);
				
				comboBoxs.add(comboBox);
			}
			
		}
		return comboBoxs;
    }
    
    public Set<String> findRoleNames(Set<Long> roleIds){
    	return getRoleRepository().findRoleNames(roleIds);
    }
    
    public Role findByName(String name){
    	return getRoleRepository().findByName(name);
    }
    
    public List<RoleResourcePermission> findRoleResourcePermissionFullNames(Searchable searchable, String separator){
    	List<RoleResourcePermission> roleResourcePermissions = roleResourcePermissionService.findAllWithSort(searchable);
    	
    	if (EmptyUtil.isCollectionEmpty(roleResourcePermissions)) return Lists.newArrayList();
    	
    	for (int i = 0; i <= roleResourcePermissions.size() - 1; i++){
    		RoleResourcePermission userOrganizationJob = roleResourcePermissions.get(i);
    		
    		Resource resource = resourceService.findOne(userOrganizationJob.getResourceId());
			if (resource != null){
				userOrganizationJob.setResourceName(resourceService.findNames(resource.getName(), resource.getParentIds(), false, separator));
			}
			
			String permissionNames = permissionService.findNames(userOrganizationJob.getPermissionIds());
			userOrganizationJob.setPermissionNames(permissionNames);
			
			roleResourcePermissions.set(i, userOrganizationJob);
    	}
    	return roleResourcePermissions;
    }
}
