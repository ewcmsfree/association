package com.ewcms.security.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.security.organization.entity.Job;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.JobService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.repository.UserOrganizationJobRepository;

/**
 *
 * @author 吴智俊
 */
@Service
public class UserOrganizationJobService extends BaseService<UserOrganizationJob, Long>{
	
    private UserOrganizationJobRepository getUserOrganizationJobRepository() {
        return (UserOrganizationJobRepository) baseRepository;
    }
    
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private JobService jobService;
    
    @Override
    public UserOrganizationJob save(UserOrganizationJob m){
    	UserOrganizationJob dbUserOrganizationJob = findByUserAndOrganizationIdAndJobId(m.getUser(), m.getOrganizationId(), m.getJobId());
    	if (dbUserOrganizationJob != null){
    		m.setId(dbUserOrganizationJob.getId());
    	}
    	return super.save(m);
    }
    
    public UserOrganizationJob findByUserAndOrganizationIdAndJobId(User user, Long organizationJob, Long jobId){
    	return getUserOrganizationJobRepository().findByUserAndOrganizationIdAndJobId(user, organizationJob, jobId);
    }
    
    /**
     * 获取那些在用户-组织机构/工作职务中存在 但在组织机构/工作职务中不存在的
     *
     * @param pageable
     * @return
     */
    public Page<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(Pageable pageable) {
        return getUserOrganizationJobRepository().findUserOrganizationJobOnNotExistsOrganizationOrJob(pageable);
    }

    /**
     * 删除用户不存在的情况的UserOrganizationJob（比如手工从数据库物理删除）。。
     *
     * @return
     */
    public void deleteUserOrganizationJobOnNotExistsUser() {
    	getUserOrganizationJobRepository().deleteUserOrganizationJobOnNotExistsUser();
    }
    
    /**
     * 查询组织机构和工作职务的全名
     * 
     * @param searchable
     * @param separator 分格符
     * @return
     */
    public Map<String, Object> findUserOrganizationJobFullNames(Searchable searchable, String separator){
    	Page<UserOrganizationJob> pages = findAll(searchable);
    	
    	List<UserOrganizationJob> userOrganizationJobs = pages.getContent();
    	
    	for (UserOrganizationJob userOrganizationJob : userOrganizationJobs){
    		Organization organization = organizationService.findOne(userOrganizationJob.getOrganizationId());
			if (EmptyUtil.isNotNull(organization)){
				userOrganizationJob.setOrganizationName(organizationService.findNames(organization.getName(), organization.getParentIds(), false, separator));
			}
			
			Job job = jobService.findOne(userOrganizationJob.getJobId());
			if (EmptyUtil.isNotNull(job)) {
				userOrganizationJob.setJobName(jobService.findNames(job.getName(), job.getParentIds(), false, separator));
			}
    	}
    	
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", pages.getTotalElements());
		resultMap.put("rows", userOrganizationJobs);

    	return resultMap;
    }
    
    public Set<Long> findUserOrganizationJobAllOrganizationId(User user){
    	return getUserOrganizationJobRepository().findUserOrganizationJobAllOrganizationId(user);
    }
}
