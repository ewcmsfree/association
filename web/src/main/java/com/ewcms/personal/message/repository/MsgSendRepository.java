package com.ewcms.personal.message.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.entity.MsgType;

/**
 * @author 吴智俊
 */
public interface MsgSendRepository extends BaseRepository<MsgSend, Long> {
	
	MsgSend findByUserIdAndId(Long userId, Long id);
	
	List<MsgSend> findByTypeOrderBySendTimeDesc(MsgType type);
	
    @Query("select username from User where id in ?1")
    Set<String> findUserNames(Set<Long> userIds);
}
