package com.ewcms.extra.push;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 * 推送信息服务
 * 
 * @author wu_zhijun
 *
 */
@Service
public class PushService {
	
	@Autowired
    private BasePushService basePushService;

	/**
	 * 未读取消息
	 * 
	 * @param userId 登录用户名
	 * @param unreadMessageCount 未读取数
	 */
    public void pushUnreadMessage(Long userId, Long unreadMessageCount) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("unreadMessageCount", unreadMessageCount);
        basePushService.push(userId, data);
    }
    
    public void pushTotalArchive(Long totalArchive){
        Map<String, Object> data = Maps.newHashMap();
        data.put("totalArchive", totalArchive);
        basePushService.push(data);
    }
    
    public void pushOnline(Long onlineCount){
    	Map<String, Object> data = Maps.newHashMap();
        data.put("onlineCount", onlineCount);
        basePushService.push(data);
    }
    
    /**
     * 公告栏信息
     * 
     * @param notices 公告栏信息列表
     */
    public void pushNewNotice(List<Map<String, Object>> notices){
    	Map<String, Object> data = Maps.newHashMap();
    	data.put("notices", notices);
    	basePushService.push(data);
    }
    
    /**
     * 订阅栏信息
     * 
     * @param subscriptions 订阅栏信息列表
     */
    public void pushNewSubscription(List<Map<String, Object>> subscriptions){
    	Map<String, Object> data = Maps.newHashMap();
    	data.put("subscriptions", subscriptions);
    	basePushService.push(data);
    }
    
    /**
     * 侍办事件信息
     * 
     * @param userId 登录用户名
     * @param todos 待办事件信息列表
     */
    public void pushTodo(Long userId, List<Map<String, Object>> todos){
    	Map<String, Object> data = Maps.newHashMap();
    	data.put("todos", todos);
    	basePushService.push(userId, data);
    }
    
    /**
     * 提醒消息信息
     * 
     * @param userId 用户名
     * @param pops 提醒消息信息列表
     */
    public void pushPop(Long userId, List<Map<String, Object>> pops){
    	Map<String, Object> data = Maps.newHashMap();
    	data.put("pops", pops);
    	basePushService.push(userId, data);
    }
    
    /**
     * 培训公告信息
     * 
     * @param userId
     * @param plans
     */
    public void pushPlan(Long userId, List<Map<String, Object>> plans){
    	Map<String, Object> data = Maps.newHashMap();
    	data.put("plans", plans);
    	basePushService.push(userId, data);
    }
    
    /**
     * 离线用户，即清空用户的DefferedResult 这样就是新用户，可以即时得到通知
     *
     * 比如刷新主页时，需要offline
     *
     * @param userId 用户名
     */
    public void offline(final Long userId) {
        basePushService.offline(userId);
    }
}
