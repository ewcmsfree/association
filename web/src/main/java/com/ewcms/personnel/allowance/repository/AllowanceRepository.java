package com.ewcms.personnel.allowance.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.allowance.entity.Allowance;

public interface AllowanceRepository extends BaseRepository<Allowance, Long>{
	
	Allowance findByName(String name);
}
