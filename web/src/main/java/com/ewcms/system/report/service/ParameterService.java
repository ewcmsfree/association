package com.ewcms.system.report.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.repository.ParameterRepository;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class ParameterService extends BaseService<Parameter, Long>{

	private ParameterRepository getParameterRepository(){
		return (ParameterRepository) baseRepository;
	}
	
	public Boolean findSessionIsEntityByParameterIdAndUserName(Long parameterId, String userName) {
		return getParameterRepository().countByIdAndDefaultValue(parameterId, userName) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
}
