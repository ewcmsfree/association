package com.ewcms.personnel.currentstate.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.currentstate.entity.CurrentState;

public interface CurrentStateRepository extends BaseRepository<CurrentState, Long>{
	
	CurrentState findByName(String name);
}
