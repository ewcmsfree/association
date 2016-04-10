package com.ewcms.security.user.repository;

import org.apache.shiro.session.mgt.OnlineSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.user.entity.UserOnline;

import java.util.Date;
import java.util.List;

/**
 * @author wu_zhijun
 */
public interface UserOnlineRepository extends BaseRepository<UserOnline, String> {

    @Query("from UserOnline o where o.lastAccessTime < ?1 order by o.lastAccessTime asc")
    Page<UserOnline> findExpiredUserOnlineList(Date expiredDate, Pageable pageable);

    @Modifying
    @Query("delete from UserOnline o where o.id in (?1)")
    void batchDelete(List<String> needExpiredIdList);
    
    @Query("select count(DISTINCT userId) from UserOnline where status=?1 group by userId,host")
    List<Long> onlineCountList(OnlineSession.OnlineStatus onlineStatus);
}
