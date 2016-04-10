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
import com.ewcms.security.organization.entity.Job;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/organization/job")
public class JobController extends BaseTreeableController<Job, Long> {
	
	public JobController() {
        setResourceIdentity("security:job");
    }
	
    @RequestMapping(value = "table/save/discard", method = RequestMethod.GET)
    @Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
    	throw new RuntimeException("discarded method");
    }

	@RequestMapping(value = "table/save/discard", method = RequestMethod.POST)
    @Override
	public String save(Model model, @Valid @ModelAttribute("m") Job m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
}
