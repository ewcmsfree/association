package com.ewcms.security.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.UserRunAs;
import com.ewcms.security.user.entity.UserRunAsPk;

/**
 *
 * @author 吴智俊
 */
public interface UserRunAsRepository extends BaseRepository<UserRunAs, UserRunAsPk>{

	@Query("select o.id.fromUserId from UserRunAs o where o.id.toUserId=?1")
	List<Long> findByToUserIds(Long toUserId);
}
