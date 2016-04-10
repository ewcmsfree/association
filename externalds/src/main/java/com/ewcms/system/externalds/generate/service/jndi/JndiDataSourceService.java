package com.ewcms.system.externalds.generate.service.jndi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.exception.BaseException;
import com.ewcms.system.externalds.generate.service.BaseDataSourceServiceable;

/**
 * JNDI数据库连接服务
 *
 * @author 吴智俊
 */
public class JndiDataSourceService extends BaseDataSourceServiceable {

	private static final Logger logger = LoggerFactory.getLogger(JndiDataSourceService.class);
	
    private final String jndiName;

    public JndiDataSourceService(String jndiName) {
        this.jndiName = jndiName;
    }

    protected Connection createConnection() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(jndiName);
            return ds.getConnection();
        } catch (NamingException e) {
            logger.error("NamingException", e);
            throw new BaseException("", "JND查找不到命名");
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new BaseException("", "数据源连接失败");
        }
    }
}
