package com.ewcms.personnel.photo.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.photo.entity.Photo;

public interface PhotoRepository extends BaseRepository<Photo, Long>{
	
	Photo findByUserId(Long userId);
}
