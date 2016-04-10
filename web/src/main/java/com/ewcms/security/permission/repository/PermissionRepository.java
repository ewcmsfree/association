package com.ewcms.security.permission.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.permission.entity.Permission;

/**
 * @author wu_zhijun
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {

	@Query("select o.name from Permission o where o.id in ?1 order by o.id")
	Set<String> findNames(Set<Long> ids);
	
	Permission findByName(String name);
}
