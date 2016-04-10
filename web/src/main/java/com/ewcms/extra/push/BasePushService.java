package com.ewcms.extra.push;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class BasePushService {

	private volatile Map<Long, Queue<DeferredResult<Object>>> userNameToDeferredResultMap = new ConcurrentHashMap<Long, Queue<DeferredResult<Object>>>();

	public boolean isOnline(final Long userId) {
		return userNameToDeferredResultMap.containsKey(userId);
	}

	/**
	 * 上线后 创建一个空队列，防止多次判断
	 * 
	 * @param userId
	 */
	public void online(final Long userId) {
		Queue<DeferredResult<Object>> queue = userNameToDeferredResultMap.get(userId);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<DeferredResult<Object>>(); 
			userNameToDeferredResultMap.put(userId, queue);
		}
	}

	public void offline(final Long userId) {
		Queue<DeferredResult<Object>> queue = userNameToDeferredResultMap.remove(userId);
		if (queue != null) {
			for (DeferredResult<Object> result : queue) {
				try {
					result.setResult("");
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}

	public DeferredResult<Object> newDeferredResult(final Long userId) {
		final DeferredResult<Object> deferredResult = new DeferredResult<Object>();
		deferredResult.onCompletion(new Runnable() {
			@Override
			public void run() {
				Queue<DeferredResult<Object>> queue = userNameToDeferredResultMap.get(userId);
				if (queue != null) {
					queue.remove(deferredResult);
					deferredResult.setResult("");
				}
			}
		});
		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() {
				deferredResult.setErrorResult("");
			}
		});
		Queue<DeferredResult<Object>> queue = userNameToDeferredResultMap.get(userId);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<DeferredResult<Object>>();
			userNameToDeferredResultMap.put(userId, queue);
		}
		queue.add(deferredResult);

		return deferredResult;
	}

	/**
	 * 发送给指定用户
	 * 
	 * @param userId
	 * @param data
	 */
	public void push(final Long userId, final Object data) {
		Queue<DeferredResult<Object>> queue = userNameToDeferredResultMap.get(userId);
		if (queue != null){
			for (DeferredResult<Object> deferredResult : queue) {
				if (!deferredResult.isSetOrExpired()) {
					try {
						deferredResult.setResult(data);
					} catch (Exception e) {
						queue.remove(deferredResult);
					}
				}
			}
		}
	}

	/**
	 * 发送给所有在线人员
	 * 
	 * @param data
	 */
	public void push(final Object data) {
		for (Long userId : userNameToDeferredResultMap.keySet()) {
			push(userId, data);
		}
	}

	/**
	 * 定期清空队列 防止中间推送消息时中断造成消息丢失
	 */
	@Scheduled(fixedRate = 5L * 60 * 1000)
	public void sync() {
		Map<Long, Queue<DeferredResult<Object>>> oldMap = userNameToDeferredResultMap;
		userNameToDeferredResultMap = new ConcurrentHashMap<Long, Queue<DeferredResult<Object>>>();
		for (Queue<DeferredResult<Object>> queue : oldMap.values()) {
			if (queue == null) {
				continue;
			}

			for (DeferredResult<Object> deferredResult : queue) {
				try {
					deferredResult.setResult("");
				} catch (Exception e) {
					queue.remove(deferredResult);
				}
			}

		}
	}

}