package com.ewcms.personnel.acadcategory.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.personnel.acadcategory.repository.AcadCategoryRepository;
import com.ewcms.personnel.category.entity.AcadCategory;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;
import com.google.common.collect.Lists;

/**
 * 
 *  
 * @author zhoudongchu
 */
@Service
public class AcadCategoryService extends BaseService<AcadCategory, Long> {


	
	private AcadCategoryRepository getAcadCategoryRepository(){
		return (AcadCategoryRepository) baseRepository;
	}
	@Autowired
	private OrganizationService organizationService;
	public AcadCategory findByName(String name){
		return getAcadCategoryRepository().findByName(name);
	}

	@Override
	public AcadCategory save(AcadCategory acadCategory) {
		getOrganizations(acadCategory);
		return super.save(acadCategory);
	}

	@Override
	public AcadCategory update(AcadCategory acadCategory) {
		getOrganizations(acadCategory);
		return super.update(acadCategory);
	}
	
	private void getOrganizations(AcadCategory acadCategory){
		String[] arr= acadCategory.getSelectOrganizationIds().split(",");
		List<String> organizationIds = Arrays.asList(arr);
		List<Organization> organizations = Lists.newArrayList();
		for(String id:organizationIds){
			try{
				Organization vo =organizationService.findOne(Long.valueOf(id));
				organizations.add(vo);
			}catch(Exception e){}
		}
		acadCategory.setOrganizations(organizations);
	}
	
}

