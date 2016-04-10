package com.ewcms.security.user.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.user.entity.UserLastOnline;
import com.ewcms.security.user.repository.UserLastOnlineRepository;

/**
 * @author wu_zhijun
 */
@Service
public class UserLastOnlineService extends BaseService<UserLastOnline, Long> {

    private UserLastOnlineRepository getUserLastOnlineRepository() {
        return (UserLastOnlineRepository) baseRepository;
    }

    public UserLastOnline findByUserId(Long userId) {
        return getUserLastOnlineRepository().findByUserId(userId);
    }

    public void lastOnline(UserLastOnline lastOnline) {
        UserLastOnline dbLastOnline = findByUserId(lastOnline.getUserId());

        if (dbLastOnline == null) {
            dbLastOnline = lastOnline;
        } else {
            UserLastOnline.merge(lastOnline, dbLastOnline);
        }
        dbLastOnline.incLoginCount();
        dbLastOnline.incTotalOnlineTime();

        save(dbLastOnline);
    }
}
