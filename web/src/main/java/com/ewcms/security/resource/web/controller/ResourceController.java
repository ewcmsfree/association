package com.ewcms.security.resource.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.plugin.web.controller.BaseTreeableController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.resource.entity.Resource;
import com.ewcms.security.resource.entity.ResourceStyle;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/resource")
public class ResourceController extends BaseTreeableController<Resource, Long>{
	
	public ResourceController() {
        setResourceIdentity("security:resource");
    }
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("styles", ResourceStyle.values());
	}
	
	@RequestMapping(value = "changeStatus/{newStatus}")
	@ResponseBody
	public AjaxResponse changeStatus(@PathVariable("newStatus") Boolean newStatus, @RequestParam("selections") List<Long> selections){
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		try{
			for (Long id : selections){
				Resource resource = baseService.findOne(id);
				if (resource.isRoot()) continue;
				resource.setShow(newStatus);
				baseService.update(resource);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}

    @RequestMapping(value = "{id}/edit/discard", method = RequestMethod.GET)
    @Override
    public String showSaveForm(@PathVariable("id") Resource m, Model model, RedirectAttributes redirectAttributes){
		throw new RuntimeException("discarded method");
    }
    
    @RequestMapping(value = "save/discard", method = RequestMethod.POST)
    @Override
	public String save(Model model, @ModelAttribute("m") Resource m, BindingResult result, RedirectAttributes redirectAttributes) {
		throw new RuntimeException("discarded method");
	}    
}
