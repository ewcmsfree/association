package com.ewcms.system.externalds.generate.factory.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ewcms.common.exception.BaseException;
import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.entity.JdbcDs;
import com.ewcms.system.externalds.generate.factory.DataSourceFactoryable;
import com.ewcms.system.externalds.generate.factory.dbcp.DbcpDataSourceFactory;
import com.ewcms.system.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.system.externalds.generate.service.dbcp.DbcpDataSource;
import com.ewcms.system.externalds.generate.service.jdbc.JdbcDataSourceService;

/**
 * Jdbc数据源工厂模式
 * 
 * @author 吴智俊
 */
@Component(value = "jdbcDataSourceFactory")
public class JdbcDataSourceFactory implements DataSourceFactoryable {

	private static final Logger logger = LoggerFactory.getLogger(JdbcDataSourceFactory.class);

    protected static class PooledDataSourcesCache {

        protected static class DataSourceEntry {

            final Object key;
            final DbcpDataSource ds;
            DataSourceEntry next, prev;
            long lastAccess;

            public DataSourceEntry(Object key, DbcpDataSource ds) {
                this.key = key;
                this.ds = ds;
            }

            public void access(long now) {
                lastAccess = now;
            }
        }
        final Map<Object, DataSourceEntry> cache;
        DataSourceEntry first, last;

        public PooledDataSourcesCache() {
            cache = new HashMap<Object, DataSourceEntry>();
            first = last = null;
        }

        public DbcpDataSource get(Object key, long now) {
            DataSourceEntry entry = (DataSourceEntry) cache.get(key);

            if (entry == null) {
                return null;
            }

            moveFirst(entry);
            entry.access(now);
            return entry.ds;
        }

        private void moveFirst(DataSourceEntry entry) {
            entry.next = first;
            entry.prev = null;
            if (first != null) {
                first.prev = entry;
            }
            first = entry;
            if (last == null) {
                last = entry;
            }
        }

        public void put(Object key, DbcpDataSource ds, long now) {
            DataSourceEntry entry = new DataSourceEntry(key, ds);
            moveFirst(entry);
            entry.access(now);
            cache.put(key, entry);
        }

        public List<DbcpDataSource> removeExpired(long now, int timeout) {
            List<DbcpDataSource> expired = new ArrayList<DbcpDataSource>();
            DataSourceEntry entry = last;
            long expTime = now - timeout * 1000;
            while (entry != null && entry.lastAccess < expTime) {
                expired.add(entry.ds);
                remove(entry);
                entry = entry.prev;
            }
            return expired;
        }

        protected void remove(DataSourceEntry entry) {
            cache.remove(entry.key);
            if (entry.prev != null) {
                entry.prev.next = entry.next;
            }
            if (entry.next != null) {
                entry.next.prev = entry.prev;
            }
            if (first == entry) {
                first = entry.next;
            }
            if (last == entry) {
                last = entry.prev;
            }
        }
    }
    @Autowired
    private DbcpDataSourceFactory dbcpDataSourceFactory;
    private PooledDataSourcesCache poolDataSources;
    private int poolTimeout;

    public JdbcDataSourceFactory() {
        poolDataSources = new PooledDataSourcesCache();
    }

    public void setDbcpDataSourceFactory(
            DbcpDataSourceFactory dbcpDataSourceFactory) {
        this.dbcpDataSourceFactory = dbcpDataSourceFactory;
    }

    public DbcpDataSourceFactory getDbcpDataSourceFactory() {
        return dbcpDataSourceFactory;
    }

    @Override
    public EwcmsDataSourceServiceable createService(BaseDs alqcDataSource) {
        if (!(alqcDataSource instanceof JdbcDs)) {
        	logger.error("无效的Jdbc数据库");
            throw new BaseException("无效的Jdbc数据库", new Object[]{alqcDataSource.getClass()});
        }
        JdbcDs jdbcDataSet = (JdbcDs) alqcDataSource;

        DataSource dataSource = getPoolDataSource(jdbcDataSet.getDriver(), jdbcDataSet.getConnUrl(), jdbcDataSet.getUserName(), jdbcDataSet.getPassWord());
        return new JdbcDataSourceService(dataSource);
    }

    protected DataSource getPoolDataSource(String driverClass, String url, String username, String password) {
        Object poolKey = createJdbcPoolKey(driverClass, url, username, password);
        DbcpDataSource dataSource;
        List<DbcpDataSource> expired = null;
        long now = System.currentTimeMillis();
        synchronized (poolDataSources) {
            dataSource = poolDataSources.get(poolKey, now);
            if (dataSource == null) {
                logger.info("建立Pool数据连接 driver=\"" + driverClass + "\", url=\"" + url + "\", username=\"" + username + "\".");
                dataSource = dbcpDataSourceFactory.createPooledDataSource(driverClass, url, username, password);
                poolDataSources.put(poolKey, dataSource, now);
            }

            if (getPoolTimeout() > 0) {
                expired = poolDataSources.removeExpired(now, getPoolTimeout());
            }
        }

        if (expired != null && !expired.isEmpty()) {
            for (DbcpDataSource ds : expired) {
                try {
                    ds.release();
                } catch (Exception e) {
                    logger.error("Error while releasing connection pool.", e);
                }
            }
        }

        return dataSource.getDataSource();
    }

    protected Object createJdbcPoolKey(String driverClass, String url, String username, String password) {
        return new JdbcPoolKey(driverClass, url, username, password);
    }

    protected static class JdbcPoolKey {

        private final String driverClass;
        private final String url;
        private final String username;
        private final String password;
        private final int hash;

        public JdbcPoolKey(String driverClass, String url, String username, String password) {
            this.driverClass = driverClass;
            this.url = url;
            this.username = username;
            this.password = password;

            int hashCode = 559;
            if (driverClass != null) {
                hashCode += driverClass.hashCode();
            }
            hashCode *= 43;
            if (url != null) {
                hashCode += url.hashCode();
            }
            hashCode *= 43;
            if (username != null) {
                hashCode += username.hashCode();
            }
            hashCode *= 43;
            if (password != null) {
                hashCode += password.hashCode();
            }

            hash = hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof JdbcPoolKey)) {
                return false;
            }
            if (this == obj) {
                return true;
            }

            JdbcPoolKey key = (JdbcPoolKey) obj;
            return (driverClass == null ? key.driverClass == null : (key.driverClass != null && driverClass.equals(key.driverClass))) && (url == null ? key.url == null : (key.url != null && url.equals(key.url))) && (username == null ? key.username == null : (key.username != null && username.equals(key.username))) && (password == null ? key.password == null : (key.password != null && password.equals(key.password)));
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    public int getPoolTimeout() {
        return poolTimeout;
    }

    public void setPoolTimeout(int poolTimeout) {
        this.poolTimeout = poolTimeout;
    }
}
