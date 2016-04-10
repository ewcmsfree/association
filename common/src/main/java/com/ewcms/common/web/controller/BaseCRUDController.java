package com.ewcms.common.web.controller;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.AbstractEntity;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.web.controller.permission.PermissionList;
import com.ewcms.common.web.validate.AjaxResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础CRUD 控制器
 * 
 * @author wu_zhijun
 */
public abstract class BaseCRUDController<M extends AbstractEntity<ID>, ID extends Serializable> extends BaseController<M, ID> {

	protected BaseService<M, ID> baseService;

	private boolean listAlsoSetCommonData = false;

	protected PermissionList permissionList = null;

	/**
	 * 设置基础service
	 *
	 * @param baseService
	 */
	@Autowired
	public void setBaseService(BaseService<M, ID> baseService) {
		this.baseService = baseService;
	}

	/**
	 * 列表也设置common data
	 */
	public void setListAlsoSetCommonData(boolean listAlsoSetCommonData) {
		this.listAlsoSetCommonData = listAlsoSetCommonData;
	}

	/**
	 * 权限前缀：如sys:user 则生成的新增权限为 sys:user:create
	 */
	public void setResourceIdentity(String resourceIdentity) {
		if (!StringUtils.isEmpty(resourceIdentity)) {
			permissionList = PermissionList.newPermissionList(resourceIdentity);
		}
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		
		if (listAlsoSetCommonData){
			setCommonData(model);
		}
		return viewName("index");
	}

	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<ID> searchParameter, Model model) {
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		
		if (listAlsoSetCommonData){
			setCommonData(model);
		}
		
		//searchParameter.getSorts().put("id", Direction.DESC);
		
		return baseService.query(searchParameter);
	}

	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String showSaveForm(Model model, @RequestParam(required = false) List<ID> selections) {
        setCommonData(model);

        if (!model.containsAttribute("close")){
	        if (selections == null || selections.isEmpty()) {
		        if (permissionList != null) {
		            this.permissionList.assertHasCreatePermission();
		        }
	        	model.addAttribute("operate", "add");
		        
		        if (!model.containsAttribute("m")) {
		            model.addAttribute("m", newModel());
		        }
		        
				//model.addAttribute("selections", Lists.<ID>newArrayList());
			} else {
				if (permissionList != null) {
		            this.permissionList.assertHasUpdatePermission();
		        }
				model.addAttribute("operate", "update");
				
				M m = baseService.findOne(selections.get(0));
				model.addAttribute("m", m);
				model.addAttribute("selections", selections);
			}
        }
        
		return viewName("edit");
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") M m, BindingResult result, @RequestParam(required = false) List<ID> selections) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

		setCommonData(model);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        M lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        M lastM = baseService.save(m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(model, selections);
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(required = false) List<ID> selections){
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
				baseService.delete(selections);
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
		return ajaxResponse;
	}
}
