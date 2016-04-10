package com.ewcms.personnel.nation.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.nation.entity.Nation;

public interface NationRepository extends BaseRepository<Nation, Long>{

	Nation findByName(String name);
}
