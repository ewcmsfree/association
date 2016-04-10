package com.ewcms.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;

@Controller
public class RegisterFormController extends BaseController<User, Long>{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showSaveForm(Model model){
		if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") User m, BindingResult result, RedirectAttributes redirectAttributes){
		if (hasError(m, result)) {
            return showSaveForm(model);
        }
		
		m.setIsRegister(true);
		userService.save(m);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "注册成功，请重新登录！");
		return redirectToUrl("login");
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
    @RequestMapping(value = "/register/validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue) {

        ValidateResponse response = ValidateResponse.newInstance();


        if ("username".equals(fieldId)) {
            User user = userService.findByUsername(fieldValue);
            if (user == null) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "用户名已被其他人使用");
            }
        }

        if ("email".equals(fieldId)) {
            User user = userService.findByEmail(fieldValue);
            if (user == null) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "邮箱已被其他人使用");
            }
        }

        if ("mobilePhoneNumber".equals(fieldId)) {
            User user = userService.findByMobilePhoneNumber(fieldValue);
            if (user == null) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "手机号已被其他人使用");
            }
        }

        return response.result();
    }
}
