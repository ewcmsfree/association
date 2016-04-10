package com.ewcms.personnel.currentstate.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personnel.currentstate.entity.CurrentState;
import com.ewcms.personnel.currentstate.repository.CurrentStateRepository;

@Service
public class CurrentStateService extends BaseService<CurrentState, Long>{

	private CurrentStateRepository getCurrentStateRepository(){
		return (CurrentStateRepository) baseRepository;
	}
	
	public CurrentState findByName(String name){
		return getCurrentStateRepository().findByName(name);
	}
}
