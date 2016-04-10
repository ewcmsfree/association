package com.ewcms.system.externalds.generate.service.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.exception.BaseException;
import com.ewcms.system.externalds.generate.service.BaseDataSourceServiceable;

/**
 * JDBC数据库连接服务
 * 
 * @author 吴智俊
 */
public class JdbcDataSourceService extends BaseDataSourceServiceable {

	private static final Logger logger = LoggerFactory.getLogger(JdbcDataSourceService.class);
	
    private final DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcDataSourceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Connection createConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Jdbc建立数据库连接时错误.", e);
            throw new BaseException("建立数据库连接时错误.", e.getMessage());
        }
    }
}
