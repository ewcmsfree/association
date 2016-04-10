package com.ewcms.monitor.web.controller;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.repository.hibernate.HibernateUtils;
import com.ewcms.common.utils.PrettyMemoryUtils;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.common.web.controller.entity.PropertyGrid;
import com.ewcms.common.web.validate.AjaxResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/monitor/hibernate")
@RequiresPermissions("monitor:hibernate:*")
public class HibernateCacheMonitorController extends BaseController{

	@PersistenceContext
	private EntityManager em;
	
	private Statistics statistics = null;
	long upSeconds = 1;
	
	@RequestMapping(value = "index")
	public String index(){
		statistics = HibernateUtils.getSessionFactory(em).getStatistics();
		
		Date startDate = new Date(statistics.getStartTime());
		Date nowDate = new Date();
		upSeconds = (nowDate.getTime() - startDate.getTime())/1000;

		return viewName("index");
	}
	
	/**=================Hibernate===================*/
	@RequestMapping(value = "hibernate/index")
	public String indexHibernate(){
		return viewName("hibernate");
	}
	
	@RequestMapping(value = "hibernate/query")
	@ResponseBody
	public Map<String, Object> queryHibernate(){
		List<PropertyGrid> propertyGrids = Lists.newArrayList();

		Date startDate = new Date(statistics.getStartTime());
		Date nowDate = new Date();
		long upSeconds = (nowDate.getTime() - startDate.getTime())/1000;
		
		String group = "事务统计";
		PropertyGrid propertyGrid = new PropertyGrid("执行的事务次数", statistics.getTransactionCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("成功的事务次数", statistics.getSuccessfulTransactionCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "连接统计";
       	propertyGrid = new PropertyGrid("数据库连接次数", statistics.getConnectCount() + "（平均" + (statistics.getConnectCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("数据库prepareStatement获取次数", statistics.getPrepareStatementCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("数据库prepareStatement关闭次数", statistics.getCloseStatementCount() + "（Hibernate4 bug 没有记录）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("optimistic lock失败次数", statistics.getOptimisticFailureCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "Session统计";
    	propertyGrid = new PropertyGrid("Session打开次数", statistics.getSessionOpenCount() + "（平均" + (statistics.getSessionOpenCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("Session关闭次数", statistics.getSessionCloseCount() + "（因为OpenSessionInView，所以少一次）", group);
    	propertyGrids.add(propertyGrid);
		
    	propertyGrid = new PropertyGrid("Session flush次数", statistics.getFlushCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	//二级缓存
    	secondLevelCachePropertyGrid(propertyGrids);
    	
    	//实体和集合缓存
    	entityAndCollectionCachePropertyGrid(propertyGrids);
    	
    	//查询缓存
    	queryCachePropertyGrid(propertyGrids);
    	
    	//TODO 加入下面注释掉代码，使得数据显示不了
//    	group = "jpa配置";
//    	Map<String, Object> properties = em.getEntityManagerFactory().getProperties();
//    	Iterator<Entry<String, Object>> propertyIt = properties.entrySet().iterator();
//    	while (propertyIt.hasNext()){
//    		Entry<String, Object> entry = propertyIt.next();
//    		String lowerCaseKey = entry.getKey().trim().toLowerCase();
//    		if (lowerCaseKey.startsWith("hibernate") || lowerCaseKey.startsWith("javax") || lowerCaseKey.startsWith("net")){
//    			propertyGrid = new PropertyGrid(entry.getKey(), entry.getValue(), group);
//    	    	propertyGrids.add(propertyGrid);
//    		}
//    	}
    	
    	group = "加载的实体";
    	SessionFactory sessionFactory = HibernateUtils.getSessionFactory(em);
    	Map<String, ClassMetadata> entries = sessionFactory.getAllClassMetadata();
    	Iterator<Entry<String, ClassMetadata>> entryIt = entries.entrySet().iterator();
    	while (entryIt.hasNext()){
    		Entry<String, ClassMetadata> entry = entryIt.next();
    		ClassMetadata classMetadata = (ClassMetadata)entry.getValue();
    		propertyGrid = new PropertyGrid(entry.getKey(), "实体名：" + classMetadata.getEntityName() + "[标识符号：" + classMetadata.getIdentifierPropertyName() + "]", group);
	    	propertyGrids.add(propertyGrid);
    	}
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", propertyGrids.size());
        result.put("rows", propertyGrids);

        return result;
	}
	
	/**=================二级缓存统计===================*/
	@RequestMapping(value = "secondLevelCache/index")
	public String indexSecondLevelCache(){
		return viewName("secondLevelCache");
	}
	
	/**
	 * 二级缓存PropertyGrid
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "secondLevelCache/queryProperty")
	@ResponseBody
	public Map<String, Object> queryPropertyGridSecondLevelCache(){
		List<PropertyGrid> propertyGrids = Lists.newArrayList();
		
		secondLevelCachePropertyGrid(propertyGrids);
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", propertyGrids.size());
        result.put("rows", propertyGrids);

        return result;
	}
	
	/**
	 * 二级缓存DataGrid
	 * @param searchParameter
	 * @return
	 */
	@RequestMapping(value = "secondLevelCache/queryData")
	@ResponseBody
	public Map<String, Object> queryDataGridSecondLevelCache(@ModelAttribute SearchParameter<Object> searchParameter){
		String[] secondLevelCacheRegionNames = statistics.getSecondLevelCacheRegionNames();
		
//		sortSecondLevelCache(secondLevelCacheRegionNames, statistics, searchParameter.getSort());
		
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String secondLevelCacheRegionName : secondLevelCacheRegionNames){
			SecondLevelCacheStatistics secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics(secondLevelCacheRegionName);
			
			long totalCount = secondLevelCacheStatistics.getHitCount() + secondLevelCacheStatistics.getMissCount();
			totalCount = totalCount > 0 ? totalCount : 1;
			double cacheHitPercent = secondLevelCacheStatistics.getHitCount() * 1.0 / totalCount;
			
			Map<String, Object> map = Maps.newHashMap();
			
			map.put("regionName", secondLevelCacheRegionName);
			map.put("hitPercent", cacheHitPercent);
			map.put("hitCount", secondLevelCacheStatistics.getHitCount() + "（平均" +  secondLevelCacheStatistics.getHitCount() / upSeconds + "次/秒）");
			map.put("missCount", secondLevelCacheStatistics.getMissCount() + "（平均" +  secondLevelCacheStatistics.getMissCount() / upSeconds + "次/秒）");
			map.put("putCount", secondLevelCacheStatistics.getPutCount());
			map.put("sizeInMemory", PrettyMemoryUtils.prettyByteSize(secondLevelCacheStatistics.getSizeInMemory()));
			map.put("elementCountInMemory", secondLevelCacheStatistics.getElementCountInMemory());
			map.put("elementCountOnDisk", secondLevelCacheStatistics.getElementCountOnDisk());
			
			list.add(map);
		}
		
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", list.size());
        result.put("rows", list);

        return result;
	}
	
	/**=================二级缓存统计===================*/
	@RequestMapping(value = "queryCache/index")
	public String indexQueryCache(){
		return viewName("queryCache");
	}
	
	/**
	 * 查询缓存PropertyGrid
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "queryCache/queryProperty")
	@ResponseBody
	public Map<String, Object> queryPropertyGridQueryCache(){
		List<PropertyGrid> propertyGrids = Lists.newArrayList();
		
		queryCachePropertyGrid(propertyGrids);
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", propertyGrids.size());
        result.put("rows", propertyGrids);

        return result;
	}
	
	/**
	 * 查询缓存DataGrid
	 * @param searchParameter
	 * @return
	 */
	@RequestMapping(value = "queryCache/queryData")
	@ResponseBody
	public Map<String, Object> queryDataGridQueryCache(@ModelAttribute SearchParameter<Object> searchParameter){
		String[] queries = statistics.getQueries();
		
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String query : queries){
			QueryStatistics queryStatistics = statistics.getQueryStatistics(query);
			
			long queryCacheCount = queryStatistics.getCacheHitCount() + queryStatistics.getCacheMissCount();
			queryCacheCount = queryCacheCount > 0 ? queryCacheCount : 1;
			double queryCacheHitPercent = queryStatistics.getCacheHitCount() * 1.0 / queryCacheCount;
			
			double executionAvgRowCount = queryStatistics.getExecutionRowCount() / queryStatistics.getExecutionCount();
			executionAvgRowCount = executionAvgRowCount > 0 ? executionAvgRowCount : 0;
			
			Map<String, Object> map = Maps.newHashMap();
			
			map.put("query", query);
			map.put("hitPercent", queryCacheHitPercent);
			map.put("hitCount", queryStatistics.getCacheHitCount() + "（平均" +  queryStatistics.getCacheHitCount() / upSeconds + "次/秒）");
			map.put("missCount", queryStatistics.getCacheMissCount() + "（平均" +  queryStatistics.getCacheMissCount() / upSeconds + "次/秒）");
			map.put("putCount", queryStatistics.getCachePutCount());
			map.put("executionCount", queryStatistics.getExecutionCount());
			map.put("executionRowCount", queryStatistics.getExecutionRowCount() + "（平均行数：" + executionAvgRowCount + "）");
			map.put("executionAvgTime", queryStatistics.getExecutionAvgTime());
			map.put("executionMaxTime", queryStatistics.getExecutionMaxTime());
			map.put("executionMinTime", queryStatistics.getExecutionMinTime());
			
			list.add(map);
		}
		
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", list.size());
        result.put("rows", list);

        return result;
	}
	
	
	/**=================实体和集合缓存统计===================*/
	@RequestMapping(value = "entityAndCollectionCache/index")
	public String indexEntityAndCollectionCache(){
		return viewName("entityAndCollectionCache");
	}
	
	/**
	 * 查询缓存PropertyGrid
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "entityAndCollectionCache/queryProperty")
	@ResponseBody
	public Map<String, Object> queryPropertyGridEntityAndCollectionCache(){
		List<PropertyGrid> propertyGrids = Lists.newArrayList();
		
		entityAndCollectionCachePropertyGrid(propertyGrids);
    	
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", propertyGrids.size());
        result.put("rows", propertyGrids);

        return result;
	}
	
	/**
	 * 实体缓存DataGrid
	 * @param searchParameter
	 * @return
	 */
	@RequestMapping(value = "entityCache/queryData")
	@ResponseBody
	public Map<String, Object> queryDataGridEntityCache(@ModelAttribute SearchParameter<Object> searchParameter){
		String[] entityNames = statistics.getEntityNames();
		
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String entityName : entityNames){
			EntityStatistics entityStatistics = statistics.getEntityStatistics(entityName);
			
			Map<String, Object> map = Maps.newHashMap();
			
			map.put("entityName", entityName);
			map.put("deleteCount", entityStatistics.getDeleteCount());
			map.put("insertCount", entityStatistics.getInsertCount());
			map.put("updateCount", entityStatistics.getUpdateCount());
			map.put("loadCount", entityStatistics.getLoadCount());
			map.put("fetchCount", entityStatistics.getFetchCount());
			map.put("optimisticFailureCount", entityStatistics.getOptimisticFailureCount());
			
			list.add(map);
		}
		
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", list.size());
        result.put("rows", list);

        return result;
	}
	
	/**
	 * 实体缓存DataGrid
	 * @param searchParameter
	 * @return
	 */
	@RequestMapping(value = "collectionCache/queryData")
	@ResponseBody
	public Map<String, Object> queryDataGridCollectionCache(@ModelAttribute SearchParameter<Object> searchParameter){
		String[] collectionRoleNames = statistics.getCollectionRoleNames();
		
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String collectionRoleName : collectionRoleNames){
			CollectionStatistics collectionStatistics = statistics.getCollectionStatistics(collectionRoleName);
			
			Map<String, Object> map = Maps.newHashMap();
			
			map.put("collectionRoleName", collectionRoleName);
			map.put("removeCount", collectionStatistics.getRemoveCount());
			map.put("updateCount", collectionStatistics.getUpdateCount());
			map.put("loadCount", collectionStatistics.getLoadCount());
			map.put("fetchCount", collectionStatistics.getFetchCount());
			map.put("recreateCount", collectionStatistics.getRecreateCount());
			
			list.add(map);
		}
		
    	Map<String, Object> result = Maps.newHashMap();
        result.put("total", list.size());
        result.put("rows", list);

        return result;
	}
	
	/**
	 * 实体和集合 增删改查 次数 统计
	 * @return
	 */
	@RequestMapping(value = "control")
	public String showControlForm(){
		return viewName("control");
	}
	
	@RequestMapping(value = "entityName/canUse")
	@ResponseBody
	public List<ComboBox> canUseEntityName(){
		List<ComboBox> comboBoxs = Lists.newArrayList();
		
		ComboBox comboBox = null;
		
		String[] entityNames = statistics.getEntityNames();
		for (String entityName : entityNames){
			comboBox = new ComboBox();
			comboBox.setId(entityName);
			comboBox.setText(entityName);
			comboBoxs.add(comboBox);
		}
		return comboBoxs;
	}
	
	@RequestMapping(value = "collectionRoleName/canUse")
	@ResponseBody
	public List<ComboBox> canUseCollectionRoleName(){
		List<ComboBox> comboBoxs = Lists.newArrayList();
		
		ComboBox comboBox = null;
		
		String[] collectionRoleNames = statistics.getCollectionRoleNames();
		for (String collectionRoleName : collectionRoleNames){
			comboBox = new ComboBox();
			comboBox.setId(collectionRoleName);
			comboBox.setText(collectionRoleName);
			comboBoxs.add(comboBox);
		}
		return comboBoxs;
	}
	
	@RequestMapping(value = "query/canUse")
	@ResponseBody
	public List<ComboBox> canUseQuery(){
		List<ComboBox> comboBoxs = Lists.newArrayList();
		
		ComboBox comboBox = null;
		
		String[] queries = statistics.getQueries();
		for (String query : queries){
			comboBox = new ComboBox();
			comboBox.setId(query);
			comboBox.setText(query);
			comboBoxs.add(comboBox);
		}
		return comboBoxs;
	}
	
	@RequestMapping(value = "evictEntity")
	@ResponseBody
	public AjaxResponse evictEntity(@RequestParam(value = "entityNames", required = false) String[] entityNames, @RequestParam(value = "entityIds", required = false) Serializable[] entityIds){
		boolean entityNamesEmpty = ArrayUtils.isEmpty(entityNames);
		boolean entityIdsEmpty = ArrayUtils.isEmpty(entityIds);
		
		Cache cache = HibernateUtils.getCache(em);
		
		if (entityNamesEmpty && entityIdsEmpty){
			cache.evictEntityRegions();
		} else if (entityIdsEmpty){
			for (String entityName : entityNames){
				cache.evictEntityRegion(entityName);
			}
		} else {
			for (String entityName : entityNames){
				for (Serializable entityId : entityIds){
					cache.evictEntity(entityName, entityId);
				}
			}
		}
		
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "evictCollection")
	@ResponseBody
	public AjaxResponse evictCollection(@RequestParam(value = "collectionRoleNames", required = false) String[] collectionRoleNames, @RequestParam(value = "collectionEntityIds", required = false) Serializable[] collectionEntityIds){
		boolean collectionRoleNamesEmpty = ArrayUtils.isEmpty(collectionRoleNames);
		boolean collectionEntityIdsEmpty = ArrayUtils.isEmpty(collectionEntityIds);
		
		Cache cache = HibernateUtils.getCache(em);
		
		if (collectionRoleNamesEmpty && collectionEntityIdsEmpty){
			cache.evictEntityRegions();
		} else if (collectionEntityIdsEmpty){
			for (String collectionRoleName : collectionRoleNames){
				cache.evictCollectionRegion(collectionRoleName);
			}
		} else {
			for (String collectionRoleName : collectionRoleNames){
				for (Serializable collectionEntityId : collectionEntityIds){
					cache.evictCollection(collectionRoleName, collectionEntityId);
				}
			}
		}
		
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "evictQuery")
	@ResponseBody
	public AjaxResponse evictQuery(@RequestParam(value = "queries", required = false) String[] queries){
		boolean queriesEmpty = ArrayUtils.isEmpty(queries);
		
		Cache cache = HibernateUtils.getCache(em);
		
		if (queriesEmpty){
			cache.evictQueryRegions();
			cache.evictDefaultQueryRegion();
		} else {
			for (String query : queries){
				cache.evictQueryRegion(query);
			}
		}
		
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "evictAll")
	@ResponseBody
	public AjaxResponse evictAll(){
		HibernateUtils.evictLevel2Cache(em);
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "clearAll")
	@ResponseBody
	public AjaxResponse clearAll(){
		HibernateUtils.evictLevel1Cache(em);
		HibernateUtils.getSessionFactory(em).getStatistics().clear();
		
		return new AjaxResponse();
	}
	
	private void secondLevelCachePropertyGrid(List<PropertyGrid> propertyGrids){
    	String group = "二级缓存统计（实体&集合缓存，不包括查询）";
    	long secondLevelCacheCount = statistics.getSecondLevelCacheHitCount() + statistics.getSecondLevelCacheMissCount();
    	secondLevelCacheCount = (secondLevelCacheCount > 0) ? secondLevelCacheCount : 1;
    	double secondLevelCacheHitPercent = statistics.getSecondLevelCacheHitCount() * 1.0 /(secondLevelCacheCount);
    	
    	PropertyGrid propertyGrid = new PropertyGrid("总命中率", secondLevelCacheHitPercent, group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("命中次数", statistics.getSecondLevelCacheHitCount() + "（平均" + (statistics.getSecondLevelCacheHitCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("失效次数", statistics.getSecondLevelCacheMissCount()+ "（平均" + (statistics.getSecondLevelCacheMissCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("被缓存的个数", statistics.getSecondLevelCachePutCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "内存情况";
    	//系统级内存
    	MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    	long usedSystemMemory = heapMemoryUsage.getUsed();
    	long maxSystemMemory = heapMemoryUsage.getMax();
    	
    	String[] secondLevelCacheRegionNames = statistics.getSecondLevelCacheRegionNames();
    			
    	int totalMemorySize = 0;
    	int totalMemoryCount = 0;
    	int totalDiskCount = 0;
    			
    	for (String secondLevelCacheRegionName : secondLevelCacheRegionNames){
    		SecondLevelCacheStatistics secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics(secondLevelCacheRegionName);
    		totalMemorySize += secondLevelCacheStatistics.getSizeInMemory();
    		totalMemoryCount += secondLevelCacheStatistics.getElementCountInMemory();
    		totalDiskCount += secondLevelCacheStatistics.getElementCountOnDisk();
    	}
    	
    	
    	propertyGrid = new PropertyGrid("当前系统使用内存", PrettyMemoryUtils.prettyByteSize(usedSystemMemory), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("系统总内存", PrettyMemoryUtils.prettyByteSize(maxSystemMemory), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("二级缓存使用内存", PrettyMemoryUtils.prettyByteSize(totalMemorySize), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("内存中的总实体数", PrettyMemoryUtils.prettyByteSize(totalMemoryCount), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("磁盘中的总实体数", PrettyMemoryUtils.prettyByteSize(totalDiskCount), group);
    	propertyGrids.add(propertyGrid);
	}
	
	private void queryCachePropertyGrid(List<PropertyGrid> propertyGrids){
    	String group = "查询缓存统计";
    	PropertyGrid propertyGrid = new PropertyGrid("查询总执行次数", statistics.getQueryExecutionCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("最慢查询执行时间", statistics.getQueryExecutionMaxTime() + "毫秒", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("最慢查询", statistics.getQueryExecutionMaxTimeQueryString(), group);
    	propertyGrids.add(propertyGrid);
    	
    	long queryCacheCount = statistics.getQueryCacheHitCount() + statistics.getQueryCacheMissCount();
    	queryCacheCount = queryCacheCount > 0 ? queryCacheCount : 1;
    	double queryCacheHitPercent = statistics.getQueryCacheHitCount() * 1.0 / queryCacheCount;
    	
    	propertyGrid = new PropertyGrid("总命中率", queryCacheHitPercent, group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("命中次数", statistics.getQueryCacheHitCount() + "（平均" + (statistics.getQueryCacheHitCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("失效次数", statistics.getQueryCacheMissCount() + "（平均" + (statistics.getQueryCacheMissCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("被缓存的查询个数", statistics.getQueryCachePutCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "UpdateTimestamp缓存统计";
    	long updateTimestampsCacheCount = statistics.getUpdateTimestampsCacheHitCount() + statistics.getUpdateTimestampsCacheMissCount();
    	updateTimestampsCacheCount = (updateTimestampsCacheCount > 0) ? updateTimestampsCacheCount : 1;
    	double updateTimestampsCacheHitPercent = statistics.getUpdateTimestampsCacheHitCount() * 1.0 / queryCacheCount;
    	
    	propertyGrid = new PropertyGrid("总命中率", updateTimestampsCacheHitPercent, group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("命中次数", statistics.getUpdateTimestampsCacheHitCount() + "（平均：" + (statistics.getUpdateTimestampsCacheHitCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("失效次数", statistics.getUpdateTimestampsCacheMissCount() + "（平均：" + (statistics.getUpdateTimestampsCacheMissCount()/upSeconds) + "次/秒）", group);
    	propertyGrids.add(propertyGrid);
	}
	
	private void entityAndCollectionCachePropertyGrid(List<PropertyGrid> propertyGrids){
		String group = "实体缓存统计";
    	PropertyGrid propertyGrid = new PropertyGrid("实体delete次数", statistics.getEntityDeleteCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("实体insert次数", statistics.getEntityInsertCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("实体update次数", statistics.getEntityUpdateCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("实体load次数", statistics.getEntityLoadCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("实体fetch次数", statistics.getEntityFetchCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	group = "集合缓存统计";
    	propertyGrid = new PropertyGrid("集合remove次数", statistics.getCollectionRemoveCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("集合insert次数", statistics.getEntityInsertCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("集合update次数", statistics.getCollectionUpdateCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("集合load次数", statistics.getCollectionLoadCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("集合fetch次数", statistics.getCollectionFetchCount(), group);
    	propertyGrids.add(propertyGrid);
    	
    	propertyGrid = new PropertyGrid("集合recreated次数", statistics.getCollectionRecreateCount(), group);
    	propertyGrids.add(propertyGrid);
	}
}
