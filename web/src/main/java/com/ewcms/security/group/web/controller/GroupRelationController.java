package com.ewcms.security.group.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupRelation;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.service.GroupRelationService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/group/groupRelation")
public class GroupRelationController extends BaseCRUDController<GroupRelation, Long>{
	
	private GroupRelationService getGroupRelationService(){
		return (GroupRelationService)baseService;
	}
	
	public GroupRelationController() {
		setResourceIdentity("security:group");
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{group}/index")
	public String index(@PathVariable(value = "group")Group group, Model model){
		return super.index(model);
	}
	
	@RequestMapping(value = "query/discard")
	@ResponseBody
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{groupId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "groupId") Long groupId){
		Map<String, Object> resultMap = new HashMap<String, Object>(2);

		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, Group.class);
		searchable.addSearchFilter("groupId", SearchOperator.EQ, groupId);
		searchable.addSort(Direction.ASC, "id");
		
		searchable.setPage(searchParameter.getPage() - 1, searchParameter.getRows());
		
		Page<GroupRelation> groupRelations = baseService.findAll(searchable);
		
		resultMap.put("total", groupRelations.getTotalElements());
		resultMap.put("rows", groupRelations.getContent());
		return resultMap;
	}

	@RequestMapping(value = "{groupId}/save", method = RequestMethod.GET)
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections, @PathVariable(value = "groupId") Group group){
		model.addAttribute("group", group);
		return super.showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "{groupId}/save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") GroupRelation m, BindingResult result, @RequestParam(required = false) List<Long> selections, @PathVariable(value = "groupId") Group group) {
		model.addAttribute("group", group);
		m.setGroupId(group.getId());
		return super.save(model, m, result, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{groupId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections, @PathVariable(value = "groupId") Long groupId){
		return super.delete(selections);
	}
	
    @RequestMapping(value = "{groupId}/validate")
    @ResponseBody
    public Object validate(@PathVariable("groupId")Group group, @ModelAttribute("m") GroupRelation m) {
        ValidateResponse response = ValidateResponse.newInstance();
    	if (group.getType() == GroupType.user){
    		GroupRelation groupRelation = getGroupRelationService().findByGroupIdAndUserId(group.getId(), m.getUserId());
    		if (groupRelation == null || (groupRelation.getId().equals(m.getId()) && groupRelation.getUserId().equals(m.getUserId()))) {
                response.validateSuccess("username", "");
            } else {
                response.validateFail("username", "用户名已存在，请重新选择");
            }
    	}
    	
    	if (group.getType() == GroupType.organization){
    		GroupRelation groupRelation = getGroupRelationService().findByGroupIdAndOrganizationId(group.getId(), m.getOrganizationId());
    		if (groupRelation == null || (groupRelation.getId().equals(m.getId()) && groupRelation.getOrganizationId().equals(m.getOrganizationId()))){
    			response.validateSuccess("organizationName", "");
            } else {
                response.validateFail("organizationName", "组织机构已存在，请重新选择");
            }
    	}
        
        return response.result();
    }
}
