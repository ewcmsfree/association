package com.ewcms.security.user.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.User;

/**
 * @author wu_zhijun
 */
public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

    User findByMobilePhoneNumber(String mobilePhoneNumber);

    User findByEmail(String email);
    
	@Query("from User where id in ?1")
	Set<User> findUserDisplay(Set<Long> userIds);
}
