package com.ewcms.monitor.web.controller;

import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.repository.hibernate.HibernateUtils;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/monitor/db")
@RequiresPermissions("monitor:ql:*")
public class JPAQLExecutorController extends BaseController{
	
	private final static String INFO_COLUMN_NAME = "提示信息";
	private final static String COLUMN_EXP = "{field:'%s',title:'%s'}";

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@RequestMapping(value = "jpaqlIndex", method = RequestMethod.GET)
	public String showJPAQLForm(){
		return viewName("jpaql");
	}
	
	@RequestMapping(value = "jpaqlQuery")
	@ResponseBody
	public Map<String, Object> executeQL(final @RequestParam(value = "jpaql") String jpaql, final @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, final @RequestParam(value = "pageSize", defaultValue="30") Integer pageSize){
		Map<String, Object> executeMap = Maps.newHashMap();
		
		if (EmptyUtil.isStringEmpty(jpaql)) return executeMap;
		
		final Map<String, Object> resultMap = Maps.newHashMap();
		final List<String> columnNamesToPage = Lists.newArrayList();
		try{
			new TransactionTemplate(transactionManager).execute(new TransactionCallback<Void>(){
				@Override
				public Void doInTransaction(TransactionStatus status) {
					//先执行jpaql更新操作
					try{
						Query query = em.createQuery(jpaql);
						int updateCount = query.executeUpdate();
						
						Map<String, String> map = Maps.newHashMap();
						map.put(INFO_COLUMN_NAME, "更新了" + updateCount + "行");
						
						resultMap.put("total", 1);
						resultMap.put("rows", Lists.newArrayList(map));
						
						columnNamesToPage.add(String.format(COLUMN_EXP, INFO_COLUMN_NAME, INFO_COLUMN_NAME));
						
						return null;
					} catch (Exception e){}
					
					//如果更新操作失败，再尝试执行jpaql查询
					String findJPAQL = jpaql;
					String alias = QueryUtils.detectAlias(findJPAQL);
					if (StringUtils.isEmpty(alias)){
						Pattern pattern = Pattern.compile("^(.*\\s*from\\s+)(.*)(\\s*.*)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
						findJPAQL = pattern.matcher(findJPAQL).replaceFirst("$1$2 o $3");
					}
					String countJPAQL = QueryUtils.createCountQueryFor(findJPAQL);
					Query countQuery = em.createQuery(countJPAQL);
					Query findQuery = em.createQuery(findJPAQL);
					findQuery.setFirstResult((pageNumber - 1) * pageSize);
					findQuery.setMaxResults(pageSize);
					
					List resultList = findQuery.getResultList();
					
					Object obj = resultList.get(0);
					SessionFactory sessionFactory = HibernateUtils.getSessionFactory(em);
					ClassMetadata metadata = sessionFactory.getClassMetadata(obj.getClass());
					
					List<String> columnNames = Lists.newArrayList();
					if (metadata != null){
						String[] propertyNames = Arrays.copyOf(metadata.getPropertyNames(), metadata.getPropertyNames().length);
						ArrayUtils.reverse(propertyNames);

						String identifierPropertyName = metadata.getIdentifierPropertyName();
						
						columnNames.add(identifierPropertyName);
						columnNamesToPage.add(String.format(COLUMN_EXP, identifierPropertyName, identifierPropertyName));
						for (String propertyName : propertyNames){
							columnNames.add(propertyName);
							columnNamesToPage.add(String.format(COLUMN_EXP, propertyName, propertyName));
						}
						
						BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);
					
						List<Map<String, Object>> result = Lists.newArrayList();
						for (Object o : resultList){
							Map<String, Object> map = Maps.newHashMap();
							
							PropertyDescriptor identifierPropertyDescriptor = beanWrapper.getPropertyDescriptor(metadata.getIdentifierPropertyName());
							
							try {
								map.put(columnNames.get(0), identifierPropertyDescriptor.getReadMethod().invoke(o));
								
								int i = 1;
								for (String propertyName : propertyNames){
									PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor(propertyName);
									map.put(columnNames.get(i), propertyDescriptor.getReadMethod().invoke(o));
									i++;
								}
							} catch (Exception e) {
							}
							result.add(map);
						}
						resultMap.put("total", ((Long) countQuery.getSingleResult()).longValue());
						resultMap.put("rows", result);
					} else{
						String out = "未知实体类型，直接循环输出<br/>";
						for(Object r : resultList) {
		                    String str = r.toString();
		                    if(r.getClass().isArray()) {
		                        str = Arrays.toString((Object[])r);
		                    }
		                    out += str + "<br/>";
						}
						
						Map<String, String> map = Maps.newHashMap();
						map.put(INFO_COLUMN_NAME, out);
						
						resultMap.put("total", 1);
						resultMap.put("rows", Lists.newArrayList(map));

						columnNamesToPage.add(String.format(COLUMN_EXP, INFO_COLUMN_NAME, INFO_COLUMN_NAME));
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

			columnNamesToPage.add(String.format(COLUMN_EXP, INFO_COLUMN_NAME, INFO_COLUMN_NAME));
		}
		
		executeMap.put("columns", columnNamesToPage);
		executeMap.put("data", resultMap);
		
		return executeMap;
	}
}
