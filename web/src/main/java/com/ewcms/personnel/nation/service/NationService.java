package com.ewcms.personnel.nation.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personnel.nation.entity.Nation;
import com.ewcms.personnel.nation.repository.NationRepository;

@Service
public class NationService extends BaseService<Nation, Long>{
	
	private NationRepository getNationRepository(){
		return (NationRepository) baseRepository;
	}
	
	public Nation findByName(String name){
		return getNationRepository().findByName(name);
	}
}
