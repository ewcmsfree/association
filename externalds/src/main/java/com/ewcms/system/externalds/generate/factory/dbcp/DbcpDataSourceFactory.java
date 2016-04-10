package com.ewcms.system.externalds.generate.factory.dbcp;

import org.apache.commons.pool.ObjectPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ewcms.common.exception.BaseException;
import com.ewcms.system.externalds.generate.service.dbcp.DbcpDataSource;

/**
 * 
 * @author 吴智俊
 */
@Component(value = "dbcpDataSourceFactory")
public class DbcpDataSourceFactory {

	private static final Logger logger = LoggerFactory.getLogger(DbcpDataSourceFactory.class);
	
    @Autowired
    private ObjectPoolFactory objectPoolFactory;

    public void setObjectPoolFactory(ObjectPoolFactory genericObjectPoolFactory) {
        this.objectPoolFactory = genericObjectPoolFactory;
    }

    public ObjectPoolFactory getObjectPoolFactory() {
        return objectPoolFactory;
    }

    public DbcpDataSource createPooledDataSource(String driverClass, String url, String username, String password) {
        registerDriver(driverClass);
        return new DbcpDataSource(objectPoolFactory, url, username, password);
    }

    /**
     * 注册数据源驱动
     * @param driverClass
     */
    protected void registerDriver(String driverClass) {
        try {
            Class.forName(driverClass, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
        	logger.error("ClassNotFoundException", e);
            throw new BaseException(e.getMessage());
        }
    }
}
