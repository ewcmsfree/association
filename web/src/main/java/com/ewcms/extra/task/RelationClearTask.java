package com.ewcms.extra.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.security.auth.task.AuthRelationClearTask;
//import com.ewcms.security.auth.task.AuthRelationClearTask;
import com.ewcms.security.group.task.GroupClearRelationTask;
import com.ewcms.security.permission.task.RoleClearRelationTask;
import com.ewcms.security.user.task.UserClearRelationTask;

/**
 * 定时清理对象间的关系
 * <p/>
 * 
 * @author wu_zhijun
 */
@Service("relationClearTask")
public class RelationClearTask {


    @Autowired
    private UserClearRelationTask userClearRelationTask;
    @Autowired
    private GroupClearRelationTask groupClearRelationTask;
    @Autowired
    private RoleClearRelationTask roleClearRelationTask;
    @Autowired
    private AuthRelationClearTask authRelationClearTask;
    
    public void autoClearRelation() {

        //用户与组织机构/工作职务的关系
        userClearRelationTask.clearDeletedUserRelation();

        //分组与组织机构/工作职务的关系
        groupClearRelationTask.clearDeletedGroupRelation();

        //角色与资源/权限的关系
        roleClearRelationTask.clearDeletedRoleRelation();

        //授权与组织机构、组、角色的关系
        authRelationClearTask.clearDeletedAuthRelation();
    }

}
