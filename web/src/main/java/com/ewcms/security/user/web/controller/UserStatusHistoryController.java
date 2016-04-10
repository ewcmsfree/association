package com.ewcms.security.user.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.entity.UserStatusHistory;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/statusHistory")
public class UserStatusHistoryController extends BaseCRUDController<UserStatusHistory, Long> {
	
	public UserStatusHistoryController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("security:userStatusHistory");
    }

    @Override
    protected void setCommonData(Model model) {
    	super.setCommonData(model);
        model.addAttribute("statusList", UserStatus.values());
    }
}
