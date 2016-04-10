package com.ewcms.system.externalds.generate.factory.init;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ewcms.system.externalds.entity.BeanDs;
import com.ewcms.system.externalds.entity.CustomDs;
import com.ewcms.system.externalds.entity.JdbcDs;
import com.ewcms.system.externalds.entity.JndiDs;
import com.ewcms.system.externalds.generate.factory.init.bean.BeanForInterfaceFactory;

/**
 * 数据库服务工厂,在系统启动时自动加载
 * 
 * @author 吴智俊
 */
@Component
public class EwcmsDataSourceFactory implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsDataSourceFactory.class);
    
    @Autowired
    private BeanForInterfaceFactory factory = null;
    private Map<String,String> beanForInterfaceMappings = null;
    
    public BeanForInterfaceFactory getFactory() {
        return factory;
    }

    public void setFactory(BeanForInterfaceFactory factory) {
        this.factory = factory;
    }

    public Map<String,String> getBeanForInterfaceMappings() {
        return beanForInterfaceMappings;
    }

    public void setBeanForInterfaceMappings(Map<String,String> beanForInterfaceMappings) {
        this.beanForInterfaceMappings = beanForInterfaceMappings;
    }

    private static Map<String, String> serviceDefinitionMap;

    static {
        serviceDefinitionMap = new HashMap<String, String>();

        serviceDefinitionMap.put(JdbcDs.class.getName().toString(), "jdbcDataSourceFactory");
        serviceDefinitionMap.put(JndiDs.class.getName().toString(), "jndiDataSourceFactory");
        serviceDefinitionMap.put(BeanDs.class.getName().toString(), "beanDataSourceFactory");
        serviceDefinitionMap.put(CustomDs.class.getName().toString(), "customDataSourceFactory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    	logger.info("EwcmsDataSourceServiceFactory Properties begin......");
        setBeanForInterfaceMappings(serviceDefinitionMap);
        logger.info("EwcmsDataSourceServiceFactory Properties end");
    }
    
    @SuppressWarnings("rawtypes")
	public Object getBean(Class itfClass) {
        return factory.getBean(getBeanForInterfaceMappings(), itfClass);
    }

    @SuppressWarnings("rawtypes")
    public String getBeanName(Class itfClass) {
        return factory.getBeanName(getBeanForInterfaceMappings(), itfClass);
    }

}
