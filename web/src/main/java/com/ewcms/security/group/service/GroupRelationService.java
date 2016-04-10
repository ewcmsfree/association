package com.ewcms.security.group.service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.group.entity.GroupRelation;
import com.ewcms.security.group.repository.GroupRelationRepository;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class GroupRelationService extends BaseService<GroupRelation, Long> {

    private GroupRelationRepository getGroupRelationRepository() {
        return (GroupRelationRepository) baseRepository;
    }

    public Set<Long> findGroupIds(Long userId){
    	return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId));
    }
    
    public Set<Long> findGroupIds(Long userId, Set<Long> organizationIds) {
        if (organizationIds.isEmpty()) {
            return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId));
        }
        return Sets.newHashSet(getGroupRelationRepository().findGroupIds(userId, organizationIds));
    }
    
    public GroupRelation findByGroupIdAndUserId(Long groupId, Long userId){
    	return getGroupRelationRepository().findByGroupIdAndUserId(groupId, userId);
    }
    
    public GroupRelation findByGroupIdAndOrganizationId(Long groupId, Long organizationId){
    	return getGroupRelationRepository().findByGroupIdAndOrganizationId(groupId, organizationId);
    }
}
