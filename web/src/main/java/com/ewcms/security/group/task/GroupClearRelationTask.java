package com.ewcms.security.group.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.security.group.repository.GroupRelationRepository;

/**
 * 清理无关联的关系
 * 1、Group-GroupRelation
 * 2、GroupRelation-User
 * 3、GroupRelation-Organization
 * 4、GroupRelation-Job
 * 
 * @author wu_zhijun
 */
@Service
public class GroupClearRelationTask {

    @Autowired
    private GroupRelationRepository groupRelationRepository;

    /**
     * 清除删除的分组对应的关系
     */
    public void clearDeletedGroupRelation() {
        groupRelationRepository.clearDeletedGroupRelation();
    }

}
