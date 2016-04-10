package com.ewcms.web.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.extra.push.PushService;
import com.ewcms.security.resource.entity.Menu;
import com.ewcms.security.resource.service.ResourceService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
public class HomeController {
	
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private PushService pushApiService;

	@RequestMapping(value = "home")
	public String home(@CurrentUser User user, Model model){
        List<Menu> menus = resourceService.findMenus(user);
        model.addAttribute("menus", menus);
        
        pushApiService.offline(user.getId());
        
		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			model.addAttribute("previousUsername", previousUsername);
		}

		return "home";
	}
	
	
}
