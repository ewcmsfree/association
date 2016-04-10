package com.ewcms.security.organization.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewcms.common.plugin.web.controller.BaseTreeableController;
import com.ewcms.security.organization.entity.Organization;
import com.ewcms.security.organization.entity.OrganizationType;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/organization/organization")
public class OrganizationController extends BaseTreeableController<Organization, Long>{

	public OrganizationController() {
        setResourceIdentity("security:organization");
    }
	
	@Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", OrganizationType.values());
    }

    @RequestMapping(value = "table/save/discard", method = RequestMethod.GET)
    @Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
    	throw new RuntimeException("discarded method");
    }

	@RequestMapping(value = "table/save/discard", method = RequestMethod.POST)
    @Override
	public String save(Model model, @Valid @ModelAttribute("m") Organization m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
}
