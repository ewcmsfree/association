package com.ewcms.personnel.archive.repository;

import java.util.List;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.archive.entity.EducationExperience;

public interface EducationExperienceRepository extends BaseRepository<EducationExperience, Long>{

	List<EducationExperience> findByUserId(Long userId);
}
