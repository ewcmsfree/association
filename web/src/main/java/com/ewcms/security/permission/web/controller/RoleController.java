package com.ewcms.security.permission.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.enums.AvailableEnum;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.service.PermissionService;
import com.ewcms.security.permission.service.RoleService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/permission/role")
public class RoleController extends BaseCRUDController<Role, Long> {
	
    @Autowired
    private PermissionService permissionService;

    private RoleService getRoleService() {
        return (RoleService) baseService;
    }
	
	public RoleController(){
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:role");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("availableList", AvailableEnum.values());
		model.addAttribute("permissions", permissionService.findAll());
	}
	
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") Role m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (m.getId() != null){
			Role dbRole = baseService.findOne(m.getId());
			m.setResourcePermissions(dbRole.getResourcePermissions());
		}
		return super.save(model, m, result, selections);
	}
	
	@RequestMapping(value = "permission")
	@ResponseBody
	public List<ComboBox> getPermission(@RequestParam(value = "id") Long roleId){
		return getRoleService().findPermission(roleId);
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(@RequestParam("fieldId") String fieldId, 
    		@RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();
        
        if ("name".equals(fieldId)) {
            Role role = getRoleService().findByName(fieldValue);
            if (role == null || (role.getId().equals(id) && role.getName().equals(fieldValue))) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "角色名已存在，请输入其他名字");
            }
        }
        
        return response.result();
    }
	
	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Role> canUseRole() {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("show", SearchOperator.EQ, Boolean.TRUE);
		searchable.addSort(Direction.ASC, "id");
		return baseService.findAllWithSort(searchable);
	}
	
//	@RequestMapping(value = "{roleId}/index")
//	public String roleResourcePermissionIndex(@PathVariable(value = "roleId") Long roleId, Model model){
//		setCommonData(model);
//		return viewName("resourcePermission/index");
//	}
//	
//	@RequestMapping(value = "{roleId}/query")
//	public Map<String, Object> roleResourcePermissionQuery(@PathVariable(value = "roleId") Long roleId, @ModelAttribute SearchParameter<Long> searchParameter){
//		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, RoleResourcePermission.class);
//		searchable.addSearchFilter("role.id", SearchOperator.EQ, roleId);
//		searchable.addSort(Direction.ASC, "id");
//		
//		List<RoleResourcePermission> roleResourcePermissions = getRoleService().findRoleResourcePermissionFullNames(searchable, ",");
//		
//		Map<String, Object> resultMap = new HashMap<String, Object>(2);
//		resultMap.put("total", roleResourcePermissions.size());
//		resultMap.put("rows", roleResourcePermissions);
//		return resultMap;
//	}
}