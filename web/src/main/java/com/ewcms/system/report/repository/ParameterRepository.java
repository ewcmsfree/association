package com.ewcms.system.report.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.report.entity.Parameter;

/**
 * 
 * @author wu_zhijun
 * 
 */
public interface ParameterRepository extends BaseRepository<Parameter, Long> {
	
	Long countByIdAndDefaultValue(Long id, String defaultValue);
}
