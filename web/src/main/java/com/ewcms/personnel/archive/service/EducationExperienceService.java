package com.ewcms.personnel.archive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.personnel.archive.entity.EducationExperience;

@Service
public class EducationExperienceService extends BaseService<EducationExperience, Long>{

	@Autowired
	private ArchiveService archiveService;
	
	@Override
	public EducationExperience save(EducationExperience m) {
		archiveService.updateArchiveStatusToUseredit(m.getUserId());
		return super.save(m);
	}
	
	@Override
	public EducationExperience update(EducationExperience m) {
		archiveService.updateArchiveStatusToUseredit(m.getUserId());
		return super.update(m);
	}
	
	@Override
	public void delete(List<Long> ids) {
		if (EmptyUtil.isCollectionNotEmpty(ids) && ids.size() > 0){
			Long id = ids.get(0);
			EducationExperience m = findOne(id);
			archiveService.updateArchiveStatusToUseredit(m.getUserId());
		}
		super.delete(ids);
	}
}
