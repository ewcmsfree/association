package com.ewcms.system.externalds.generate.factory.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ewcms.common.exception.BaseException;
import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.entity.JndiDs;
import com.ewcms.system.externalds.generate.factory.DataSourceFactoryable;
import com.ewcms.system.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.system.externalds.generate.service.jdbc.JdbcDataSourceService;

/**
 * Jndi数据源工厂模式
 *
 * @author 吴智俊
 */
@Component(value = "jndiDataSourceFactory")
public class JndiDataSourceFactory implements DataSourceFactoryable {

	private static final Logger logger = LoggerFactory.getLogger(JndiDataSourceFactory.class);
    private Context ctx = null;

    public JndiDataSourceFactory() {
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            logger.error("NamingException", e);
            throw new BaseException(e.getMessage());
        }
    }

    @Override
    public EwcmsDataSourceServiceable createService(BaseDs alqcDataSource) {
        try {
            if (!(alqcDataSource instanceof JndiDs)) {
            	logger.error("无效的Jndi数据源");
                throw new BaseException("无效的Jndi数据源", new Object[]{alqcDataSource.getClass()});
            }
            JndiDs jndiDataSource = (JndiDs) alqcDataSource;

            String jndiName = jndiDataSource.getJndiName();

            DataSource ds = (DataSource) ctx.lookup(jndiName);
            return new JdbcDataSourceService(ds);
        } catch (NamingException e) {
            logger.error("NamingException", e);
            throw new BaseException(e.getMessage());
        }
    }
}
