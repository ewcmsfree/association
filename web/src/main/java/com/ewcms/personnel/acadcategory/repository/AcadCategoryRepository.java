package com.ewcms.personnel.acadcategory.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.category.entity.AcadCategory;

/**
 * 
 *  
 * @author zhoudongchu
 */
public interface AcadCategoryRepository extends BaseRepository<AcadCategory, Long>{
	AcadCategory findByName(String name);
}

