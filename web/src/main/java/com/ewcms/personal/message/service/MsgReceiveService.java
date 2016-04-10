package com.ewcms.personal.message.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.extra.push.PushService;
import com.ewcms.personal.message.entity.MsgReceive;
import com.ewcms.personal.message.repository.MsgReceiveRepository;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class MsgReceiveService extends BaseService<MsgReceive, Long>{
	
	private MsgReceiveRepository getMsgReceiveRepository(){
		return (MsgReceiveRepository) baseRepository;
	}
	
	@Autowired
	private PushService pushService;

	public void delete(Long userId, List<Long> msgReceiveIds) {
		super.delete(msgReceiveIds);
		pushService.pushUnreadMessage(userId, findUnReadMessageCountByUserId(userId));
	}

	public List<MsgReceive> findMsgReceiveByUserId(Long userId) {
		return getMsgReceiveRepository().findByUserId(userId);
	}

	public void markReadMsgReceive(Long userId, Long msgReceiveId, Boolean read) {
		MsgReceive msgReceive = getMsgReceiveRepository().findByUserIdAndId(userId, msgReceiveId);
		Assert.notNull(msgReceive);
		if (msgReceive.getRead() != read){
			msgReceive.setRead(read);
			if (read){
				msgReceive.setReadTime(new Date(Calendar.getInstance().getTime().getTime()));
			}else{
				msgReceive.setReadTime(null);
			}
			super.save(msgReceive);
		}
		pushService.pushUnreadMessage(userId, findUnReadMessageCountByUserId(userId));
	}

	public List<MsgReceive> findMsgReceiveByUnRead(Long userId) {
		return getMsgReceiveRepository().findByUserIdAndReadFalseOrderByIdDesc(userId);
	}

	public void readMsgReceive(Long userId, Long msgReceiveId) {
		MsgReceive msgReceive = getMsgReceiveRepository().findByUserIdAndId(userId, msgReceiveId);
		Assert.notNull(msgReceive);
		msgReceive.setRead(true);
		msgReceive.setReadTime(new Date(Calendar.getInstance().getTime().getTime()));
		super.save(msgReceive);
	}

	public Long findUnReadMessageCountByUserId(Long userId) {
		List<MsgReceive> receives = getMsgReceiveRepository().findByUserIdAndReadFalseOrderByIdDesc(userId);
		Long count = 0L;
		if (EmptyUtil.isCollectionNotEmpty(receives)){
			count = (long) receives.size();
		}
		return count;
	}
	
	public Map<String, Object> query(Long userId, SearchParameter<Long> searchParameter){
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("EQ_userId", userId);
		searchParameter.setParameters(parameters);
		
		Map<String, Direction> sorts = searchParameter.getSorts();
		sorts.put("read",  Direction.ASC);
		sorts.put("readTime", Direction.DESC);
		sorts.put("id", Direction.DESC);
		searchParameter.setSorts(sorts);

		return super.query(searchParameter);
	}
}
