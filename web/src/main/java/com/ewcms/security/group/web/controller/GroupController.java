package com.ewcms.security.group.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.group.entity.Group;
import com.ewcms.security.group.entity.GroupType;
import com.ewcms.security.group.service.GroupService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/group/group")
public class GroupController extends BaseCRUDController<Group, Long> {

	private GroupService getGroupService(){
		return (GroupService) baseService;
	}
	
	public GroupController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:group");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("types", GroupType.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}

	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(
			@PathVariable("newStatus") Boolean newStatus,
			@RequestParam("selections") List<Long> selections) {
		
		this.permissionList.assertHasUpdatePermission();
		
		AjaxResponse ajaxResponse = new AjaxResponse("改变状态成功！");

		try {
			for (Long id : selections) {
				Group group = baseService.findOne(id);
				group.setShow(newStatus);
				baseService.update(group);
			}
		} catch (IllegalStateException e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("改变状态失败了！");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "/changeDefaultGroup/{newStatus}")
	@ResponseBody
	public AjaxResponse changeDefaultGroup(
			@PathVariable("newStatus") Boolean newStatus,
			@RequestParam("selections") List<Long> selections) {

		this.permissionList.assertHasUpdatePermission();

		AjaxResponse ajaxResponse = new AjaxResponse("改变状态成功！");
		
		try{
			for (Long id : selections) {
				Group group = baseService.findOne(id);
				if (group.getType() != GroupType.user) {// 只有用户组 可设置为默认 其他无效
					continue;
				}
				group.setDefaultGroup(newStatus);
				baseService.update(group);
			}
		} catch (IllegalStateException e) {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("改变状态失败了！");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Group> canUseGroup(@RequestParam(value = "type", required = false) GroupType type) {
		Searchable searchable = Searchable.newSearchable();
		if (EmptyUtil.isNotNull(type)){
			searchable.addSearchFilter("type", SearchOperator.EQ, type);
		}
		searchable.addSearchFilter("show", SearchOperator.EQ, Boolean.TRUE);
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();
        
        if ("name".equals(fieldId)) {
            Group group = getGroupService().findByName(fieldValue);
            if (group == null || (group.getId().equals(id) && group.getName().equals(fieldValue))) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "分组名已存在，请输入其他名字");
            }
        }
        
        return response.result();
    }
}
