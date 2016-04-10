package com.ewcms.monitor.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * SQL执行
 * 
 * @author wu_zhijun
 *
 */
@Controller
@RequestMapping(value = "/monitor/db")
@RequiresPermissions("monitor:ql:*")
public class SQLExecutorController extends BaseController{

	private final static String INFO_COLUMN_NAME = "提示信息";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@RequestMapping(value = "sqlIndex", method = RequestMethod.GET)
	public String showSQLForm(){
		return viewName("sql");
	}
	
	@RequestMapping(value = "sqlShowColumn")
	@ResponseBody
	public String showColumnNames(@RequestParam("sql") String sql){
		return JSON.toJSONString(getColumnNames(sql, true), true);
	}
		
	@RequestMapping(value = "sqlQuery")
	@ResponseBody
	public Map<String, Object> executeQL(final @RequestParam(value = "sql") String sql, final @ModelAttribute SearchParameter<Object> searchParameter){
		final Map<String, Object> resultMap = Maps.newHashMap();
		
//		final String sql = (String) searchParameter.getParameters().get("CUSTOM_sql");
		
		if (EmptyUtil.isStringEmpty(sql)) return resultMap;
		
		if (!isDML(sql) && !isDQL(sql)) {
			Map<String, String> map = Maps.newHashMap();
			map.put(INFO_COLUMN_NAME, "您执行的SQL不允许，只允许insert、update、delete、select");
			
			resultMap.put("total", 1);
			resultMap.put("rows", Lists.newArrayList(map));
		} else {
			try{
				new TransactionTemplate(transactionManager).execute(new TransactionCallback<Void>(){
					@Override
					public Void doInTransaction(TransactionStatus status) {
						if (isDML(sql)){
							Query query = em.createNativeQuery(sql);
							int updateCount = query.executeUpdate();
							
							Map<String, String> map = Maps.newHashMap();
							map.put(INFO_COLUMN_NAME, "更新了" + updateCount + "行");
							
							resultMap.put("total", 1);
							resultMap.put("rows", Lists.newArrayList(map));
						} else {
							String findSQL = sql;
							String countSQL = "select count(*) from (" + findSQL + ") o";
							Query countQuery = em.createNativeQuery(countSQL);
							Query findQuery = em.createNativeQuery(findSQL);
							findQuery.setFirstResult((searchParameter.getPage() - 1) * searchParameter.getRows());
							findQuery.setMaxResults(searchParameter.getRows());
							
							List<String> columnNames = getColumnNames(sql, false);
							
							List resultList = findQuery.getResultList();
							List<Map<String, Object>> result = Lists.newArrayList();
							for (Object o : resultList){
								Map<String, Object> map = Maps.newHashMap();
								if (!o.getClass().isArray()){
									//TODO 未实现
								} else {
									int i = 0;
									for (Object c : (Object[])o){
										map.put(columnNames.get(i), c);
										i++;
									}
								}
								result.add(map);
							}
							
							resultMap.put("total", ((BigInteger) countQuery.getSingleResult()).longValue());
							resultMap.put("rows", result);
						}
						return null;
					}
				});
			} catch (Exception e){
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				
				Map<String, String> map = Maps.newHashMap();
				map.put(INFO_COLUMN_NAME, sw.toString());

				resultMap.put("total", 1);
				resultMap.put("rows", Lists.newArrayList(map));
			}
		}
		return resultMap;
	}
	
	private final Boolean isDML(String sql){
		String lowerCaseSQL = sql.trim().toLowerCase();
		return lowerCaseSQL.startsWith("insert") || lowerCaseSQL.startsWith("update") || lowerCaseSQL.startsWith("delete");
	}
	
	private final Boolean isDQL(String sql){
		String lowerCaseSQL = sql.trim().toLowerCase();
		return lowerCaseSQL.startsWith("select");
	}
	
	/**
	 * 获取SQL列名
	 * 
	 * @param sql
	 * @return
	 */
	private List<String> getColumnNames(final String sql, final Boolean isShow){
		final List<String> columnNames = Lists.newArrayList();

		String columnExp = "%s";
		if (isShow){
			columnExp = "{field:'%s',title:'%s'}";
		}
		
		final String workColumnExp = columnExp;
		
		if (!isDML(sql) && !isDQL(sql)){
			columnNames.add(String.format(columnExp, INFO_COLUMN_NAME, INFO_COLUMN_NAME));
		} else {
			try{
				em.unwrap(Session.class).doWork(new Work(){
					@Override
					public void execute(final Connection connection) throws SQLException{
						PreparedStatement ps = connection.prepareStatement(sql);
						ResultSetMetaData metaData = ps.getMetaData();
						
						for (int i = 1, j = metaData.getColumnCount(); i <= j; i++){
							String columnLabel = metaData.getColumnLabel(i);
							columnNames.add(String.format(workColumnExp, columnLabel, columnLabel));
						}
						ps.close();
					}
				});
			} catch (Exception e){
				columnNames.add(String.format(columnExp, INFO_COLUMN_NAME, INFO_COLUMN_NAME));
			}
		}	
		return columnNames;
	}
}
