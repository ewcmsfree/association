package com.ewcms.security.group.repository;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.group.entity.Group;

import java.util.List;
import java.util.Set;

/**
 * @author wu_zhijun
 */
public interface GroupRepository extends BaseRepository<Group, Long> {

    @Query("select id from Group where defaultGroup=true and show=true")
    List<Long> findDefaultGroupIds();

    Group findByName(String name);
    
    @Query("select id from Group where id in ?1 and show=true")
    Set<Long> findGroupIds(Set<Long> groupIds);
    
	@Query("from Group where id in ?1")
	Set<Group> findGroupDisplay(Set<Long> groupIds);
}
