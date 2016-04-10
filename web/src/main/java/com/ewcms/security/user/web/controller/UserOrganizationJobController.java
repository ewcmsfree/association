package com.ewcms.security.user.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.service.UserService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/userOrganizationJob")
public class UserOrganizationJobController extends BaseCRUDController<UserOrganizationJob, Long>{
	
	private UserOrganizationJobService getUserOrganizationJobService(){
		return (UserOrganizationJobService) baseService;
	}

	@Autowired
	private UserService userService;
	
    @Value("${full.path.separator.mark}")
    private String fullPathSeparator;
	
	public UserOrganizationJobController() {
		setResourceIdentity("security:user");
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
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "userId")Long userId){
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, UserOrganizationJob.class);
		searchable.addSearchFilter("user.id", SearchOperator.EQ, userId);
		searchable.addSort(Direction.ASC, "id");
		
		return getUserOrganizationJobService().findUserOrganizationJobFullNames(searchable, fullPathSeparator);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "userId") Long userId, Model model, @RequestParam(required = false) List<Long> selections){
		return super.showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") UserOrganizationJob m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/save", method = RequestMethod.POST)
	public String save(@PathVariable(value = "userId") Long userId, Model model, @Valid @ModelAttribute("m") UserOrganizationJob m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		setCommonData(model);
		
		if (hasError(m, result)) {
            return showSaveForm(userId, model, selections);
        }
		
		User user = userService.findOne(userId);
		user.addOrganizationJob(m);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        UserOrganizationJob lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        UserOrganizationJob lastM = baseService.save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(userId, model, selections);
	}
	
	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{userId}/delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections, @PathVariable(value = "userId") Long userId){
		return super.delete(selections);
	}
	
    @RequestMapping(value = "{userId}/validate")
    @ResponseBody
    public Object validate(@PathVariable("userId")User user, @ModelAttribute("m") UserOrganizationJob m) {
        m.setUser(user);
        UserOrganizationJob dbUserOrganizationJob = getUserOrganizationJobService().findByUserAndOrganizationIdAndJobId(user, m.getOrganizationId(), m.getJobId());
        
        ValidateResponse response = ValidateResponse.newInstance();
        if (dbUserOrganizationJob == null || (dbUserOrganizationJob.getId().equals(m.getId()) && dbUserOrganizationJob.getUser().equals(user) && dbUserOrganizationJob.getJobId().equals(m.getJobId()) && dbUserOrganizationJob.getOrganizationId().equals(m.getOrganizationId()))){
        	response.validateSuccess("", "");
        } else {
        	response.validateFail("", "记录已存在，请重新选择");
        }
        return response.result();
    }
}
