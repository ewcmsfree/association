package com.ewcms.security.auth.repository;

import java.util.Set;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.auth.entity.Auth;

/**
 * @author wu_zhijun
 */
public interface AuthRepository extends BaseRepository<Auth, Long> {

    Auth findByUserId(Long userId);

    Auth findByGroupId(Long groupId);

    Auth findByOrganizationIdAndJobId(Long organizationId, Long jobId);

    Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds, Set<Long[]> organizationJobIds);

}
