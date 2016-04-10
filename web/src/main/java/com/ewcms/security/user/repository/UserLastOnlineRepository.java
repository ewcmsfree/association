package com.ewcms.security.user.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.UserLastOnline;

/**
 * @author wu_zhijun
 */
public interface UserLastOnlineRepository extends BaseRepository<UserLastOnline, Long> {

    UserLastOnline findByUserId(Long userId);
}
