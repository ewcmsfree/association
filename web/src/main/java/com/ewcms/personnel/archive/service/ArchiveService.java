package com.ewcms.personnel.archive.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.personnel.acadcategory.service.AcadCategoryService;
import com.ewcms.personnel.archive.entity.Archive;
import com.ewcms.personnel.archive.entity.ArchiveStatus;
import com.ewcms.personnel.archive.entity.ArchiveSummary;
import com.ewcms.personnel.archive.repository.ArchiveRepository;
import com.ewcms.personnel.category.entity.AcadCategory;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class ArchiveService extends BaseService<Archive, Long>{
	
	private ArchiveRepository getArchiveRepository(){
		return (ArchiveRepository) baseRepository;
	}
	
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private AcadCategoryService acadCategoryService;
	@Autowired
	private OrganizationService organizationService;
	
	public Archive findByUserId(Long userId){
		return getArchiveRepository().findByUserId(userId);
	}

	public void updateArchiveStatusToUseredit(Long userId){
		Archive archive = findByUserId(userId);
		if (archive != null){
			archive.setStatus(ArchiveStatus.useredit);
			update(archive);
		}
	}

	public void covertSearchParameter(User user, SearchParameter<Long> searchParameter) {
		if (EmptyUtil.isNull(user) || user.getIsRegister()) {
			searchParameter.getParameters().put("EQ_organizations|id", -100L);
		} else {
			// 非注册用户
			if (!user.getIsRegister()) {
				// 非管理员用户，相当于当前组织机构管理用户
				if (!user.getAdmin()){
					Set<Long> organizationIds = userOrganizationJobService.findUserOrganizationJobAllOrganizationId(user);
					List<Long> lists = Lists.newArrayList(organizationIds.iterator());
					
					if (EmptyUtil.isCollectionEmpty(organizationIds)) {
						searchParameter.getParameters().put("EQ_organizations|id", -100L);
					} else {
						searchParameter.getParameters().put("IN_organizations|id", lists);
					}
				} else {
					List<Long> organizationIds = Lists.newArrayList();
					String customOrganizationId = (String)searchParameter.getParameters().get("CUSTOM_organizationsIds");
					if (EmptyUtil.isStringNotEmpty(customOrganizationId)){
						String[] customOrganizationIds = customOrganizationId.split(",");
						for (String id : customOrganizationIds){
							organizationIds.add(Long.parseLong(id));
						}
					}
					
					String categoryId = (String) searchParameter.getParameters().get("CUSTOM_category");
					if (EmptyUtil.isStringNotEmpty(categoryId)){
						AcadCategory acadCategory = acadCategoryService.findOne(Long.parseLong(categoryId));
						if (EmptyUtil.isNotNull(acadCategory)){
							organizationIds.addAll(acadCategory.getOrganizationIds());
						}
					}
					
					if (EmptyUtil.isCollectionNotEmpty(organizationIds)){
						searchParameter.getParameters().put("IN_organizations|id", organizationIds);
					} 
				}
			} else {
				searchParameter.getParameters().put("EQ_organization|id", -100L);
			}
		}
		
		
	}
	
	public String findCountArchive(){
		Long total = count();
		Long countUserEdit = count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.useredit));
		Long countSubmitThrough = count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.submitthrough));
		Long countThrough = count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.through));
		Long countNoThrough = count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.nothrough));
		
		return String.format("现已登记人数：%d，其中未提交审核人数：%d，已提交审核人数：%d，审核通过人数：%d，未审核通过人数：%d", total, countUserEdit, countSubmitThrough, countThrough, countNoThrough);
	}
	
	public Long totalArchive(){
		return count();
	}
	
	public Map<String, Long> archiveChart(){
		Map<String, Long> map = Maps.newHashMap();
		
		map.put("未提交审核", count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.useredit)));
		map.put("已提交审核", count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.submitthrough)));
		map.put("审核通过", count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.through)));
		map.put("未审核通过", count(Searchable.newSearchable().addSearchFilter("status", SearchOperator.EQ, ArchiveStatus.nothrough)));
		
		return map;
	}
	
	public Map<String, Object> findTopRowSubmitThroughArchive(User user, SearchParameter<Long> searchParameter) {
		Map<String, Object> resultMap = Maps.newHashMap();
		
		//if (!user.getAdmin() && user.getIsRegister()) return resultMap;
		
		List<ArchiveSummary> archiveSummaries = Lists.newArrayList();
		searchParameter.getParameters().put("NE_parentId", 0L);
	
		//if (user.getAdmin()){
			Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, Organization.class);
			searchable.addSort(Direction.ASC, "weight");
			Page<Organization> organizationPages = organizationService.findAll(searchable);
			List<Organization> organizations = organizationPages.getContent();
			
			ArchiveSummary archiveSummary = null;
			for (Organization organization : organizations){
				Long total = getArchiveRepository().countByOrganizationsIn(organization);
				Long throughCount = getArchiveRepository().countByStatusAndOrganizationsIn(ArchiveStatus.through, organization);
				
				archiveSummary = new ArchiveSummary(organization.getId(), organization.getName(), total, throughCount);
				
				archiveSummaries.add(archiveSummary);
			}
			
			
			resultMap.put("total", organizationPages.getTotalElements());
			resultMap.put("rows", archiveSummaries);
		//}
		
		return resultMap;
	}
}
