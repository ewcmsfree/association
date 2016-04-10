package com.ewcms.monitor.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.PrettyMemoryUtils;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Ehcache监控
 * 
 * @author wu_zhijun
 *
 */
@Controller
@RequestMapping(value = "/monitor/ehcache")
@RequiresPermissions("monitor:ehcache:*")
public class EhcacheMonitorController extends BaseController{

	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(value = "index")
	public String index(){
		return viewName("index");
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Object> searchParameter){
		 String[] cacheNames = cacheManager.getCacheNames();
         int length = cacheNames.length;
         
         List<Map<String, Object>> ehcacheMonitors = Lists.newArrayList();
         
         Map<String, Object> result = Maps.newHashMap();
         if (length != 0) {
        	 int page = searchParameter.getPage();
        	 int rows = searchParameter.getRows();
        	 
        	 if (length > page * rows) length = page * rows;
        	 
        	 for (int i = 0; i < length; i++){
        		 String cacheName = cacheNames[i];
        		 
        		 Cache cache = cacheManager.getCache(cacheName);
        		 Statistics statistics= cache.getStatistics();
        		 
        		 Long cacheHits = statistics.getCacheHits();
        		 Long cacheMisses = statistics.getCacheMisses();
        		 Long totalCount =  cacheHits + cacheMisses;
        		 totalCount = totalCount > 0 ? totalCount : 1;
        		 Double cacheHitPercent = cacheHits * 1.0 / totalCount;
        		 
        		 Map<String, Object> map = Maps.newHashMap();
        		 
        		 map.put("cacheName", cacheName);
        		 map.put("cacheHitPercent", cacheHitPercent);
        		 map.put("cacheHits", cacheHits);
        		 map.put("cacheMisses", cacheMisses);
        		 map.put("objectCount", statistics.getObjectCount());
        		 map.put("searchesPerSecond", statistics.getSearchesPerSecond());
        		 map.put("averageSearchTime", statistics.getAverageSearchTime());
        		 map.put("averageGetTime", statistics.getAverageGetTime());
        		 
        		 ehcacheMonitors.add(map);
        	 }
        	
         }
         result.put("total", length);
         result.put("rows", ehcacheMonitors);
         
         return result;
	}
	
	@RequestMapping(value = "{cacheName}/detail/index")
	public String detailIndex(@PathVariable(value = "cacheName") String cacheName){
		return viewName("detail/index");
	}
	
	@RequestMapping(value = "{cacheName}/detail/query")
	@ResponseBody
	public Map<String, Object> detailQuery(@PathVariable(value = "cacheName") String cacheName, @ModelAttribute SearchParameter<String> searchParameter){
		List keys = cacheManager.getCache(cacheName).getKeys();
		
		String searchKey = (String) searchParameter.getParameters().get("CUSTOM_key");
		if (EmptyUtil.isStringEmpty(searchKey)) searchKey = "";
		
		List<Map<String, Object>> ehcacheDetailMonitors = Lists.newArrayList();
		Map<String, Object> result = Maps.newHashMap();
		
		int page = searchParameter.getPage();
   	 	int rows = searchParameter.getRows();
   	 	
		Integer length = 0;
		for (Object key : keys){
			if (key.toString().contains(searchKey)){
				Element element = cacheManager.getCache(cacheName).get(key);
				
				Map<String, Object> map = Maps.newHashMap();
				map.put("key", key.toString());
				map.put("objectValue", element.getObjectValue().toString());
				map.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
				map.put("hitCount", element.getHitCount());
				map.put("latestOfCreationAndUpdateTime", DateFormatUtils.format(new Date(element.getLatestOfCreationAndUpdateTime()), "yyyy-MM-dd HH:mm:ss"));
				map.put("lastAccessTime", DateFormatUtils.format(new Date(element.getLastAccessTime()), "yyyy-MM-dd HH:mm:ss"));
				if (element.getExpirationTime() == Long.MAX_VALUE){
					map.put("expirationTime", "不过期");
				} else {
					map.put("expirationTime", DateFormatUtils.format(new Date(element.getExpirationTime()), "yyyy-MM-dd HH:mm:ss"));
				}
				map.put("timeToIdle", element.getTimeToIdle());
				map.put("timeToLive", element.getTimeToLive());
				map.put("version", element.getVersion());
				
				ehcacheDetailMonitors.add(map);
				
				length++;
			}
			if (length != 0 && (length > page * rows)) break;
		}
		
        result.put("total", length);
        result.put("rows", ehcacheDetailMonitors);
        
        return result;
		
	}
	
	
	@RequestMapping(value = "{cacheName}/detail/delete")
	@ResponseBody
	public AjaxResponse delete(@PathVariable(value = "cacheName") String cacheName, @RequestParam(required = false) List<String> selections){
		AjaxResponse ajaxResponse = new AjaxResponse(true);
		try{
			Cache cache = cacheManager.getCache(cacheName);
			for (String key : selections){
				cache.remove(key);
			}
		} catch (Exception e){
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("操作失败");
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "{cacheName}/detail/clear")
	@ResponseBody
	public AjaxResponse clear(@PathVariable(value = "cacheName") String cacheName){
		AjaxResponse ajaxResponse = new AjaxResponse(true);
		try{
			Cache cache = cacheManager.getCache(cacheName);
			cache.clearStatistics();
			cache.removeAll();
			ajaxResponse.setSuccess(true);
		} catch (Exception e){
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("操作失败");
		}
		return ajaxResponse;
	}
}
