package com.ewcms.system.externalds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.externalds.entity.BaseDs;

@Service
public class BaseDsService extends BaseService<BaseDs, Long>{
	
	public Iterable<BaseDs> findAllBaseDs() {
		Iterable<BaseDs> baseDss = findAll();
		List<BaseDs> newBaseDss = new ArrayList<BaseDs>();
		
		BaseDs newBaseDs = new BaseDs();
		newBaseDs.setId(0L);
		newBaseDs.setName("默认数据源");
		newBaseDss.add(newBaseDs);
		
		for (BaseDs baseDs : baseDss){
			newBaseDss.add(baseDs);
		}
		
		return newBaseDss;
	}
}
