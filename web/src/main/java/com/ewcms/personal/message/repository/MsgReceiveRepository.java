package com.ewcms.personal.message.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personal.message.entity.MsgReceive;

/**
 * @author 吴智俊
 */
public interface MsgReceiveRepository extends BaseRepository<MsgReceive, Long> {
	
	List<MsgReceive> findByUserId(Long userId);
	
	MsgReceive findByUserIdAndId(Long userId, Long id);
	
	List<MsgReceive> findByUserIdAndReadFalseOrderByIdDesc(Long userId);
}
