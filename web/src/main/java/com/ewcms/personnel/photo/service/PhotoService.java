package com.ewcms.personnel.photo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personnel.archive.service.ArchiveService;
import com.ewcms.personnel.photo.entity.Photo;
import com.ewcms.personnel.photo.repository.PhotoRepository;

@Service
public class PhotoService extends BaseService<Photo, Long>{
	
	private PhotoRepository getPhotoRepository(){
		return (PhotoRepository) baseRepository;
	}
	
	@Autowired
	private ArchiveService archiveService;
	
	public Photo findByUserId(Long userId){
		return getPhotoRepository().findByUserId(userId);
	}
	
	@Override
	public Photo save(Photo m) {
		archiveService.updateArchiveStatusToUseredit(m.getUserId());
		return super.save(m);
	}
	
	@Override
	public Photo update(Photo m) {
		archiveService.updateArchiveStatusToUseredit(m.getUserId());
		return super.update(m);
	}
}
