package com.ewcms.security.user.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;

/**
 *
 * @author 吴智俊
 */
public interface UserOrganizationJobRepository extends BaseRepository<UserOrganizationJob, Long> {
	
    UserOrganizationJob findByUserAndOrganizationIdAndJobId(User user, Long organizationId, Long jobId);

	@Query("select uoj from UserOrganizationJob uoj where not exists(select 1 from Job j where uoj.jobId=j.id) or not exists(select 1 from Organization o where uoj.organizationId=o.id)")
    Page<UserOrganizationJob> findUserOrganizationJobOnNotExistsOrganizationOrJob(Pageable pageable);

    @Modifying
    @Query("delete from UserOrganizationJob uoj where not exists(select 1 from User u where uoj.user=u)")
    void deleteUserOrganizationJobOnNotExistsUser();
    
    @Query("select organizationId from UserOrganizationJob where user=?1")
    Set<Long> findUserOrganizationJobAllOrganizationId(User user);
}
