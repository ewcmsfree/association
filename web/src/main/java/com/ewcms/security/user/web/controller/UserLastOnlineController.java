package com.ewcms.security.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.UserLastOnline;
import com.ewcms.security.user.service.UserService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/lastOnline")
public class UserLastOnlineController extends BaseCRUDController<UserLastOnline, Long> {
	
	@Autowired
	private UserService userService;
	
	public UserLastOnlineController() {
        setResourceIdentity("security:userLastOnline");
        setListAlsoSetCommonData(true);
    }
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("userList", userService.findAll());
	}
}
