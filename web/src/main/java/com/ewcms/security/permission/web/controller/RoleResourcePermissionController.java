package com.ewcms.security.permission.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.permission.entity.Role;
import com.ewcms.security.permission.entity.RoleResourcePermission;
import com.ewcms.security.permission.service.PermissionService;
import com.ewcms.security.permission.service.RoleResourcePermissionService;
import com.ewcms.security.permission.service.RoleService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/permission/roleResourcePermission")
public class RoleResourcePermissionController extends BaseCRUDController<RoleResourcePermission, Long>{
	
	private RoleResourcePermissionService getRoleResourcePermissionService(){
		return (RoleResourcePermissionService) baseService;
	}
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
    @Value("${full.path.separator.mark}")
    private String fullPathSeparator;
    
	public RoleResourcePermissionController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:role");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("permissions", permissionService.findAll());
	}

	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{roleId}/index")
	public String index(@PathVariable(value = "roleId")Long roleId, Model model){
		return super.index(model);
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{roleId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "roleId")Long roleId){
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, RoleResourcePermission.class);
		searchable.addSearchFilter("role.id", SearchOperator.EQ, roleId);
		searchable.addSort(Direction.ASC, "id");
		
		return getRoleResourcePermissionService().findRoleResourcePermissionFullNames(searchable, fullPathSeparator);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{roleId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "roleId")Long roleId, Model model, @RequestParam(required = false) List<Long> selections){
		return super.showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") RoleResourcePermission m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{roleId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "roleId") Long roleId, Model model, @Valid @ModelAttribute("m") RoleResourcePermission m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		setCommonData(model);
		
		if (hasError(m, result)) {
            return showSaveForm(roleId, model, selections);
        }

		Role role = roleService.findOne(roleId);
		role.addResourcePermission(m);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        RoleResourcePermission lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        RoleResourcePermission lastM = baseService.save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(roleId, model, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{roleId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections, @PathVariable(value = "roleId") Long roleId){
		return super.delete(selections);
	}
}
