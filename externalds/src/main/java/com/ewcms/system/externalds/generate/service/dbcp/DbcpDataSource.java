package com.ewcms.system.externalds.generate.service.dbcp;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.exception.BaseException;

/**
 * @author 吴智俊
 */
public class DbcpDataSource {

	private static final Logger logger = LoggerFactory.getLogger(DbcpDataSource.class);
	
    private final ObjectPool connectionPool;
    private final PoolingDataSource dataSource;

    public DbcpDataSource(ObjectPoolFactory objectPoolFactory, String url, String username, String password) {
        connectionPool = createPool(objectPoolFactory);
        createPoolableConnectionFactory(url, username, password);

        dataSource = new PoolingDataSource(connectionPool);
    }

    protected ObjectPool createPool(ObjectPoolFactory objectPoolFactory) {
        ObjectPool objectPool = objectPoolFactory.createPool();
        return objectPool;
    }

    protected void createPoolableConnectionFactory(String url, String username, String password) {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, username, password);
        new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, true, false);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void release() {
        try {
            connectionPool.close();
        } catch (Exception e) {
            logger.error("关闭DBCP连接池时产生错误", e);
            throw new BaseException(e.getMessage());
        }
    }
}
