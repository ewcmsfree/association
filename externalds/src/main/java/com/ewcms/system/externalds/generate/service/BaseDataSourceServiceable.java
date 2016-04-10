package com.ewcms.system.externalds.generate.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.exception.BaseException;

/**
 * 基本数据源服务
 *
 * @author 吴智俊
 */
public abstract class BaseDataSourceServiceable implements EwcmsDataSourceServiceable {

	private static final Logger logger = LoggerFactory.getLogger(BaseDataSourceServiceable.class);
	
    private Connection conn;

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("关闭数据库时有错误产生.", e);
                throw new BaseException("", "关闭数据库时有错误产生.");
            }

            conn = null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("关闭使用的数据库连接池.");
        }
    }

    public Connection openConnection() {
        return createConnection();
    }

    protected abstract Connection createConnection();
}
