package com.ewcms.system.externalds.generate.factory;

import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.generate.service.EwcmsDataSourceServiceable;


/**
 * 数据源工厂接口
 *
 * @author 吴智俊
 */
public interface DataSourceFactoryable {

	/**
	 * 建立数据源服务
	 * 
	 * @param dataSource 
	 * @return AlqcDataSourceServiceable
	 */
    public EwcmsDataSourceServiceable createService(BaseDs dataSource);
}
