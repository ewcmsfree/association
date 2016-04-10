package com.ewcms.personal.message.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.extra.push.PushService;
//import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.personal.message.entity.MsgContent;
import com.ewcms.personal.message.entity.MsgReceive;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.entity.MsgStatus;
import com.ewcms.personal.message.entity.MsgType;
import com.ewcms.personal.message.repository.MsgSendRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class MsgSendService extends BaseService<MsgSend, Long>{

	private MsgSendRepository getMsgSendRepository(){
		return (MsgSendRepository) baseRepository;
	}
	
	@Autowired
	private MsgContentService msgContentService;
	@Autowired
	private PushService pushService;
	@Autowired
	private MsgReceiveService msgReceiveService;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Long addMsgSend(Long userId, MsgSend msgSend, String content, Set<Long> receiveUserIds) {
		msgSend.setUserId(userId);
		msgSend.setStatus(MsgStatus.FAVORITE);
		MsgContent msgContent = new MsgContent();
		msgContent.setTitle(msgSend.getTitle());
		msgContent.setDetail(content);
		List<MsgContent> msgContents = new ArrayList<MsgContent>();
		msgContents.add(msgContent);
		msgSend.setMsgContents(msgContents);
		
		if (msgSend.getType() == MsgType.GENERAL){
			msgSend.setUserIds(receiveUserIds);
			super.save(msgSend);
			
			receiveUserIds.remove(userId);
			MsgReceive msgReceive = null;
			for (Long receiveUserId : receiveUserIds){
				msgReceive = new MsgReceive();
				msgReceive.setUserId(receiveUserId);
				msgReceive.setSendUserId(userId);
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceive.setMsgContent(msgContent);
				msgReceiveService.save(msgReceive);
				pushService.pushUnreadMessage(receiveUserId, msgReceiveService.findUnReadMessageCountByUserId(receiveUserId));;
			}
		}else if (msgSend.getType() == MsgType.NOTICE){
			msgSend.setUserIds(null);
			super.save(msgSend);
			pushService.pushNewNotice(findTopRowNoticesOrSubscription(MsgType.NOTICE, 10));
		}else if (msgSend.getType() == MsgType.SUBSCRIPTION){
			msgSend.setUserIds(null);
			super.save(msgSend);
			pushService.pushNewSubscription(findTopRowNoticesOrSubscription(MsgType.SUBSCRIPTION, 10));
		}
		
		return msgSend.getId();
	}

	public Long updMsgSend(MsgSend send) {
		return null;
	}

	@Override
	public void delete(List<Long> msgSendIds) {
		Boolean isNotice = false;
		Boolean isSubscription = false;
		
		for (Long msgSendId : msgSendIds){
			MsgSend msgSend = findOne(msgSendId);
			if (msgSend.getType() == MsgType.NOTICE){
				isNotice = true;
			}else if (msgSend.getType() == MsgType.SUBSCRIPTION){
				isSubscription = true;
			}
			super.delete(msgSend);
		}
		
		if (isNotice){
			pushService.pushNewNotice(findTopRowNoticesOrSubscription(MsgType.NOTICE, 10));
		}

		if (isSubscription){
			pushService.pushNewSubscription(findTopRowNoticesOrSubscription(MsgType.SUBSCRIPTION, 10));
		}
	}
	

	public Long addSubscription(Long userId, Long msgSendId, String title, String detail) {
		MsgSend msgSend = getMsgSendRepository().findByUserIdAndId(userId, msgSendId);
		Assert.notNull(msgSend);
		if (msgSend.getType() == MsgType.SUBSCRIPTION){
			List<MsgContent> msgContents = msgSend.getMsgContents();
			
			MsgContent msgContent = new MsgContent();
			msgContent.setTitle(title);
			msgContent.setDetail(detail);
			
			msgContents.add(msgContent);
			msgSend.setMsgContents(msgContents);
			
			super.save(msgSend);
			
			Set<Long> receiveUserIds = msgSend.getUserIds();
			receiveUserIds.remove(userId);
			
			MsgReceive msgReceive = null;
			for (Long receiveUserId : receiveUserIds){
				msgReceive = new MsgReceive();
				msgReceive.setMsgContent(msgContent);
				msgReceive.setSendUserId(userId);
				msgReceive.setSubscription(true);
				msgReceive.setUserId(receiveUserId);
				msgReceive.setStatus(MsgStatus.FAVORITE);
				msgReceiveService.save(msgReceive);
			}
			return msgSend.getId();
		}
		return null;
	}

	public void delSubscription(Long msgContentId) {
		msgContentService.delete(msgContentId);
	}
	
	public List<Map<String, Object>> findTopRowNoticesOrSubscription(MsgType type, Integer row) {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("type", SearchOperator.EQ, type);
		searchable.addSort(Direction.DESC, "id").addSort(Direction.DESC, "sendTime");
		searchable.setPage(0, row);
		Page<MsgSend> msgSends = findAll(searchable);
				
		List<Map<String, Object>> lists = Lists.newArrayList();
		for (MsgSend msgSend : msgSends.getContent()){
			Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", msgSend.getId());
            map.put("title", msgSend.getTitle());
            map.put("date", simpleDateFormat.format(msgSend.getSendTime()));
            map.put("sendUserName", Collections3.convertToString(getMsgSendRepository().findUserNames(Sets.newHashSet(msgSend.getUserId())), ","));
            lists.add(map);
		}
		return lists;
	}
	
	public Map<String, Object> query(Long userId, SearchParameter<Long> searchParameter){
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, MsgSend.class);
		searchable.addSearchFilter("userId", SearchOperator.EQ, userId);
		searchable.addSort(Direction.DESC, "id").addSort(Direction.DESC, "sendTime");
		
		Page<MsgSend> msgSends = findAll(searchable);
		
		for (MsgSend msgSend : msgSends.getContent()){
			Set<Long> userIds = msgSend.getUserIds();
			if (EmptyUtil.isCollectionNotEmpty(userIds)){
				Set<String> userNames = getMsgSendRepository().findUserNames(userIds);
				if (EmptyUtil.isCollectionNotEmpty(userNames)){
					msgSend.setUserNames(Collections3.convertToString(userNames, ","));
				}
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", msgSends.getTotalElements());
		resultMap.put("rows", msgSends.getContent());
		return resultMap;
	}
	
	public Map<String, Object> queryMore(SearchParameter<Long> searchParameter, MsgType type){
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("EQ_type", type);
		searchParameter.setParameters(parameters);
		
		Map<String, Direction> sorts = searchParameter.getSorts();
		sorts.put("sendTime",  Direction.DESC);
		sorts.put("id", Direction.DESC);
		searchParameter.setSorts(sorts);

		return super.query(searchParameter);
	}
	
	public Set<String> findUserNames(Set<Long> userIds){
		return getMsgSendRepository().findUserNames(userIds);
	}
}
