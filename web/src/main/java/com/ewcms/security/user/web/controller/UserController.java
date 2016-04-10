package com.ewcms.security.user.web.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/user")
public class UserController extends BaseCRUDController<User, Long> {

	private UserService getUserService() {
		return (UserService) baseService;
	}

	public UserController() {
		setListAlsoSetCommonData(true);
		setResourceIdentity("security:user");
	}

	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("statusList", UserStatus.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") User m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (m.getId() != null){
			User dbUser = baseService.findOne(m.getId());
			m.setOrganizationJobs(dbUser.getOrganizationJobs());
		}
		m.setIsRegister(false);
		return super.save(model, m, result, selections);
	}

	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<User> canUseUser() {
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("deleted", SearchOperator.EQ, Boolean.FALSE);
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
	}

	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable("newStatus") UserStatus newStatus,
			@RequestParam("selections") List<Long> selections,
			@RequestParam("reason") String reason, @CurrentUser User opUser) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getUserService().changeStatus(opUser, selections, newStatus, reason);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "changePassword")
	@ResponseBody
	public AjaxResponse changePassword(@RequestParam("selections") List<Long> selections,
			@RequestParam("newPassword") String newPassword,
			@CurrentUser User opUser) {
		AjaxResponse ajaxResponse = new AjaxResponse("修改密码成功！");
		
		try{
			getUserService().changePassword(opUser, selections, newPassword);
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("修改密码失败了！");
		}
		return ajaxResponse;
	}

	@RequestMapping(value = "recycle")
	@ResponseBody
	public AjaxResponse recycle(@RequestParam("selections") List<Long> selections) {
		AjaxResponse ajaxResponse = new AjaxResponse("还原成功！");
		
		try{
			for (Long id : selections) {
				User user = getUserService().findOne(id);
				user.setDeleted(Boolean.FALSE);
				getUserService().update(user);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("还原失败了！");
		}
		return ajaxResponse;
	}

    /**
     * 验证返回格式
     * 单个：[fieldId, 1|0, msg]
     * 多个：[[fieldId, 1|0, msg],[fieldId, 1|0, msg]]
     *
     * @param fieldId
     * @param fieldValue
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();

        if ("username".equals(fieldId)) {
            User user = getUserService().findByUsername(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getUsername().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "用户名已被其他人使用");
            }
        }

        if ("email".equals(fieldId)) {
            User user = getUserService().findByEmail(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getEmail().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "邮箱已被其他人使用");
            }
        }

        if ("mobilePhoneNumber".equals(fieldId)) {
            User user = getUserService().findByMobilePhoneNumber(fieldValue);
            if (user == null || (user.getId().equals(id) && user.getMobilePhoneNumber().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "手机号已被其他人使用");
            }
        }

        return response.result();
    }
    
    
}
