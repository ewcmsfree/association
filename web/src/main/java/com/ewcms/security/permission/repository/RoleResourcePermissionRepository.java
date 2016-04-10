package com.ewcms.security.permission.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.entity.RoleResourcePermission;

/**
 * @author wu_zhijun
 */
public interface RoleResourcePermissionRepository extends BaseRepository<RoleResourcePermission, Long> {
    
	RoleResourcePermission findByRoleAndResourceId(Role role, Long resourceId);
}
