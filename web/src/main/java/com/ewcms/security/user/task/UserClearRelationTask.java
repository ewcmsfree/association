package com.ewcms.security.user.task;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.repository.RepositoryHelper;
import com.ewcms.common.utils.LogUtils;
import com.ewcms.security.organization.service.JobService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;

import java.util.Collection;

/**
 * 清理无关联的User-Organization/Job关系
 * 1、User-Organization/Job
 * 
 * @author wu_zhijun
 */
@Service
public class UserClearRelationTask {

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrganizationJobService userOrganizationJobService;
    
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private JobService jobService;


    /**
     * 清除删除的用户-组织机构/工作职务对应的关系
     */
    public void clearDeletedUserRelation() {

        //删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
    	userOrganizationJobService.deleteUserOrganizationJobOnNotExistsUser();

        Page<UserOrganizationJob> page = null;

        int pn = 0;
        final int PAGE_SIZE = 100;
        Pageable pageable = null;
        do {
            pageable = new PageRequest(pn++, PAGE_SIZE);
            page = userOrganizationJobService.findUserOrganizationJobOnNotExistsOrganizationOrJob(pageable);

            //开启新事物清除
            try {
                UserClearRelationTask userClearRelationTask = (UserClearRelationTask) AopContext.currentProxy();
                userClearRelationTask.doClear(page.getContent());
            } catch (Exception e) {
                //出异常也无所谓
                LogUtils.logError("clear user relation error", e);

            }
            //清空会话
            RepositoryHelper.clear();

        } while (page.hasNext());

    }

    public void doClear(Collection<UserOrganizationJob> userOrganizationJobColl) {
        for (UserOrganizationJob userOrganizationJob : userOrganizationJobColl) {

            User user = userOrganizationJob.getUser();

            if (!organizationService.exists(userOrganizationJob.getOrganizationId())) {
                user.getOrganizationJobs().remove(userOrganizationJob);//如果是组织机构删除了 直接移除
            } else if (!jobService.exists(userOrganizationJob.getJobId())) {
                user.getOrganizationJobs().remove(userOrganizationJob);
                userOrganizationJob.setJobId(null);
                user.getOrganizationJobs().add(userOrganizationJob);
            }
            //不加也可 加上的目的是为了清缓存
            userService.update(user);
        }

    }
}
