package com.ewcms.personnel.archive.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personnel.archive.entity.EducationExperience;
import com.ewcms.personnel.archive.service.EducationExperienceService;

@Controller
@RequestMapping(value = "/personnel/archive/educationExperience")
public class EducationExperienceController extends BaseCRUDController<EducationExperience, Long>{

	private EducationExperienceService getEducationExperienceService(){
		return (EducationExperienceService) baseService;
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/index")
	public String index(@PathVariable(value = "userId")Long userId, Model model){
		return super.index(model);
	}
	
	@RequestMapping(value = "{userId}/view")
	public String view(@PathVariable(value = "userId")Long userId, Model model){
		return viewName("view");
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "{userId}/query")
	@ResponseBody
	public Map<String, Object> query(@PathVariable(value = "userId")Long userId, @ModelAttribute SearchParameter<Long> searchParameter, Model model){
		searchParameter.getParameters().put("EQ_userId", userId);
		searchParameter.getSorts().put("graduationDate", Direction.DESC);
		
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "userId")Long userId, Model model, @RequestParam(required = false)List<Long> selections){
		return super.showSaveForm(model, selections);
	}
		
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, EducationExperience m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "userId")Long userId, Model model, @Valid @ModelAttribute("m") EducationExperience m, BindingResult result, @RequestParam(required = false)List<Long> selections){
		setCommonData(model);
		
		if (hasError(m, result)) {
            return showSaveForm(userId, model, selections);
        }
		
		m.setUserId(userId);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        EducationExperience lastM = getEducationExperienceService().update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        EducationExperience lastM = getEducationExperienceService().save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		return showSaveForm(userId, model, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@Override
	public AjaxResponse delete(List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/delete")
	@ResponseBody
	public AjaxResponse delete(@PathVariable(value = "userId") Long userId, @RequestParam(required = false) List<Long> selections){
		return super.delete(selections);
	}
}