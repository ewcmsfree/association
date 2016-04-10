package com.ewcms.security.permission.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.permission.entity.Role;

/**
 * @author wu_zhijun
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
	
    @Query("select name from Role where id in ?1")
    Set<String> findRoleNames(Set<Long> roleIds);
    
    Role findByName(String name);
    
    @Query("from Role where id in ?1 and show=true")
    Set<Role> findShowRoles(Set<Long> roleIds);
}
