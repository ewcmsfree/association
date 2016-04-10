package com.ewcms.personnel.archive.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.personnel.archive.entity.Archive;
import com.ewcms.personnel.archive.entity.ArchiveStatus;
import com.ewcms.security.organization.entity.Organization;

public interface ArchiveRepository extends BaseRepository<Archive, Long> {
	
	Archive findByUserId(Long userId);
	
	Long countByOrganizationsIn(Organization organization);
	
	Long countByStatusAndOrganizationsIn(ArchiveStatus status, Organization organization);
}
