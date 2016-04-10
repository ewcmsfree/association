package com.ewcms.security.permission.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.enums.AvailableEnum;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.permission.entity.Permission;
import com.ewcms.security.permission.service.PermissionService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/permission/permission")
public class PermissionController extends BaseCRUDController<Permission, Long> {
	
	private PermissionService getPermissionService(){
		return (PermissionService) baseService;
	}
	
	public PermissionController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:permission");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("availableList", AvailableEnum.values());
	}
	
	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable("newStatus") Boolean newStatus, @RequestParam("selections") List<Long> selections){
		
		this.permissionList.assertHasUpdatePermission();
		
		AjaxResponse ajaxResponse = new AjaxResponse("改变状态成功！");
		
		try{
			for (Long id : selections){
				Permission permission = baseService.findOne(id);
				permission.setShow(newStatus);
				baseService.update(permission);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("改变状态失败了！");
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();
        
        if ("name".equals(fieldId)) {
            Permission permission = getPermissionService().findByName(fieldValue);
            if (permission == null || (permission.getId().equals(id) && permission.getName().equals(fieldValue))) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "权限名已存在，请输入其他名字");
            }
        }
        
        return response.result();
    }
}
