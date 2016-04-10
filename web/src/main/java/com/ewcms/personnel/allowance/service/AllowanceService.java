package com.ewcms.personnel.allowance.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personnel.allowance.entity.Allowance;
import com.ewcms.personnel.allowance.repository.AllowanceRepository;

@Service
public class AllowanceService extends BaseService<Allowance, Long>{

	private AllowanceRepository getAllowanceRepository(){
		return (AllowanceRepository) baseRepository;
	}
	
	public Allowance findByName(String name){
		return getAllowanceRepository().findByName(name);
	}
}
