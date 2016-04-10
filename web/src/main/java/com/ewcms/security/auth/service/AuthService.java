package com.ewcms.security.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.auth.entity.Auth;
import com.ewcms.security.auth.repository.AuthRepository;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.service.GroupService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;

import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class AuthService extends BaseService<Auth, Long> {

	@Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    private AuthRepository getAuthRepository() {
        return (AuthRepository) baseRepository;
    }
    
    public Auth addUserAuth(Auth auth){
    	User user = userService.findOne(auth.getUserId());
		if (user == null) return null;
		
		Auth dbAuth = getAuthRepository().findByUserId(auth.getUserId());
		if (dbAuth != null){
			dbAuth.setGroupId(0L);
			dbAuth.setJobId(0L);
			dbAuth.setOrganizationId(0L);
			dbAuth.setRoleIds(auth.getRoleIds());
			
			return update(dbAuth);
		} else{
			dbAuth = new Auth();
			
			dbAuth.setUserId(auth.getUserId());
            dbAuth.setType(auth.getType());
            dbAuth.setRoleIds(auth.getRoleIds());
            
            return save(dbAuth);
		}
    }
    
    public Auth addGroupAuth(Auth auth){
    	Group group = groupService.findOne(auth.getGroupId());
		if (group == null) return null;
		
		Auth dbAuth = getAuthRepository().findByGroupId(auth.getGroupId());
        if (dbAuth != null) {
        	dbAuth.setUserId(0L);
        	dbAuth.setJobId(0L);
			dbAuth.setOrganizationId(0L);
			dbAuth.setRoleIds(auth.getRoleIds());
			
			return update(dbAuth);
        } else {
            dbAuth = new Auth();
            
            dbAuth.setGroupId(auth.getGroupId());
            dbAuth.setType(auth.getType());
            dbAuth.setRoleIds(auth.getRoleIds());
            
            return save(dbAuth);
        }
    }
    
    public Auth addOrganizationJobAuth(Auth m){
    	if (m.getOrganizationId() == null) {
            m.setOrganizationId(0L);
        }
        if (m.getJobId() == null) {
            m.setJobId(0L);
        }

        Auth auth = getAuthRepository().findByOrganizationIdAndJobId(m.getOrganizationId(), m.getJobId());
        if (auth != null) {
        	auth.setUserId(0L);
        	auth.setGroupId(0L);
        	auth.setRoleIds(m.getRoleIds());
        	
        	return update(auth);
        } else {
            auth = new Auth();
            
            auth.setOrganizationId(m.getOrganizationId());
            auth.setJobId(m.getJobId());
            auth.setType(m.getType());
            auth.setRoleIds(m.getRoleIds());
            
            return save(auth);
        }
    }
    
    /**
     * 根据用户信息获取 角色
     * 1.1、用户  根据用户绝对匹配
     * 1.2、组织机构 根据组织机构绝对匹配 此处需要注意 祖先需要自己获取
     * 1.3、工作职务 根据工作职务绝对匹配 此处需要注意 祖先需要自己获取
     * 1.4、组织机构和工作职务  根据组织机构和工作职务绝对匹配 此处不匹配祖先
     * 1.5、组  根据组绝对匹配
     *
     * @param userId             必须有
     * @param groupIds           可选
     * @param organizationIds    可选
     * @param jobIds             可选
     * @param organizationJobIds 可选
     * @return
     */
    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds, Set<Long[]> organizationJobIds) {
        return getAuthRepository().findRoleIds(userId, groupIds, organizationIds, jobIds, organizationJobIds);
    }
}
