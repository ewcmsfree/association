package com.ewcms.security.user.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.utils.PatternUtils;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.PasswordService;
import com.ewcms.security.user.service.UserLastOnlineService;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 * 登录用户的个人信息
 * 
 * @author 吴智俊
 */
@Controller
@RequestMapping("/security/user/loginUser")
public class LoginUserController extends BaseController<User, Long> {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private UserLastOnlineService userLastOnlineService;

	@Override
	protected void setCommonData(Model model) {
		model.addAttribute("booleanList", BooleanEnum.values());
		model.addAttribute("statusList", UserStatus.values());
	}

	@RequestMapping("viewInfo")
	public String viewInfo(@CurrentUser User user, Model model) {
		setCommonData(model);
		user = userService.findOne(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("lastOnline", userLastOnlineService.findByUserId(user.getId()));
		return viewName("edit");
	}

	@RequestMapping(value = "updateInfo", method = RequestMethod.GET)
	public String updateInfoForm(@CurrentUser User user, Model model) {
		setCommonData(model);
		model.addAttribute("user", user);
		model.addAttribute("lastOnline", userLastOnlineService.findByUserId(user.getId()));
		return viewName("edit");
	}

	@RequestMapping(value = "updateInfo", method = RequestMethod.POST)
	public String updateInfo(@CurrentUser User user,
			@RequestParam("email") String email,
			@RequestParam("mobilePhoneNumber") String mobilePhoneNumber,
			Model model, RedirectAttributes redirectAttributes) {
		if (email == null || !email.matches(PatternUtils.EMAIL_PATTERN)) {
			model.addAttribute(Constants.ERROR, "请输入正确的邮箱地址");
			return updateInfoForm(user, model);
		}
		if (mobilePhoneNumber == null
				|| !mobilePhoneNumber.matches(PatternUtils.MOBILE_PHONE_NUMBER_PATTERN)) {
			model.addAttribute(Constants.ERROR, "请输入正确的手机号");
			return updateInfoForm(user, model);
		}
		User useEmail = userService.findByEmail(email);
		if (useEmail != null && !useEmail.equals(user)) {
			model.addAttribute(Constants.ERROR, "邮箱地址已被其他人使用了，请换一个");
			return updateInfoForm(user, model);
		}
		User useMobilePhoneNumber = userService.findByMobilePhoneNumber(mobilePhoneNumber);
		if (useMobilePhoneNumber != null && !useMobilePhoneNumber.equals(user)) {
			model.addAttribute(Constants.ERROR, "手机号已经被其他人使用了，请换一个");
			return updateInfoForm(user, model);
		}
		user.setEmail(email);
		user.setMobilePhoneNumber(mobilePhoneNumber);
		userService.update(user);

		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改个人资料成功");
		return redirectToUrl(viewName("updateInfo"));
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public String changePasswordForm(@CurrentUser User user, Model model) {
		setCommonData(model);
		model.addAttribute("user", user);
		return viewName("changePassword");
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public String changePassword(@CurrentUser User user,
			@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "plainPassword") String plainPassword,
			Model model, RedirectAttributes redirectAttributes) {
		if (!passwordService.matches(user, oldPassword)) {
			model.addAttribute(Constants.ERROR, "旧密码不正确");
			return changePasswordForm(user, model);
		}
		if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(plainPassword)){
			model.addAttribute(Constants.ERROR, "必须输入新密码");
			return changePasswordForm(user, model);
		}
		if (!newPassword.equals(plainPassword)){
			model.addAttribute(Constants.ERROR, "两次输入的密码不一致");
			return changePasswordForm(user, model);
		}
		userService.changePassword(user, newPassword);
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改密码成功");
		return redirectToUrl(viewName("changePassword"));
	}
}
