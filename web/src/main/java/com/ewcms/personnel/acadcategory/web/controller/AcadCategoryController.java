package com.ewcms.personnel.acadcategory.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.personnel.acadcategory.service.AcadCategoryService;
import com.ewcms.personnel.category.entity.AcadCategory;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.service.OrganizationService;

/**
 * 
 * 
 * @author zhoudongchu
 */
@Controller
@RequestMapping(value = "/personnel/acadCategory")
public class AcadCategoryController extends BaseCRUDController<AcadCategory, Long> {
	
	@Autowired
	private OrganizationService organizationService;
	
	private AcadCategoryService getAcadCategoryService(){
		return (AcadCategoryService) baseService;
	}
	
	public AcadCategoryController() {
		setResourceIdentity("personnel:acadCategory");
	}
	
	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Organization> query(@RequestParam(value = "categoryId",required = false) Long categoryId) {
		if(categoryId == null){
			Searchable searchable = Searchable.newSearchable();
			
			searchable.addSearchFilter("parentIds", SearchOperator.NE, "0/");
			searchable.addSort(Direction.ASC, "weight");
			
			return organizationService.findAllWithSort(searchable);
		}
		AcadCategory vo = getAcadCategoryService().findOne(categoryId);
		return vo.getOrganizations();
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		searchParameter.getSorts().put("id", Direction.DESC);
		return super.query(searchParameter, model);
	}
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

        if ("name".equals(fieldId)) {
        	AcadCategory nation = getAcadCategoryService().findByName(fieldValue);
            if (nation == null|| (nation.getId().equals(id) && nation.getName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "学会分类名称已存在");
            }
        }
        return response.result();
    }	
    
}

