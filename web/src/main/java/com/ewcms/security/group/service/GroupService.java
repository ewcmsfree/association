package com.ewcms.security.group.service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.repository.GroupRepository;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class GroupService extends BaseService<Group, Long> {

    @Autowired
    private GroupRelationService groupRelationService;

    private GroupRepository getGroupRepository() {
        return (GroupRepository) baseRepository;
    }

    @Override
    public Group save(Group m){
    	if (m.getType() == GroupType.organization){
    		m.setDefaultGroup(Boolean.FALSE);
    	}
    	return super.save(m);
    }
    
    @Override
    public Group update(Group m){
    	if (m.getType() == GroupType.organization){
    		m.setDefaultGroup(Boolean.FALSE);
    	}
    	return super.update(m);
    }
    
    public Set<Map<String, Object>> findIdAndNames(Searchable searchable, String groupName) {

        searchable.addSearchFilter("name", SearchOperator.LIKE, groupName);

        return Sets.newHashSet(
                Lists.transform(
                        findAll(searchable).getContent(),
                        new Function<Group, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> apply(Group input) {
                                Map<String, Object> data = Maps.newHashMap();
                                data.put("label", input.getName());
                                data.put("value", input.getId());
                                return data;
                            }
                        }
                )
        );
    }

    /**
     * 获取可用的的分组编号列表
     *
     * @param userId 用户编号
     * @param organizationIds 组织机构编号集合
     * @param isDefaultGroup 是否加入默认分组
     * @return
     */
    public Set<Long> findShowGroupIds(Long userId, Set<Long> organizationIds, Boolean isDefaultGroup) {
        Set<Long> groupIds = Sets.newHashSet();
        if (isDefaultGroup){
        	groupIds.addAll(getGroupRepository().findDefaultGroupIds());
        }
        groupIds.addAll(groupRelationService.findGroupIds(userId, organizationIds));

        if (EmptyUtil.isCollectionEmpty(groupIds)){
        	return groupIds;
        } else {
        	return getGroupRepository().findGroupIds(groupIds);
        }
    }
    
    public Group findByName(String name){
    	return getGroupRepository().findByName(name);
    }
    
    public Set<Group> findGroupDisplay(Set<Long> groupIds){
    	if (EmptyUtil.isCollectionEmpty(groupIds)){
    		return Sets.newHashSet();
    	} else {
    		return getGroupRepository().findGroupDisplay(groupIds);
    	}
    }
}
