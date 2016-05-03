package com.ewcms;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ewcms.common.entity.AbstractEntity;

/**
 * 
 * @author wu_zhijun
 *
 */
@ContextConfiguration(locations = {
		"classpath:spring-common.xml",
		"classpath:spring-config.xml",
		"classpath:spring-test-config.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class BaseIT extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager entityManager;
	
	protected String nextRandom(){
		return System.currentTimeMillis() + RandomStringUtils.randomNumeric(5);
	}
	
	protected void flush(){
		entityManager.flush();
	}
	
	protected void clear(){
		entityManager.flush();
		entityManager.clear();
	}
	
	protected void deleteAll(List<? extends AbstractEntity<?>> entityList){
		for (AbstractEntity<?> m : entityList){
			delete(m);
		}
	}
	
	protected void delete(AbstractEntity<?> m){
		m = entityManager.find(m.getClass(), m.getId());
		if (m != null){
			entityManager.remove(m);
		}
	}
}
