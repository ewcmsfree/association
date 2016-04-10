package com.ewcms.security.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.user.entity.UserRunAs;
import com.ewcms.security.user.entity.UserRunAsPk;
import com.ewcms.security.user.repository.UserRunAsRepository;

/**
 *
 * @author 吴智俊
 */
@Service
public class UserRunAsService extends BaseService<UserRunAs, UserRunAsPk>{
	
    private UserRunAsRepository getUserRunAsRepository() {
        return (UserRunAsRepository) baseRepository;
    }
    
    public List<Long> findByToUserIds(Long toUserId){
    	return getUserRunAsRepository().findByToUserIds(toUserId);
    }
    
    /**
     * 授予身份
     * 
     * @param fromUserId
     * @param toUserId
     */
    public void grantRunAs(Long fromUserId, Long toUserId){
    	UserRunAsPk userRunAsPk = new UserRunAsPk(fromUserId, toUserId);
    	if (!super.exists(userRunAsPk)){
    		UserRunAs userRunAs = new UserRunAs();
    		userRunAs.setId(userRunAsPk);
    		super.save(userRunAs);
    	}
    }
    
    /**
     * 回收身份
     * 
     * @param fromUserId
     * @param toUserId
     */
    public void revokeRunAs(Long fromUserId, Long toUserId){
    	UserRunAsPk userRunAsPk = new UserRunAsPk(fromUserId, toUserId);
    	super.delete(userRunAsPk);
    }
    
    /**
     * 判断实体是否存在
     * 
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public boolean exists(Long fromUserId, Long toUserId){
    	UserRunAsPk userRunAsPk = new UserRunAsPk(fromUserId, toUserId);
    	return super.exists(userRunAsPk);
    }
}
